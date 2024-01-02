/*-------------------------------------------------*/
/* Trevor Davis                                    */
/* Lab 4                                           */
/* Figure the perimeter and area of a polygon      */
/* surrounded by a circle                          */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>


//#define INFILE "lab4sample.dat"
#define INFILE "lab4.dat"
//#define OUTFILE "lab4sample.out"
#define OUTFILE "lab4.out"


int main(void)
{

FILE *input_file;
FILE *output_file;
double radius, nsides, Perimeter, Area = 0;
	

// opening input & output files 
input_file = fopen(INFILE,"r");
    if(input_file == NULL)
        {
	    printf("Error on opening data file\n");
	    exit (EXIT_FAILURE);
        }

output_file = fopen(OUTFILE,"w");
    if(output_file == NULL)
        {
          printf("Error on opening output file\n");
          exit (EXIT_FAILURE);
        }
   
	
    fprintf(output_file, "\nTrevor Davis.  Lab 4.\n\n");
	fprintf(output_file, "            Number      Perimeter      Area Of  \n");
	fprintf(output_file, " Radius    Of Sides    Of Polygon      Polygon  \n");
	fprintf(output_file, "--------   --------   ------------   ----------- \n");

	
    while((fscanf(input_file, "%lf%lf", &radius, &nsides)) == 2)
    {	
     Perimeter = 2*nsides*radius*(sin(M_PI / nsides));
     Area = 0.5 * nsides * (radius * radius) * (sin((2 * M_PI) / nsides));
     fprintf(output_file, "%7.2f      %5.2f     %8.4f       %9.4f\n",radius, nsides, Perimeter, Area);
       	
	}		
fclose(input_file);
fclose(output_file);



return EXIT_SUCCESS;

		
}



/*------------------------------------------------------------------*/
