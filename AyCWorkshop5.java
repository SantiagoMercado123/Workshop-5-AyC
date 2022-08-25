/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.aycworkshop5;

/*
 * Algorithms and Complexity                                  August 25, 2022
 * IST 4310-01
 * Prof. M. Diaz-Maldonado
 *
 * Workshop 5: Calculating Best/Average/Worst Case and Time Complexity in a "Determining if there are any repetitons in a plain text file with n ints" Algorithm
 *
 * Synopsis: Creates a file with n integers (the form they are selected depends on the case we are evaluating), then all those integers are transfered to an array.
 * The program reads the elements in the array and stores the unique numbers in a different array. With this, it tries to find if there is a duplicate in the text. 
 * The program ends once a duplicate is found or it gets to the EOF.
 * This process is done 300 times for each file with n integers in order to find the average runtime for the program given. The elapsed time and # of comparisons
 * is calculated every time for the 300 repetitions and an average is made. 
 * Size of n is also increased for every 300 repetitions, in order to analyze how elapsed time and complexity (# of comparisons) vary when n increases.
 * This is done with all n integers being 0 (best case), n random numbers(average case) and n different numbers (worst case).
 * Lastly, the program creates a text file to store the 3 values with 3 columns: n size / # of comparisons / runtime.
 * 
 * Authors: Santiago Andrés Mercado Barandica y David Salgado Cortés
 * ID: 200155614 / 200144831
*/


import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
public class AyCWorkshop5 {
static int[] list; //array containing the random numbers.
    static int[] quantity; //array that counts the quantity of each unique number.
    static int[] numbers; //array containing numbers found.
    static int size; //total size of file/"list" array.
    static int counter; //Counts number of comparisons
    static int option; //Variable to differentiate best, average and worst case.
    static int actualsize; //size currently used in "numbers" and "quantity" array.
    static LinkedList<Integer> runtimes; //List to store the runtimes for each n integers.
    static LinkedList<Integer> is; //List to store the amount of integers in the iteration.
    static LinkedList<Integer> comparisons; //List to store the amount of comparisons for each n integers.
	public static void main (String[] args)
	{
            Cases(20000, 300, 1); //Best Case (200000 max amount of n integers, 300 repetitions).
            Cases(5000, 300, 2); //Average Case (5000 max amount of n integers, 300 repetitions). If n is bigger, elapsed time will overflow the variable.
            Cases(5000, 300, 3); //Worst Case (5000 max amount of n integers, 300 repetitions).
	}

	// implementations:
        
        private static void Cases(int ii, int jj, int opti){ //(ii is the amount of integers, jj is the amount of repetitions, opti is the case we are evaluating).
            runtimes = new LinkedList<Integer>();
                is = new LinkedList<Integer>();
                comparisons = new LinkedList<Integer>(); //We initialize all 3 lists.
                int factor;
                if(opti == 1){
                    factor = 6;
                } else {
                    factor = 5; //i grows times 3/2 (1.5) instead of 2 in order to get more data in worst and average case.
                }
                
                for (int i=32; i<ii; i=i*factor/4){ 
                    counter = 0;
                    long total = 0;
                    for(int j=0; j<jj; j++){
                        Initialize(i, opti); //Realize every procedure besides the counting of integers as to not mix the runtimes.
                        long startTime = System.nanoTime();
                        Count(); //We do the counting of each integer.
                        long endTime   = System.nanoTime();
                        long totalTime = endTime - startTime; //We calculate the runtime for each repetition and add it to the previous ones..
                        total = total + totalTime;
                    }
                   counter = counter/jj; //We find the average comparisons per iteration and store it in a list.
                   total = total/jj; //Average runtime
                   if (total < 0) total = total*-1;
                   runtimes.add((int)total);
                   is.add(i); //Amount of integers in the array
                   comparisons.add(counter);
                }
                switch (opti){ //We create a file with 3 columns: n, comparisons, runtime and name it according to the case.
                    case 1:
                        create("BestCase.txt");
                        write("BestCase.txt", is.size());
                    break;
                
                    case 2:
                        create("AverageCase.txt");
                        write("AverageCase.txt", is.size());
                    break;
                    
                    case 3: 
                        create("WorstCase.txt");
                        write("WorstCase.txt", is.size());
                }
		
        }
        
        private static void Initialize(int s, int opc){ //Executes procedures needed to execute the program.
                size = s; //We assign the size and case we are going to be using for the iteration.
                option = opc;
                create("data.txt");	// creates a file
		write();	// writes data to the file
		read();		// reads data in the file
		store();	// stores data in an array
        }
        
        private static void Count(){ //Counts each time a unique number appears in an array.
           quantity = new int[size]; //We create an array that counts the amount of times a digit [0,9] appears in the "list" array.
           numbers = new int[size]; //We do the same for a array that will contain the different numbers in the "list" array.
           for (int i=0; i<size; i++){ //We initialize the counter array in 0.
               quantity[i] = 0;
               numbers[i]=0;
           }
           boolean dupefound = false;
           quantity[0] = 1;
           numbers[0] = list[0];  //We initialize in position 1 instead of 0, and add the value of pos 0 to the numbers array.
           actualsize=1;
                        
                        int x;
                        int z=1;
			while (z<size && dupefound == false){ // reads every integer in the list array until it finds a duplicate or until there are no more integers left.
			  x = list[z];
                          z++;
                          int pos = 0;
                          boolean keep = true;
                          while(keep==true && pos < actualsize){ //We check if the int is already in the numbers array, if it is we add 1 to its counterpart in the "quantity" array
                              if(numbers[pos]==x){
                                  counter++; //Everytime we make a comparison, we increase the counter.
                                  quantity[pos]++;
                                  keep = false;
                                  dupefound = true;
                              } else {
                                  counter++;
                                  pos++;
                              }
                          }
                          if (pos == actualsize && keep == true){ //If we find a new number, we add it to the "numbers" array, increase the coumter in the "quantity" array and increase actualsize.
                              counter++;
                              numbers[actualsize]=x;
                              quantity[actualsize]++;
                              actualsize++;
                          } else {
                              counter++;
                          }
			}
            
           return;
        }
        
        private static void PrintResult(){ //Prints the reulting unique numbers and their appearances in the file.
            System.out.println("  Numbers   |   Appearences");
            System.out.println(" ");
            for (int i=0; i<actualsize; i++){
                System.out.println("      " + numbers[i]+"     |   "+quantity[i]);
                System.out.println("  ----------|--------------"); //Decoration
            }
        }
        
        
	private static void create (String name)
	// creates a file with a given name
	{
		try
		{
			// defines the filename
			String fname = (name);
			// creates a new File object
			File f = new File (fname);
                        
			String msg = "creating file `" + fname + "' ... ";
			// creates the new file
			f.createNewFile();

		}
		catch (IOException err)
		{
			// complains if there is an Input/Output Error
			err.printStackTrace();
		}

		return;
	}


        private static void write (String name, int tm)
	// writes data to a file with a given name and size
	{
		try
		{
			// defines the filename
			String filename = (name);
			// creates new PrintWriter object for writing file
			PrintWriter out = new PrintWriter (filename);
                        String fmt = ("%10s %10s %10s\n"); //We store 3 values in different columns per line: amount of integers(n), comparisons and runtime.
                            for (int i = 0; i < tm; ++i){
                                out.printf(fmt, is.get(i), comparisons.get(i), runtimes.get(i));
                            }
			

			out.close();	// closes the output stream
		}
		catch (FileNotFoundException err)
		{
			// complains if file does not exist
			err.printStackTrace();
		}

		return;
	}
        
	private static void write ()
	// writes data to a file
	{
		try
		{
			// defines the filename
			String filename = ("data.txt");
			// creates new PrintWriter object for writing file
			PrintWriter out = new PrintWriter (filename);

			//size = 250;
                        Random rand = new Random();
                        if (option == 1){ //Depending on the case, we use n amount of 0s for the best case, n random numbers for average case and n different numbers for worst case
                            for (int i = 0; i < size; ++i)
				out.printf("%d\n", 0);
                        } else {
                            if (option == 2){
                              for (int i = 0; i < size; ++i)
				out.printf("%d\n", rand.nextInt(size));  
                            } else {
                                for (int i=0; i<size; i++){
                                  out.printf("%d\n", i);  
                                }
                            }
                        }
			

			out.close();	// closes the output stream
		}
		catch (FileNotFoundException err)
		{
			// complains if file does not exist
			err.printStackTrace();
		}

		return;
	}


	private static void read ()
	// reads the file contents and prints them to the console
	{
		// defines the filename
		String filename = ("data.txt");
		// creates a File object
		File f = new File (filename);
		try
		{
			// attempts to create a Scanner object
			Scanner in = new Scanner (f);


			int x;
			int count = 0;
			// reads integers in file until EOF
			while ( in.hasNextInt() )
			{
				// reads  number and prints it
				x = in.nextInt();
				++count;
			}

			String msg = ("%d numbers have been read\n");

			in.close();	// closes the input stream

		}
		catch (FileNotFoundException err)
		{
			// complains if file does not exist
			err.printStackTrace();
		}


		return;
	}


	private static void store ()
	// stores the file contents into an array and prints the array
	{
		String filename = "data.txt";
		File f = new File (filename);

		try
		{
			Scanner in = new Scanner (f);
                         
			// allocates list for storing the numbers in file
			list = new int [size];                   
			for(int i=0; i<size; i++){
                           list[i] = in.nextInt();
                        }
                        
                        

		}
		catch (FileNotFoundException err)
		{
			// complains if file does not exist
			err.printStackTrace();
		}


		return;
	}


	private static int lines (String filename)
	// counts number of lines (or records) in a file
	{

		int count = 0;
		// creates a File object
		File f = new File (filename);
		try
		{
			// attempts to create a Scanner object
			Scanner in = new Scanner (f);

			// reads integers in file until EOF for counting
			while ( in.hasNextInt() )
			{
				in.nextInt();
				++count;
			}

			in.close();	// closes the input stream
		}
		catch (FileNotFoundException err)
		{
			// complains if file does not exist
			err.printStackTrace();
		}

		return count;
	}
}
