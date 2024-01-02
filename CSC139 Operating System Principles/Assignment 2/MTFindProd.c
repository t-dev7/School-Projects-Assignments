/*
CSC139 
Fall 2023
Second Assignment
Davis, Trevor
Section #2
OSs Tested on: Linux


Architecture:          x86_64
CPU op-mode(s):        32-bit, 64-bit
Byte Order:            Little Endian
CPU(s):                4
On-line CPU(s) list:   0-3
Thread(s) per core:    1
Core(s) per socket:    2
Socket(s):             2
NUMA node(s):          1
Vendor ID:             GenuineIntel
CPU family:            6
Model:                 58
Model name:            Intel(R) Xeon(R) Gold 6254 CPU @ 3.10GHz
Stepping:              0
CPU MHz:               3092.733
BogoMIPS:              6185.46
Hypervisor vendor:     VMware
Virtualization type:   full
L1d cache:             32K
L1i cache:             32K
L2 cache:              1024K
L3 cache:              25344K
NUMA node0 CPU(s):     0-3

*/

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/timeb.h>
#include <semaphore.h>

#define MAX_SIZE 100000000
#define MAX_THREADS 16
#define RANDOM_SEED 7649
#define MAX_RANDOM_NUMBER 3000
#define NUM_LIMIT 9973

// Global variables
long gRefTime; //For timing
int gData[MAX_SIZE]; //The array that will hold the data

int gThreadCount; //Number of threads
int gDoneThreadCount; //Number of threads that are done at a certain point. Whenever a thread is done, it increments this. Used with the semaphore-based solution
int gThreadProd[MAX_THREADS]; //The modular product for each array division that a single thread is responsible for
bool gThreadDone[MAX_THREADS]; //Is this thread done? Used when the parent is continually checking on child threads

// Semaphores
sem_t completed; //To notify parent that all threads have completed or one of them found a zero
sem_t mutex; //Binary semaphore to protect the shared variable gDoneThreadCount

int SqFindProd(int size); //Sequential FindProduct (no threads) computes the product of all the elements in the array mod NUM_LIMIT
void *ThFindProd(void *param); //Thread FindProduct but without semaphores
void *ThFindProdWithSemaphore(void *param); //Thread FindProduct with semaphores
int ComputeTotalProduct(); // Multiply the division products to compute the total modular product 
void InitSharedVars();
void GenerateInput(int size, int indexForZero); //Generate the input array
void CalculateIndices(int arraySize, int thrdCnt, int indices[MAX_THREADS][3]); //Calculate the indices to divide the array into T divisions, one division per thread
int GetRand(int min, int max);//Get a random number between min and max

//Timing functions
long GetMilliSecondTime(struct timeb timeBuf);
long GetCurrentTime(void);
void SetTime(void);
long GetTime(void);

int main(int argc, char *argv[]){

	pthread_t tid[MAX_THREADS];
	pthread_attr_t attr[MAX_THREADS];
	int indices[MAX_THREADS][3];
	int i, indexForZero, arraySize, prod;

	// Code for parsing and checking command-line arguments
	if(argc != 4){
		fprintf(stderr, "Invalid number of arguments!\n");
		exit(-1);
	}
	if((arraySize = atoi(argv[1])) <= 0 || arraySize > MAX_SIZE){
		fprintf(stderr, "Invalid Array Size\n");
		exit(-1);
	}
	gThreadCount = atoi(argv[2]);
	if(gThreadCount > MAX_THREADS || gThreadCount <=0){
		fprintf(stderr, "Invalid Thread Count\n");
		exit(-1);
	}
	indexForZero = atoi(argv[3]);
	if(indexForZero < -1 || indexForZero >= arraySize){
		fprintf(stderr, "Invalid index for zero!\n");
		exit(-1);
	}

    GenerateInput(arraySize, indexForZero);

    CalculateIndices(arraySize, gThreadCount, indices);

	// Code for the sequential part
	SetTime();
	prod = SqFindProd(arraySize);
	printf("Sequential multiplication completed in %ld ms. Product = %d\n", GetTime(), prod);
	// END sequential END
	
	
	// Threaded with parent waiting for all child threads //
	InitSharedVars();
	SetTime();

	// Write your code here
	// Initialize threads, create threads, and then let the parent wait for all threads using pthread_join
	// The thread start function is ThFindProd
	// Don't forget to properly initialize shared variables
	int j;
	for (j = 0; j < gThreadCount; j++) {
            pthread_create(&tid[j], NULL, ThFindProd, &indices[j]);
	}
	//let the parent wait for all threads using pthread_join
	int k;
	for (k = 0; k < gThreadCount; k++) {
            pthread_join(tid[k], NULL);
	}

    prod = ComputeTotalProduct();
	printf("Threaded multiplication with parent waiting for all children completed in %ld ms. Product = %d\n", GetTime(), prod);
	/////////////////////////// END parent wait for child thread END//////////////////
	
	
	
	// Multi-threaded with busy waiting (parent continually checking on child threads without using semaphores) //
	InitSharedVars();
	SetTime();

	// Write your code here
    // Don't use any semaphores in this part
	// Initialize threads, create threads, and then make the parent continually check on all child threads
	// The thread start function is ThFindProd
	// Don't forget to properly initialize shared variables
	
	//Initialize threads, create threads
	int m;
	int n;
	for(m = 0; m < gThreadCount; m++) {
            pthread_create(&tid[m], NULL, ThFindProd, &indices[m]);
        }
		
		volatile int done = 0;// prevent the compiler from optimizing out the
							  //checking code and replacing the loop with an infinite loop
        
	while( done < gThreadCount ){
		for(n=0; n < gThreadCount; n++){
			if(gThreadDone[n]){
				if(gThreadProd[n] == 0){
					break;
				} // if(gThreadProd[i] == 0) END
				done += 1;
			} // if(gThreadDone) END
		}// for() END	
	} // while() END

	int p;
	for( p = 0; p < gThreadCount; p++) {
            pthread_cancel(tid[p]);
        } 

    prod = ComputeTotalProduct();
	printf("Threaded multiplication with parent continually checking on children completed in %ld ms. Product = %d\n", GetTime(), prod);
	/////////////////////// END multi-thread busy wait END ////////////////////////////////
	
	

	// Multi-threaded with semaphores

	InitSharedVars();
	
    // Initialize your semaphores here
	  sem_init(&mutex,0,1); // create semaphore and initialize it to 1
	  sem_init(&completed,0,0); // create semaphore and initialize it to 0

	SetTime();

    // Write your code here
	// Initialize threads, create threads, and then make the parent wait on the "completed" semaphore
	// The thread start function is ThFindProdWithSemaphore
	// Don't forget to properly initialize shared variables and semaphores using sem_init
	int q;
	for (q = 0; q < gThreadCount; q++) {
	    pthread_create(&tid[q], NULL, ThFindProdWithSemaphore, &indices[q]);
	}
	sem_wait(&completed);	
        
	int r;	
    for(r = 0; r < gThreadCount; r++) {
	    pthread_cancel(tid[r]);
	}

    prod = ComputeTotalProduct();
	printf("Threaded multiplication with parent waiting on a semaphore completed in %ld ms. Product = %d\n", GetTime(), prod);
}

// Write a regular sequential function to multiply all the elements in gData mod NUM_LIMIT
// REMEMBER TO MOD BY NUM_LIMIT AFTER EACH MULTIPLICATION TO PREVENT YOUR PRODUCT VARIABLE FROM OVERFLOWING
int SqFindProd(int size) {

int prod = 1;

int i;
for(i=0; i < size; i++){ 
	
	if (gData[i] == 0){ 
		break;
	} // if() END
	else{
	prod = prod * gData[i];
	prod %= NUM_LIMIT;
	}//else END
} // for() END

	return prod; // return the product
	
} // SqFindProd(int size) END


// Write a thread function that computes the product of all the elements in one division of the array mod NUM_LIMIT
// REMEMBER TO MOD BY NUM_LIMIT AFTER EACH MULTIPLICATION TO PREVENT YOUR PRODUCT VARIABLE FROM OVERFLOWING
// When it is done, this function should store the product in gThreadProd[threadNum] and set gThreadDone[threadNum] to true
void* ThFindProd(void *param) {
	int threadNum = ((int*)param)[0];
	
	 int start = ((int*)param)[1];   
    int end = ((int*)param)[2];

gThreadProd[threadNum] = 1;	

	int i;
    for(i = start; i < end; i++) {
        if(gData[i] == 0) {
            break;
        }
    else{   
	   	gThreadProd[threadNum] *= gData[i]; // this function should store the product in gThreadProd[threadNum]
		gThreadProd[threadNum] %= NUM_LIMIT;
		}//else END
    }//for() END
	
    gThreadDone[threadNum] = true; // set gThreadDone[threadNum] to true 
    pthread_exit(NULL); // exit thread

}

// Write a thread function that computes the product of all the elements in one division of the array mod NUM_LIMIT
// REMEMBER TO MOD BY NUM_LIMIT AFTER EACH MULTIPLICATION TO PREVENT YOUR PRODUCT VARIABLE FROM OVERFLOWING
// When it is done, this function should store the product in gThreadProd[threadNum]
// If the product value in this division is zero, this function should post the "completed" semaphore
// If the product value in this division is not zero, this function should increment gDoneThreadCount and
// post the "completed" semaphore if it is the last thread to be done
// Don't forget to protect access to gDoneThreadCount with the "mutex" semaphore
void* ThFindProdWithSemaphore(void *param) {
	
	
   int threadNum = ((int*)param)[0]; // thread number
    int start = ((int*)param)[1]; // start index  
    int end = ((int*)param)[2];  // end index
	
	gThreadProd[threadNum] = 1;	
	
	int i;
	 for(i = start; i < end; i++) {
        if(gData[i] == 0) { // If the product value in this division is zero
            sem_post(&completed); // post the "completed" semaphore
            break;
        } // if() END
		else{
		gThreadProd[threadNum] *= gData[i];
		gThreadProd[threadNum] %= NUM_LIMIT;
		}//else END
		
    } // for() END   
 
    gThreadDone[threadNum] = true; 

    sem_wait(&mutex); //  protect access to gDoneThreadCount with the "mutex" semaphore
    
    gDoneThreadCount++; // increment gDoneThreadCount 
    
  
    if (gDoneThreadCount == gThreadCount) {
        sem_post(&completed); // post the "completed" semaphore if it is the last thread to be done
    }
    
    sem_post(&mutex);  // release semaphore.
    
    pthread_exit(NULL); // exit the thread 

}

int ComputeTotalProduct() {
    int i, prod = 1;

	for(i=0; i<gThreadCount; i++)
	{
		prod *= gThreadProd[i];
		prod %= NUM_LIMIT;
	}

	return prod;
}

void InitSharedVars() {
	int i;

	for(i=0; i<gThreadCount; i++){
		gThreadDone[i] = false;
		gThreadProd[i] = 1;
	}
	gDoneThreadCount = 0;
}

// Write a function that fills the gData array with random numbers between 1 and MAX_RANDOM_NUMBER
// If indexForZero is valid and non-negative, set the value at that index to zero
void GenerateInput(int size, int indexForZero) {

	int i;
	for(i=0; i < size; i++){
		
		if(i == indexForZero ) // if current index is the index for zero and is non-negative
			gData[i] = 0;
		
		else {
			gData[i] = GetRand(1, MAX_RANDOM_NUMBER);
		} //else END
		
	} // for() END
} // GenerateInput END

// Write a function that calculates the right indices to divide the array into thrdCnt equal divisions
// For each division i, indices[i][0] should be set to the division number i,
// indices[i][1] should be set to the start index, and indices[i][2] should be set to the end index
void CalculateIndices(int arraySize, int thrdCnt, int indices[MAX_THREADS][3]) {
	
	int div = arraySize/thrdCnt;
	int iStart = 0;
	int iEnd = div-1;
	
	int i;
	for (i=0; i<thrdCnt; i++){
		indices[i][0] = i; //indices[i][0] should be set to the division number i
		indices[i][1] = iStart; // indices[i][1] should be set to the start index
		indices[i][2] = iEnd; // indices[i][2] should be set to the end index
		
		iStart += div; 
		iEnd += div;
	} //for() END

} // CalculateIndices END

// Get a random number in the range [x, y]
int GetRand(int x, int y) {
    int r = rand();
    r = x + r % (y-x+1);
    return r;
}

long GetMilliSecondTime(struct timeb timeBuf){
	long mliScndTime;
	mliScndTime = timeBuf.time;
	mliScndTime *= 1000;
	mliScndTime += timeBuf.millitm;
	return mliScndTime;
}

long GetCurrentTime(void){
	long crntTime=0;
	struct timeb timeBuf;
	ftime(&timeBuf);
	crntTime = GetMilliSecondTime(timeBuf);
	return crntTime;
}

void SetTime(void){
	gRefTime = GetCurrentTime();
}

long GetTime(void){
	long crntTime = GetCurrentTime();
	return (crntTime - gRefTime);
}

