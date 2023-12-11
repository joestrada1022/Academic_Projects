Consider the following data points:

(3,1), (1,0.12), (0,-0.3),(4,2),(7,2.5)

Exercise 1

Construct an interpolation polynomial using the Lagrange's Interpolation method, and evaluate at x=2.

Exercise 2

Construct an interpolation polynomial using the Newton's Interpolation method, and evaluate at x=2.

Exercise 3

Write a program, in the language of your preference, that is used to compute Newton's interpolation. The program should take as input a file that contains the data points in the following format:

x0  x1  x2  ...  xn
y0  y1  y2  ...  yn

that is, the first rows contains the first element of each data point, and the next row the second one. After processing the input, the program will provide a prompt asking of a value to be used to evaluate the polynomial and print the result of such evaluation, going back to the prompt. Entering 'q' instead of a real number will exit the program. Use your program to with the example in the exercises above to check your solution.

Exercise 4

Create a program that takes a positive integer 'n' as input the produces a random data set file with 'n' points, in the format of the previous exercise. Use this program to create data sets with 10, 100, and 1000 points and run your program with these data sets. Evaluate at random points. Report on the time it takes to compute interpolation and evaluate in each instance.

Exercise 5

Compute numerical derivatives for the following functions at x = 3.5467

sin(x), 1 + ln x, x^2 - 3x + 5

Use the first approximation: f'(x) = (f(x + h) - f(x - h)) / 2h (with h = 0.0001)

Then refine that value by halving h and re-computing approximation (5 iterations).

Finally compute derivative using Richardson's interpolation, where: D(i, 0) = (f(x + h) - f(x - h)) / 2h(i) , h(i) = h / 2^i , D(i, j) = D(i, j - 1) + (D(i, j - 1) - D(i - 1, j - 1)) / (4^j - 1), 1 <= i <= j <= N

Use N = 5. Report on which value in the resulting matrix is the most accurate.

Excerise 6

For the same functions listed above, compute numerical integrations between points a = 1, and b = 5.

First use trapezoid method for a total 4 iterations. Compare to actual values of integrals.

The use Richardson's extrapolation, with R(0,0) = ((f(a) + f(b)) / 2) * (b - a) and R(i, 0) = R(i - 1, 0) / 2 + h sum(k = 1, 2^(i - 1), f(a + (2k - 1)h)), and
R(i,j) = R(i, j - 1) + (R(i, j - 1) - R(i - 1, j - 1)) / (4^j - 1), 1 <= i <= j <= N

Use N = 5. Report on which value on the resulting matrix is most accurate.
