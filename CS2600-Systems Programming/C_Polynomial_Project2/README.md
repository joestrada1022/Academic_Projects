This problem gives you further practice with arrays and pointers as well as string functions from string.h and stdlib.h libraries.

Develop a C program that works with Polynomials. The internal representation of a polynomial is an array: each element that stores the coefficients and the index of the array determines the exponents. Have the user enter a string as follows for the polynomial (^ refers to the power of)

 

                  -4x^0 + x^1 + 4x^3 - 4x^5 - 3x^7

 

 this polynomial has the second element (exponent 1) of coefficient array store 1. Similarly the third term is stored as coefficient 4 in the 4th element (index and exponent 3). The third element for exponent 2 has a coefficient of 0 since the term doesn't exist. One can figure out the number of terms in this polynomial by tokenizing the string and looking for substrting x^. Once you know the number of terms, use that to allocate two integer arrays, one that stores the coefficient and the other stores the exponent. So, for the polynomial above, the coefficient and exponent arrays will be as follows:

-4

1

0

-4

-3

 

0

1

3

5

 7

 

The driver program (main part of the program) should ask the user to enter two polynomials (specify to the user how the polynomial should be entered, use the specified format above) and then give a choice to do the following:

(1) Addittion

(2) Subrtaction

(3) Enter two new polynomials

(4) Exit

Make sure to print the result of the operation on the console in the same format for the polynomial as was entered by the user.

PS: If you are struggling and unable to parse the input string to extract the number of terms and coefficient and exponent of each term, then simply ask the user for the number of terms to allocate memory for the two arrays. Then ask the user for the coefficient and exponent for each term and store it in the dynamic array of integers. You will loose 10% if you are unable to get the input from the string, but at least you will get 80% if your program works properly. 
