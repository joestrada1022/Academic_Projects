Exercise 1

Use the bisection method to find roots for the following functions on the intervals indicated:

f(x) = x^3 + 3x - 1, on [0,1]
g(x) = x^3 - 2 sin x, on [0.5,2]
h(x) = x + 10 - x cosh(50/x), on [120,130]

Exercise 2

Use Newton's method to find a root for the following function:

f(x) = x^3 + 2x^2 + 10x - 20, starting with x_0 = 2

Exercise 3

Use the secant Method to find a root for the function:

f(x) = x^3 + 2x^2 + 10x -20, with x_0 = 2, and x_1 = 1.

Exercise 4

Write a program, in the language of your preference, that computes roots for polynomials. The program should take as input a file which contains description of a polynomial in the following format:

n
a(n) a(n-1) a(n-2) ... a(2) a(1) b

where n is the degree of the polynomial, a(i)  is the coefficient of the monomial of degree i, and b is the constant term. For example, the polynomial:

3x^3 + 5x^2 - 7

would be represented as:

3
3  5  0 -7

The file should have extension .pol, for example, fun1.pol would be a suitable name for a file. The program should use bisection method by default and should place the solution in a file with the same name as the input, but with extension .sol (such as fun1.sol), with format:

root  iterations outcome

where root is the last root approximation, iterations is the total number of iterations performed by the algorithm, and outcome should be one of success if the algorithm reached convergence, or fail if the algorithm didn't converge.

For all algorithms, the error tolerance should be IEEE 754 single precision. You should implement the following algorithms:

Bisection method.
Newton's Method.
Secant Method.
Hybrid: starts with bisection for early iterations and switches to newton's.
The program should use bisection as default and operate as follows:

> polRoot [-newt, -sec, -hybrid] [-maxIt n] initP [initP2] polyFileName

By default the program uses bisection, but this can be modified with -newt for Newton's or -sec for Secant. The program should attempt 10,000 iterations by default, but this can be modified with -maxIter and the number of desired iterations. The initial point is provided (or an extra point for bisection and secant). For example, to run bisection method on file fun1.pol, with initial points 0 and 1:

> polRoot 0 1 fun1.pol

to run newton's with initial point 0:

> polRoot -newt 0 fun1.pol

and to run secant, with initial points 0 and 1, for 100,000 iterations:

> polRoot -sec -maxIter 100000 0 1 fun1.pol

Exercise 5

Use your program to compute solutions for all polynomial functions depicted in the text of this assignment.
