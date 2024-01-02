lab5: lab5.o compute.o lab5.h
	gcc -lm -o lab5 lab5.o compute.o 

lab5.o: lab5.c lab5.h
	gcc -c lab5.c
	
compute.o: compute.c lab5.h
	gcc -c compute.c
