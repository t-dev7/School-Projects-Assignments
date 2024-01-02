/* Author(s): Trevor Davis & Mario Palacios
 *
 * This is lab9.c the csc60mshell
 * This program serves as a skeleton for doing labs 9, 10, 11.
 * Student is required to use this program to build a mini shell
 * using the specification as documented in direction.
 * Date: Fall 2019
 */

/* The include files section */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>

/* Define Section */
#define MAXLINE 80
#define MAXARGS 20
#define MAX_PATH_LENGTH 50
#define TRUE 1

/* function prototypes */
int parseline(char *cmdline, char **argv);

//The two function prototypes below will be needed in lab10. 
//Leave them here to be used later.
 void process_input(int argc, char **argv); 
 void handle_redir(int argc, char *argv[]); 


/* ----------------------------------------------------------------- */
/*                  The main program starts here                     */
/* ----------------------------------------------------------------- */
int main(void)
{
    char cmdline[MAXLINE];
    char *argv[MAXARGS];
    int argc;
    int status;
    pid_t pid;

    /* Loop forever to wait and process commands */
    while (TRUE)
    {
	/* Print your shell name: csc60msh (m for mini shell) */
	printf("csc60msh> ");

	/* Read the command line */
	fgets(cmdline, MAXLINE, stdin);

	/* Call parseline to build argc/argv */
    argc = parseline(cmdline, argv);

    //printf("Argc = %i\n",argc);

    int i = 0;      // loop counter 
    for (i = 0; i < argc; i++)
    {
        printf("Argv %i = %s\n",i,argv[i]);
    }
    

    if(argc == 0)
	{
        continue;
	}
    if(strcmp(argv[0],"exit") == 0)
    {
        if("exit")
        {
            exit(EXIT_SUCCESS);
        }
    }
    else if(strcmp(argv[0],"pwd")== 0)
    {        
      if("pwd")
        {
            char path[MAX_PATH_LENGTH];
            getcwd(path, MAX_PATH_LENGTH);
            printf("%s\n",path);
            continue; 
        }
    }
    else if(strcmp(argv[0],"cd") == 0)
	{
		char* dir;
        if(argc == 1)
        {
          dir =  getenv("HOME");
        }
        else
        {
            dir = argv[1];
        }
        if (chdir(dir) != 0)
	    {
            perror("Error changing directory\n");
		}
	  continue;
	}
//	/* Else, fork off a process */
		else
		{
	    pid = fork();
        switch(pid)
    	    {
	    	case -1:
		    perror("Shell Program fork error");
	            exit(EXIT_FAILURE);
	   	case 0:
		    /* I am child process. I will execute the command, */
		    /* and call: execvp */
		    process_input(argc, argv);
		    break;
	   	default:
		    /* I am parent process */
 		    if (wait(&status) == -1)
     		    	perror("Parent Process error");
		    else
		   	printf("Child returned status: %d\n",status);
		    break;
	    } 	/* end of the switch */
  	}	/* end of the if-else-if */
  }		/* end of the while */
} 		/* end of main */

/* ----------------------------------------------------------------- */
/*                  parseline                                        */
/* ----------------------------------------------------------------- */
/* parse input line into argc/argv format */

int parseline(char *cmdline, char **argv)
{
    int argc = 0;
    char *separator = " \n\t"; /* Includes space, Enter, Tab */
 
    /* strtok searches for the characters listed in separator */
    argv[argc] = strtok(cmdline, separator);

    while ((argv[argc] != NULL) && (argc+1 < MAXARGS)) 
    	argv[++argc] = strtok((char *) 0, separator);
     		
    return argc;  
}
/* ----------------------------------------------------------------- */
/*                  process_input                                    */
/* ----------------------------------------------------------------- */
 void process_input(int argc, char **argv) {                        

    /* Step 1: Call handle_redir to deal with operators:             */
    /* < , or  >, or both                                            */
       handle_redir(argc, argv);

    /* Step 2: perform system call execvp to execute command         */
    /* Hint: Please be sure to review execvp.c sample program        */
     int return_value = execvp(argv[0], argv);
     
     if (return_value == -1) 
     {                                         
        fprintf(stderr, "Error on the exec call\n");              
        _exit(EXIT_FAILURE);                                       
     }                                                             
 
}
/* ----------------------------------------------------------------- */
 void handle_redir(int count, char *argv[])
 {
    int out_loc = 0;              // out_lock
    int in_loc = 0;               // in_lock
    int i;                     // loop variable 

    for (i = 0 ; i < count; i++)
    {
       if(strcmp(argv[i],">") == 0)   // if redirect is detected
        {
            if (out_loc != 0)
            {
                perror("Error. Cannot output to more than one file.");
                _exit(EXIT_FAILURE);
            }// end of if
            else if (i == 0)
            {
                perror("Error. No command entered");
                _exit(EXIT_FAILURE);
            }// end of else if
            out_loc = i;
        } // end of ">" loop

       else if (strcmp(argv[i],"<") == 0)
        {
			if (in_loc != 0)
			{
                perror("Error. Cannot input more than one file.");  
                _exit(EXIT_FAILURE);  
			}// end of it
          else if ( i == 0)
          {
             perror("Error. No command entered.");
             _exit(EXIT_FAILURE);
          }//end of else if

          in_loc = i;
        } // end of "<" loop
		
		if (out_loc != 0)                                           // if a redirect out is taking place
		{
			int fd = open(argv[out_loc+1], O_RDWR | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR);

            if (fd < 0)
            {
               perror("Error. Cannot Open Output.");
               _exit(EXIT_FAILURE);
            } // end of if statement
			
			            dup2(fd, 1);
            if (close(fd) == -1)
            {
                perror("Error. Cannot close output file");
                _exit(EXIT_FAILURE);
            }
            argv[out_loc] = NULL;
		}
		if (in_loc != 0)
        {
			if (argv[in_loc+1] == NULL)                             // if there is no file 
			{
				perror("Error. No file to open");   
				_exit(EXIT_FAILURE);
			}
			int fd = open(argv[in_loc+1],O_RDWR);
			if (fd < 0)
			{
            perror("Error. Could not open file to read.");
            _exit(EXIT_FAILURE);
			}
			dup2(fd,0);
		    if (close(fd) == -1)
				{
				perror("Error. Could not close for read.");
				_exit(EXIT_FAILURE);
			}
			argv[in_loc] = NULL;
		}
	}
 }	
/* ----------------------------------------------------------------- */


/* ----------------------------------------------------------------- */
