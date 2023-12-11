#include <stdio.h>
#include <stdlib.h>

struct matrix
{
    float **m;
    int rows, columns; // m rows and n columns
};

struct matrix initializeMatrix();
struct matrix multiply(struct matrix m1, struct matrix m2);
void printMatrix(struct matrix aMatrix);
void deallocateMatrix(struct matrix aMatrix);

int main(int argc, char **argv)
{

    int i, j;
    struct matrix m1, m2, m3;

    // Initializes and prints both matrices
    m1 = initializeMatrix();
    m2 = initializeMatrix();

    // Multiplies the two matrices and stores the product in m3
    m3 = multiply(m1, m2);

    printf("\nMatrix 1 is:\n");
    printMatrix(m1);
    printf("\nMatrix 2 is:\n");
    printMatrix(m2);


    printf("\nProduct of two matrices is:\n");
    printMatrix(m3);

    // Deallocates the memory of each matrix that was allocated
    deallocateMatrix(m1);
    deallocateMatrix(m2);
    deallocateMatrix(m3);

    return 0;
}

struct matrix initializeMatrix()
{
    struct matrix matrix1;
    int row, col, i, j;
    printf("\nEnter the size of the matrix: ");
    scanf("%d %d", &matrix1.rows, &matrix1.columns);
    matrix1.m = (float **)malloc(matrix1.rows * sizeof(float *));
    if (matrix1.m == NULL)
    {
        printf("out of memory\n");
        exit(1);
    }
    for (i = 0; i < matrix1.rows; i++)
    {
        *(matrix1.m + i) = (float *)malloc(matrix1.columns * sizeof(float));
        if (matrix1.m == NULL)
        {
            printf("out of memory\n");
            exit(1);
        }
    }
    printf("Enter %d numbers: ", matrix1.rows * matrix1.columns);
    for (i = 0; i < matrix1.rows; i++)
    {
        for (j = 0; j < matrix1.columns; j++)
        {
            scanf("%f", &matrix1.m[i][j]);
        }
    }
    return matrix1;
}

void printMatrix(struct matrix aMatrix)
{
    int i, j;
    for (i = 0; i < aMatrix.rows; i++)
    {
        for (j = 0; j < aMatrix.columns; j++)
        {
            printf("%.2f\t", aMatrix.m[i][j]);
        }
        printf("\n");
    }
}

void deallocateMatrix(struct matrix aMatrix)
{
    int i, j;
    for (i = 0; i < aMatrix.rows; i++)
        free(aMatrix.m[i]);
    free(aMatrix.m);
}

struct matrix multiply(struct matrix m1, struct matrix m2)
{
    struct matrix m3;
    m3.rows = m1.rows;
    m3.columns = m2.columns;
    int i, j, k;

    // initializes m3 matrix
    m3.m = (float **)malloc(m3.rows * sizeof(float *));
    if (m3.m == NULL)
    {
        printf("out of memory\n");
        exit(1);
    }
    for (i = 0; i < m3.rows; i++)
    {
        *(m3.m + i) = (float *)malloc(m3.columns * sizeof(float));
        if (m3.m == NULL)
        {
            printf("out of memory\n");
            exit(1);
        }
    }

    if (m1.columns != m2.rows)
    {
        printf("\nMatrix Sizes are incorrect. Exiting program...");
        exit(1);
    }

    for (i = 0; i < m3.rows; i++)
    {
        for (j = 0; j < m3.columns; j++)
        {
            m3.m[i][j] = 0;
        }
    }

    for (i = 0; i < m1.rows; i++)
    {
        for (j = 0; j < m2.columns; j++)
        {
            for (k = 0; k < m1.columns; k++)
            {
                m3.m[i][j] += m1.m[i][k] * m2.m[k][j];
            }
        }
    }

    return m3;
}
