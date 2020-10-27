package com.mshernandez.mm_analysis;

/**
 * A matrix multiplier using a divide & conquer method.
 */
public class DivideConquerMatrixMultiplier extends MatrixMultiplier
{
    /**
     * Initializes this matrix multiplier with
     * the name of the algorithm used.
     */
    public DivideConquerMatrixMultiplier()
    {
        super("Divide & Conquer");
    }

    /**
     * Multiplies two matrices A and B with the
     * assumption that A and B are both n*n matrices
     * using a divide & conquer method.
     * 
     * @param a The first matrix in the operation.
     * @param b The second matrix in the operation.
     * @return The resulting matrix.
     */
    @Override
    public int[][] multiplySquareMatrices(int[][] a, int[][] b)
    {
        int size = a.length;
        int[][] result = new int[size][size];
        multiply(a, 0, 0, b, 0, 0, result, 0, 0, result.length);
        return result;
    }

    private void multiply(int[][] a, int aRowPtr, int aColPtr,
                          int[][] b, int bRowPtr, int bColPtr,
                          int[][] c, int cRowPtr, int cColPtr, int size)
    {
        // Base Case
        if (size <= 2)
        {
            for (int rPos = 0; rPos < 2; rPos++)
            {
                for (int cPos = 0; cPos < 2; cPos++)
                {
                    int sum = 0;
                    for (int i = 0; i < 2; i++)
                    {
                        sum += a[aRowPtr + rPos][aColPtr + i]
                               * b[bRowPtr + i][bColPtr + cPos]; 
                    }
                    c[cRowPtr + rPos][cColPtr + cPos] += sum;
                }
            }
            return;
        }
        // Divide & Conquer
        int dividedSize = size / 2;
        // C00 = A00*B00 + A01*B10
        multiply(a, aRowPtr,               aColPtr,               // A00
                 b, bRowPtr,               bColPtr,               // B00
                 c, cRowPtr,               cColPtr,               // C00
                 dividedSize);
        multiply(a, aRowPtr,               aColPtr + dividedSize, // A01
                 b, bRowPtr + dividedSize, bColPtr,               // B10
                 c, cRowPtr,               cColPtr,               // C00
                 dividedSize);
        // C01 = A00*B01 + A01*B11
        multiply(a, aRowPtr,               aColPtr,               // A00
                 b, bRowPtr,               bColPtr + dividedSize, // B01
                 c, cRowPtr,               cColPtr + dividedSize, // C01
                 dividedSize);
        multiply(a, aRowPtr,               aColPtr + dividedSize, // A01
                 b, bRowPtr + dividedSize, bColPtr + dividedSize, // B11
                 c, cRowPtr,               cColPtr + dividedSize, // C01
                 dividedSize);
        // C10 = A10*B00 + A11*B10
        multiply(a, aRowPtr + dividedSize, aColPtr,               // A10
                 b, bRowPtr,               bColPtr,               // B00
                 c, cRowPtr + dividedSize, cColPtr,               // C10
                 dividedSize);
        multiply(a, aRowPtr + dividedSize, aColPtr + dividedSize, // A11
                 b, bRowPtr + dividedSize, bColPtr,               // B10
                 c, cRowPtr + dividedSize, cColPtr,               // C10
                 dividedSize);
        // C11 = A10*B10 + A11*B11
        multiply(a, aRowPtr + dividedSize, aColPtr,               // A10
                 b, bRowPtr,               bColPtr + dividedSize, // B01
                 c, cRowPtr + dividedSize, cColPtr + dividedSize, // C11
                 dividedSize);
        multiply(a, aRowPtr + dividedSize, aColPtr + dividedSize, // A11
                 b, bRowPtr + dividedSize, bColPtr + dividedSize, // B11
                 c, cRowPtr + dividedSize, cColPtr + dividedSize, // C11
                 dividedSize);
        return;
    }
}