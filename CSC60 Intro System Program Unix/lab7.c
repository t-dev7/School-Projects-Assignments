/*  Trevor Davis                           */ 
/*  lab 7 on structures                    */

#include <stdio.h>
#include <stdlib.h>

#define OUT_FILENAME "lab7.out"
#define TRIES 3

#define IN_FILENAME "lab7.dat"
//#define IN_FILENAME "lab7sample.dat"

//The final data file has a length of 10, sample file has a length of 4.
#define NRACERS 10	
//#define NRACERS 4

/*-----------------------------------------------------------*/
/* Structure Section */
 
 typedef struct
 {
    char name[21];
    double tries[TRIES];
    double best_time;
    double deviation;

 }driver_t;

typedef struct
{
    double best_average;
    double fast_time;
    double slow_time;
    double median;
}stats_t;

/*-----------------------------------------------------------*/
/* Function Prototypes Section */
	/* get_data is a function to get and read data         */
void get_data (char *filename,                   /* input  */ 
	       driver_t driver_list[NRACERS] );  /* output */

	/* open_out_file is a function to open the output file */
FILE * open_out_file (void);

	/* get_stats is a function to do figure the best time for */
	/* each racer, compute the all-over average of the best   */
	/* times, and find the longest time and the shortest time */
	/* on the track     */
void get_stats(	driver_t driver_list[NRACERS],    /* in & out */
		stats_t *race_stats );            /* in & out */

	/* do_sort is a function to sort the drivers based on     */
	/* their best times                                       */  
void do_sort( driver_t driver_list[NRACERS] ); 

	/*  get_median determines the median or mid value of      */
	/*  the best times.                                       */
void get_median(driver_t driver_list[NRACERS] ,
		        stats_t *race_stats ); 
			    
	/*  print_all is a function to print things out.          */
	/*	all of its arguments are input                        */
void print_all(FILE * out_file,
  	       driver_t driver_list[NRACERS] ,
	       stats_t *race_stats ); 
			  
/*-----------------------------------------------------------*/
int main(void)
{
    driver_t driver_list[NRACERS];
    stats_t race_stats = {0.0, 0.0, 0.0, 0.0};  
    FILE * out_file;   /* file pointer for the output file    */
			
    /* Start the action. */

    out_file = open_out_file ();
    get_data(IN_FILENAME, driver_list);
    get_stats(driver_list, &race_stats);
    do_sort(driver_list);
    get_median(driver_list, &race_stats);
    print_all(out_file, driver_list, &race_stats);

    return EXIT_SUCCESS;
}

/*----------------------------------------------------------*/
/* This function will open the output file and return the   */
/* file pointer name to the main program.                   */

FILE * open_out_file (void)
{
    FILE * outfile;

    outfile = fopen(OUT_FILENAME, "w");
    if (outfile == NULL)
    {
	printf("Error on fopen of %s \n", OUT_FILENAME);
	exit(EXIT_FAILURE);
    }

    return outfile;
}

/*-----------------------------------------------------------*/
/* This function will open and read data into an array.      */

void get_data (char *filename,                    /* input   */ 
	       driver_t driver_list[NRACERS])     /* output  */
{
    int d;
    FILE *in_file;

    /* Open the data file and read in the array */
    in_file = fopen(filename, "r");
    if (in_file == NULL)
    {
	printf("Error on fopen of %s \n", filename);
	exit(EXIT_FAILURE);
    }

    /* Use an "d" for Driver. */
    for(d = 0; d < NRACERS; d++)
    {
	fscanf(in_file, "%20c%lf%lf%lf\n", 
		&(driver_list[d].name),
		&(driver_list[d].tries[0]),
		&(driver_list[d].tries[1]),
		&(driver_list[d].tries[2]) ); 
	          
	driver_list[d].name[20] = '\0';
    }
	
    fclose(in_file);
    return;
}


/*--------------------------------------------------------*/
/* This function will print everything out.               */

void print_all(FILE * out_file,
	       driver_t driver_list[NRACERS],
	       stats_t *race_stats ) 
{
    int d,t;

    fprintf(out_file, "\nTrevor Davis.  Lab 7 output. \n");

    fprintf(out_file, "\nTrack Results");

    fprintf(out_file,"\n\nDrivers                  Try 1       Try 2       Try 3      Best Time   Deviation");

    fprintf(out_file,  "\n--------------------   ---------   ---------   ---------   ----------   ---------\n");                          

    for (d = 0; d< NRACERS; d++)
    {
	fprintf(out_file, "%20s", driver_list[d].name);
	for (t = 0; t < TRIES; t++)
	    fprintf(out_file, "   %8.3f ", driver_list[d].tries[t] );

	fprintf(out_file, "    %8.3f", driver_list[d].best_time );
	fprintf(out_file, "    %8.3f\n", driver_list[d].deviation);
    }

    fprintf(out_file, "\n\n The average of best times = %8.3f ", race_stats->best_average);
    fprintf(out_file, "\n\n The track fast time       = %8.3lf ", race_stats->fast_time);
    fprintf(out_file, "\n\n The track slow time       = %8.3f ", race_stats->slow_time);
    fprintf(out_file, "\n\n The median of best times  = %8.3f ", race_stats->median);
    fprintf(out_file, "\n\n");
    return;
}

/*--------------------------------------------------------*/
/* do_sort is a function to sort the drivers based on     */
/* their best times                                       */

void do_sort(driver_t driver_list[NRACERS])  
{
    int k, j, m; 
    driver_t hold;
 
    /* start the Selection Sort algorithm */
    for (k=0; k < NRACERS-1; k++)
    {
	/* exchange min with next array value */
	m = k;
	for (j = k+1; j < NRACERS; j++)
	{
	    if ( driver_list[j].best_time < driver_list[m].best_time )
		m = j;
	}
	hold = driver_list[m];
	driver_list[m] = driver_list[k];
	driver_list[k] = hold;
    }
    return;
}
/*----------------------------------------------------------------*/
/* get_stats is a function to do figure the best time for each    */
/* racer, compute the all-over average of the best times, and     */
/* find the longest time and the shortest time on the track       */

void get_stats(	driver_t driver_list[NRACERS],    /* in & out */
		stats_t *race_stats )                     /* in & out */
{
    race_stats->best_average = 0;                     // best overall average = 0
    race_stats->slow_time = driver_list[0].tries[0];  // slowest race time = 1st driver 1st try
    race_stats->fast_time = driver_list[0].tries[0];  // fastest overall race time = 1st driver 1st try
    
    int d, t;
    double avg;
    for (d=0; d < NRACERS; d++)
       {
               driver_list[d].deviation = 0;
               driver_list[d].best_time = driver_list[d].tries[0];  // current driver best time = their first try

               for (t=0; t < TRIES; t++)
               {
                   if (driver_list[d].best_time > driver_list[d].tries[t])      // if the current drivers overall best time is greater than the thier current try... 
                        driver_list[d].best_time = driver_list[d].tries[t];     // make their current try their new best time

                    if (driver_list[d].tries[t] < race_stats->fast_time)        // if the drivers current try is less than than the current overall fast time
                            race_stats->fast_time = driver_list[d].tries[t];    // then make the current try = to the fastest time (race_stats->fast_time) 
                  
                    if (driver_list[d].tries[t] > race_stats->slow_time)
                            race_stats->slow_time = driver_list[d].tries[t];

                 }
                 avg = avg + driver_list[d].best_time;        
       }

       for (d=0; d < NRACERS; d++)
       {
               driver_list[d].deviation = race_stats->fast_time - driver_list[d].best_time;
       }

    avg = avg / (NRACERS);
    race_stats->best_average = avg;

       return;
}




/*----------------------------------------------------------------*/
/*  This function determines the median or mid value of           */
/*  the best times.                                               */
void get_median(driver_t driver_list[NRACERS],
		stats_t *race_stats )
{
        int midpoint;
        race_stats->median = 0;
        midpoint = NRACERS / 2;

        if (NRACERS % 2 != 0 )
            race_stats->median = driver_list[midpoint + 1].best_time;
         else 
             {
                race_stats->median = (driver_list[midpoint].best_time + driver_list[midpoint - 1].best_time)/2;
             }
}







/*----------------------------------------------------------------*/

