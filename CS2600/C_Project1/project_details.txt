This project has 2 separate C programs for you to write. The points for problem 1 is 30, Problem 2 is  70.

 

1. This project helps you explore some functions from the ctype.h library. It also helps you master opening,closing and reading from files. You will use functions such as ispunct(), isalpha() etc. from this library.

Write a C program that can open and read any user provided text file (Ask user for file name). If you wish, you may read the file line by line or character by character. Then print out the number of occurrences of each of the vowels a, e, i, o and u in the text, the total number of consonants, the total number of punctuations and and then print out each of these items as a % of the total number of characters in the files (ignore blank spaces and any new line characters). Also output these values into a file called output.txt. Desired output format is as follows (the numbers in the output below are just being used as an example):

Percentages of various characters:

a  10% ; e   4% ; i   3% ; o   4% ; u   0% ; consonants  67% ; punct 12%

2. This program gets you comfortable with pointers. Write a C program that creates a structure called matrix. The members of this structure are a float pointer to pointer for creating a 2D array, and two integers that are the number of rows and columns of this matrix.

struct matrix {

float **m ; // pointer to a pointer for storing the 2D array

int m,n; //m rows and n columns

}

The program should allow the user to enter the size and elements of the two matrices in this format. e.g. if the size entered by the user is 2x3 (2 rows and 3 columns), the 6 elements can be entered row wise in a single line like this:

1 4 -5 4 2 0

this is the matrix   1 4 -5

                              4 2  0

Have the users enter two matrices and write a function that takes these matrices and returns the multiplication of these two matrices. Print out the resulting matrix in the main() program. Please make sure that the matrices have the correct size to be able to multiply them. If incorrect sizes are entered, the program should let the user know that incorrect size was entered and then exit. The function prototype will look like this:

matrix multiply(matrix, matrix) ;

 

Submission: Please upload both the .c source codes. Also, upload a PDF/Word document that has the screenshots of both your program execution. For the matrices, show the execution with two different matrix sizes as well as multiplying two square matrices.

Extra credit: Time your function that does matrix multiplication by using the time() function from time.h library. Print out the time taken as well. Explore and comment in a one page report what happens when the size of matrices (nxn) you are multiplying keeps increasing.
