#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>
struct polynomial
{
    int coefficients[100];
    int exponents[100];
    int n; // n is number of terms
};

struct polynomial initPoly(char poly[]);
void addPolynomials(struct polynomial p1, struct polynomial p2);
struct polynomial sort(struct polynomial poly);
void printPolynomial(struct polynomial poly);
void subtractPolynomials(struct polynomial p1, struct polynomial p2);

int main()
{
    char poly[100];

    // Initializes polynomial 1
    printf("Enter a polynomial (ex: 2x^2 + -5x + 2): ");
    fgets(poly, 100, stdin);
    struct polynomial poly1 = initPoly(poly);

    // Initializes polynomial 2
    printf("Enter second polynomial: ");
    fgets(poly, 100, stdin);
    struct polynomial poly2 = initPoly(poly);
    int input;

    //Driver Program
    while(1){
        printf("\n(1) Addition\n(2) Subtraction\n(3) Enter two new polynomials\n(4) Exit\n");
        scanf("%d", &input);
        switch(input) {
            case 4:
                printf("Exiting program...\n");
                return 0;
            case 1:
                addPolynomials(poly1, poly2);               
                break;
            case 2:
                subtractPolynomials(poly1, poly2);
                break;
            case 3:
                printf("Enter a polynomial (ex: 2x^2 + -5x + 2): ");
		scanf("%*c");
                fgets(poly, 100, stdin);
                poly1 = initPoly(poly);
                printf("Enter second polynomial: ");
                // fflush(stdin);
                fgets(poly, 100, stdin);
                poly2 = initPoly(poly);
                break;
            default:
                printf("invalid choice\n");
                return 1;
        }

    }
    return 0;
}



struct polynomial initPoly(char poly[])
{
    struct polynomial poly1;
    poly1.n = 0;
    char *p = strtok(poly, "+- ");
    while (p != NULL)
    {
        // Find the exponent
        char *endptr = NULL;
        int exponent = strtol(p, &endptr, 10);
        if (*endptr == 'x')
        {
            // There is an exponent, so move endptr past the 'x' character
            endptr++;
            if (*endptr == '^')
            {
                // There is an exponent value, so move endptr past the '^' character
                endptr++;
                exponent = strtol(endptr, NULL, 10);
            }
            else
            {
                // The exponent is assumed to be 1
                exponent = 1;
            }
        }
        else
        {
            // There is no 'x' variable, so the exponent is 0
            exponent = 0;
        }
        poly1.exponents[poly1.n] = exponent;

        // Find the coefficient
        int coefficient = 1;
        if (p > poly && (*(p - 1) == '-' || *(p - 1) == '+'))
        {
            // There is a sign before the coefficient
            if (*(p - 1) == '-')
            {
                coefficient = -1;
            }
        }
        if (isdigit(p[0]))
        {
            // Checks for negative or positive number
            coefficient *= strtol(p, NULL, 10);
        }
        poly1.coefficients[poly1.n] = coefficient;

        poly1.n++;
        p = strtok(NULL, "+- ");
    }
    return poly1;
}

void addPolynomials(struct polynomial p1, struct polynomial p2)
{
    // sorts from highest to lowest exponent
    //(probably unnecessary but is included to avoid unexpected results)
    p1 = sort(p1);
    p2 = sort(p2);

    // addplus is meant to track which is the first term
    // (lazy solution to avoid printing a plus sign before the first term)
    int index, index2, addplus = 0;
    printf("\n");
    for (index = 0; index < p1.n; index++)
    {
        for (index2 = 0; index2 < p2.n; index2++)
        {
            if (p1.exponents[index] == p2.exponents[index2])
            {
                if(addplus == 0){
                    printf("%dx^%d", (p1.coefficients[index] + p2.coefficients[index2]), p1.exponents[index]);
                    addplus = 1;
                } else {
                    printf("+%dx^%d", (p1.coefficients[index] + p2.coefficients[index2]), p1.exponents[index]);
                }
                p1.coefficients[index] = 0;
                p2.coefficients[index2] = 0;
            }
        }
    }
    for(index = 0; index < p1.n; index++)
    {
        if(p1.coefficients[index] != 0)
        {
            if(addplus == 0){
                 printf("%dx^%d", p1.coefficients[index], p1.exponents[index]);
                addplus = 1;
            } else {
                 printf("+%dx^%d", p1.coefficients[index], p1.exponents[index]);
            }
        }
    }

    for (index = 0; index < p2.n; index++)
    {
        if (p2.coefficients[index] != 0)
        {
            printf("+%dx^%d", p2.coefficients[index], p2.exponents[index]);
        }
    }
    printf("\n");
}

struct polynomial sort(struct polynomial poly){
    int i, j, temp;
    for(i = 0; i < poly.n; i++){
        for(j = i + 1; j < poly.n; j++){
            if(poly.exponents[i] < poly.exponents[j]){
                temp = poly.exponents[i];
                poly.exponents[i] = poly.exponents[j];
                poly.exponents[j] = temp;
                temp = poly.coefficients[i];
                poly.coefficients[i] = poly.coefficients[j];
                poly.coefficients[j] = temp;
            }
        }
    }
    return poly;
}

void printPolynomial(struct polynomial poly){
    printf("Coefficients: ");
    for (int i = 0; i < poly.n; i++)
    {
        printf("%d ", poly.coefficients[i]);
    }
    printf("\nExponents: ");
    for (int i = 0; i < poly.n; i++)
    {
        printf("%d ", poly.exponents[i]);
    }
}

void subtractPolynomials(struct polynomial p1, struct polynomial p2) {

    // distribute a negative 1 to second polynomial
    // adding the inverse of the second is equivalent to subtracting
    for(int i = 0; i < p2.n; i++){
        p2.coefficients[i] = p2.coefficients[i] * -1;
    }

    addPolynomials(p1, p2);
}
