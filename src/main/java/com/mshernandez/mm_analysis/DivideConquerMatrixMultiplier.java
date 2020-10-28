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
        mul(a,      0, 0,
            b,      0, 0,
            result, 0, 0,
            result.length);
        return result;
    }

    private void mul(int[][] a, int aRowPtr, int aColPtr,
                     int[][] b, int bRowPtr, int bColPtr,
                     int[][] c, int cRowPtr, int cColPtr, int size)
    {
        /**
         * Base Case
         * Note: To avoid the creation of unnecessary arrays
         *       the base case will not only multiply the 2x2
         *       arrays together but will also directly add the
         *       results into the result matrix.
         */
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
        int quadrantSize = size / 2;
        // C11 = A11*B11 + A12*B21
        mul(a, aRowPtr,                aColPtr,                // A11
            b, bRowPtr,                bColPtr,                // B11
            c, cRowPtr,                cColPtr,                // C11
            quadrantSize);
        mul(a, aRowPtr,                aColPtr + quadrantSize, // A12
            b, bRowPtr + quadrantSize, bColPtr,                // B21
            c, cRowPtr,                cColPtr,                // C11
            quadrantSize);
        // C12 = A11*B12 + A12*B22
        mul(a, aRowPtr,                aColPtr,                // A11
            b, bRowPtr,                bColPtr + quadrantSize, // B12
            c, cRowPtr,                cColPtr + quadrantSize, // C12
            quadrantSize);
        mul(a, aRowPtr,                aColPtr + quadrantSize, // A12
            b, bRowPtr + quadrantSize, bColPtr + quadrantSize, // B22
            c, cRowPtr,                cColPtr + quadrantSize, // C12
            quadrantSize);
        // C21 = A21*B11 + A22*B21
        mul(a, aRowPtr + quadrantSize, aColPtr,                // A21
            b, bRowPtr,                bColPtr,                // B11
            c, cRowPtr + quadrantSize, cColPtr,                // C21
            quadrantSize);
        mul(a, aRowPtr + quadrantSize, aColPtr + quadrantSize, // A22
            b, bRowPtr + quadrantSize, bColPtr,                // B21
            c, cRowPtr + quadrantSize, cColPtr,                // C21
            quadrantSize);
        // C22 = A21*B12 + A22*B22
        mul(a, aRowPtr + quadrantSize, aColPtr,                // A21
            b, bRowPtr,                bColPtr + quadrantSize, // B12
            c, cRowPtr + quadrantSize, cColPtr + quadrantSize, // C22
            quadrantSize);
        mul(a, aRowPtr + quadrantSize, aColPtr + quadrantSize, // A22
            b, bRowPtr + quadrantSize, bColPtr + quadrantSize, // B22
            c, cRowPtr + quadrantSize, cColPtr + quadrantSize, // C22
            quadrantSize);
        return;
    }
}