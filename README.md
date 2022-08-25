
Algorithms and Complexity                                  August 25, 2022
IST 4310-01
Prof. M. Diaz-Maldonado

Workshop 5: Calculating Best/Average/Worst Case and Time Complexity in a "Determining if there are any repetitons in a plain text file with n ints" Algorithm

Synopsis: Creates a file with n integers (the form they are selected depends on the case we are evaluating), then all those integers are transfered to an array.
The program reads the elements in the array and stores the unique numbers in a different array. With this, it tries to find if there is a duplicate in the text. 
The program ends once a duplicate is found or it gets to the EOF.
This process is done 300 times for each file with n integers in order to find the average runtime for the program given. The elapsed time and # of comparisons
is calculated every time for the 300 repetitions and an average is made. 
Size of n is also increased for every 300 repetitions, in order to analyze how elapsed time and complexity (# of comparisons) vary when n increases.
This is done with all n integers being 0 (best case), n random numbers(average case) and n different numbers (worst case).
Lastly, the program creates a text file to store the 3 values with 3 columns: n size / # of comparisons / runtime.

Authors: Santiago Andrés Mercado Barandica y David Salgado Cortés
ID: 200155614 / 200144831
