Consider the following linear system:

3x1 + 4x2 + 3x3 = 10
x1 + 5x2 - x3 = 7
6x1 + 3x2 + 7x3 = 15

Exercise 1

Solve the system using Naive Gaussian Elimination.

Exercise 2

Solve the system using Gaussian Elimination with Scaled Partial Pivoting.

Exercise 3

Write a program, in the language of your preference, that computes solutions for linear systems. The program should take as input a file which contains data for a linear system in the following format:

n
a11 a12 ... a1n
a21 a22 ... a2n
...
an1 an2 ... ann
b1   b2   ... bn

The file should have extension .lin, for example, sys1.lin could a suitable file name. The program should use Naive Gaussian Elimination by default and should place the solution in a file with the same name as the input, but with extension .sol and with the following format:

x1 x2 ... xn

Additionally, the user should be able to modify the programs behavior with optional flag --spp, in which case the program will use Scaled Partial Pivoting to produce the solution. For example, for a system placed in file sys1.lin, the user could run:

> gaussian sys1.lin

or,

> gaussian --spp sys1.lin

In the first case the program will use NGE, and in the second it will use SPP. In both cases, the solution will be placed in file sys1.sol.

Exercise 4

Using your program, compute solutions for the following system:

4
0.0001  -5.0300  5.8090  7.8320
2.2660   1.9950  1.2120  8.0080
8.8500   5.6810  4.5520  1.3020
6.7750  -2.2530  2.9080  3.9700
9.5740   7.2190  5.7300  6.2910

Run your program in both modes. Compare and discuss your results.

---

For exercises 1 and 2, create PDF with your written solutions. For exercises 3 and 4, turn in a source code and other documents. Submit .zip file containing all materials.
