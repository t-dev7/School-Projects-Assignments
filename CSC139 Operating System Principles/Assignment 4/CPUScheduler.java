/*CSC139 
Fall 2023
Second Assignment
Davis, Trevor
Section #2*/


// Import this class to handle errors
import java.io.*;  // Import the File class
// Import the Scanner class to read text files
import java.util.*;



public class CPUScheduler {
	//Global Variables
	private static final int MAX = 100;
	private static int timeQuantum = 0;
	private static int _processes = 0;
	private static int[][] process ;
	private static int[][] finished;
	 static ArrayList<int[]> paths = new ArrayList<int[]>();
	 static int[] processNumber;
	 static int[] sameArrival = new int[2];;
	
	static int clock = 0;
	

	public static void main(String[] args) throws FileNotFoundException  {
		// TODO Auto-generated method stub
		getInput();
	}
	
	// get file input
	public static void getInput() throws FileNotFoundException {
		
			
			File file = new File("input.txt");
			
			 Scanner scanner = new Scanner(file);
				 
		   String schedule = scanner.nextLine();  
		   int processes = scanner.nextInt();
		   _processes = processes;

		   processNumber = new int[4]; // row/process number
 
		   // Round Robin//////////////////////////////////////
        if(schedule.charAt(0) == 'R') {
        	  for(int i = 0; i < processes; i++) {
    			   for(int j=0; j<4; j++) { 
    				   processNumber[j] = scanner.nextInt();
    			   		 if(j==1 && processNumber[j] == 0)
    			   			 sameArrival[0] += 1;
    			   		}
    			 paths.add(processNumber); // add to arraylist
    			 processNumber = new int[4];// create new row/process
    			
    		   }
        	if (sameArrival[0] == _processes)
        		sameArrival[1] =1;
        	timeQuantum = schedule.charAt(3)-'0';
        	
        	RoundRobin(timeQuantum);
        }
        
        // Shortest Job First/////////////////////////
        else if(schedule.charAt(0) == 'S') {

 		   for(int i = 0; i < processes; i++) {
 			   for(int j=0; j<4; j++) { 
 				   processNumber[j] = scanner.nextInt();
 				  if(j==1 && processNumber[j] == 0)
			   			 sameArrival[0] += 1;
 			   		}
 			 paths.add(processNumber);
 			 processNumber = new int[4];
 			   
 		   }
 		  if (sameArrival[0] == _processes)
      		sameArrival[1] =1;
        	ShortestJobFirst();
        }
        // priority no pre ///////////////////////////////////////////////
        else if(schedule.charAt(0) == 'P' && schedule.charAt(3) == 'n') {
        	for(int i = 0; i < processes; i++) {
 			   for(int j=0; j<4; j++) { 
 				   processNumber[j] = scanner.nextInt();
 				  if(j==1 && processNumber[j] == 0)
			   			 sameArrival[0] += 1;
 			   		}
 			 paths.add(processNumber);
 			 processNumber = new int[4];
 			
 		   }
        	 if (sameArrival[0] == _processes)
           		sameArrival[1] =1;
        	PriorityNoPre();
	}
        
        //Priority with pre ////////////////////////////////////////////////
        else if(schedule.charAt(0) == 'P' && schedule.charAt(3) == 'w') {
        	for(int i = 0; i < processes; i++) {
  			   for(int j=0; j<4; j++) { 
  				   processNumber[j] = scanner.nextInt();
  				 if(j==1 && processNumber[j] == 0)
		   			 sameArrival[0] += 1;
  			   		}
  			 paths.add(processNumber);
  			 processNumber = new int[4];
  			
  		   }
        	 if (sameArrival[0] == _processes)
           		sameArrival[1] =1;
        	PriorityWithPre();
        }
    scanner.close();      
	}
	
////////////////////Get input End////////////////////////////////
	
////////////////////Begin Scheduling methods////////////////////////////

	// Priority Scheduling With Preemption
	private static void PriorityWithPre() {
		ArrayList<int[]> wait = new ArrayList<int[]>();
		
		int processesComplete = 0;
		int totalBurst = 0;
		int totalTime = 0;
		float avgWait = 0;
		
		if(sameArrival[1] == 1) // if all arrived at same time
			 sort(paths, 3); // sort by shortest priority first
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("output.txt");
		
		 PrintWriter printWriter = new PrintWriter(fileWriter);
		 printWriter.print("PR_withPREMP\n");
		
		 if(sameArrival[1] == 1) // if all arrived at same time
			 sort(paths, 3); // sort by shortest priority first
		
		 while (_processes != processesComplete) {
				totalTime += clock;
				for(int i=0; i < _processes; i++) {
					
					if(paths.get(0)[1] != 0 && paths.get(1)[0] != paths.get(0)[0])
						totalBurst += paths.get(0)[2];
				
				printWriter.print(clock);
				printWriter.print("   ");
				printWriter.print(paths.get(i)[0]);
				printWriter.print("\n");
					
				
						
						
						clock += paths.get(i)[2];
						//paths.remove(0);
						processesComplete += 1;
						
					
						
				}
			}
			avgWait += (totalTime - totalBurst)/_processes;
			printWriter.print("AVG Waiting Time: ");
			printWriter.print(avgWait);
		printWriter.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	

	//// Priority Scheduling No Preemption
	private static void PriorityNoPre() {
		int processesComplete = 0;
		
		int totalBurst = 0;
		int totalTime = 0;
		float avgWait = 0;
		
		 FileWriter fileWriter;
			try {
				fileWriter = new FileWriter("output.txt");
			
			 PrintWriter printWriter = new PrintWriter(fileWriter);
			 printWriter.print("PR_noPREMP\n");
		
		 if(sameArrival[1] == 1) // if all arrived at same time
			 sort(paths, 3); // sort by shortest priority first
		
		 
			while (_processes != processesComplete) {
				totalTime += clock;
				for(int i=0; i < _processes; i++) {
					
					if(paths.get(0)[1] != 0 && paths.get(1)[0] != paths.get(0)[0])
						totalBurst += paths.get(0)[2];
				
				printWriter.print(clock);
				printWriter.print("   ");
				printWriter.print(paths.get(i)[0]);
				printWriter.print("\n");
					
				
						
						
						clock += paths.get(i)[2];
						//paths.remove(0);
						processesComplete += 1;
						
					
						
				}
			}
			avgWait += (totalTime - totalBurst)/_processes;
			printWriter.print("AVG Waiting Time: ");
			printWriter.print(avgWait);
			printWriter.close();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	//Shortest Job First
	public static void ShortestJobFirst() {
		int processesComplete = 0;
		int totalBurst = 0;
		int totalTime = 0;
		float avgWait = 0;

		 
		 FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("output.txt");
		
		 PrintWriter printWriter = new PrintWriter(fileWriter);
		 printWriter.print("SJF\n");
		 
		 if(sameArrival[1] == 1) // if all arrived at same time
			 sort(paths, 2); // sort by shortest process first
		 
		while (_processes != processesComplete) {
			totalTime += clock;
				
				if(paths.get(0)[1] != 0 && paths.get(1)[0] != paths.get(0)[0])
					totalBurst += paths.get(0)[2];
			
			printWriter.print(clock);
			printWriter.print("   ");
			printWriter.print(paths.get(0)[0]);
			printWriter.print("\n");
				
			
					
					
					clock += paths.get(0)[2];
					//paths.remove(0);
					processesComplete += 1;
					
				
					
			}
		avgWait += (totalTime - totalBurst)/_processes;
		printWriter.print("AVG Waiting Time: ");
		printWriter.print(avgWait);
		printWriter.close();
	}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }
		
		
	

	private static void RoundRobin(int k) {
		int processesComplete = 0;
		int totalBurst = 0;
		int totalTime = 0;
		float avgWait = 0;
		 
		 FileWriter fileWriter;
		 
		try {
			fileWriter = new FileWriter("output.txt");
		
		 PrintWriter printWriter = new PrintWriter(fileWriter);
		 printWriter.print("RR ");
		 printWriter.print(k);
		 printWriter.print("\n");
		 
		
			 
			
		while (_processes != processesComplete) {
			for(int i = 0; i<_processes;i++) {
				
				
				
				if(paths.get(0)[1] != 0 && clock == 0)
					totalBurst += paths.get(0)[2];
				
				if(paths.get(i)[2] > 0 ) {
				paths.get(i)[2] =paths.get(i)[2]-timeQuantum;
					if(paths.get(i)[2] <= 0) {
						processesComplete +=1;
					}
				
				}
				else 
					break;
			
				
				 printWriter.print(clock);
				 printWriter.print(" ");
				 printWriter.print(paths.get(i)[0]);
				 printWriter.print("\n");
				
				if(paths.get(i)[2] < 0) {
					clock += (timeQuantum + paths.get(i)[2]);
				}
				else
					clock += timeQuantum;
			}
		}
		avgWait += (totalTime - totalBurst)/_processes;
		printWriter.print("AVG Waiting Time: ");
		printWriter.print(avgWait);
		printWriter.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	
	public static void sort(ArrayList<int[]> temp, int i) {
		Collections.sort(temp, new Comparator<int[]>() {
            private int INDEX =i;
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[INDEX], o2[INDEX]);
            }
        });
        
	}
	
}
