package com.mshernandez.mm_analysis;

/**
 * A matrix multiplier using Strassens algorithm.
 */
public class StrassensMatrixMultiplier extends MatrixMultiplier
{
    /**
     * Initializes this matrix multiplier with
     * the name of the algorithm used.
     */
    public StrassensMatrixMultiplier()
    {
        super("Strassens");
    }

    /**
     * Multiplies two matrices A and B with the
     * assumption that A and B are both n*n matrices
     * using Strassens method.
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
        multiply(a, 0, 0,
                 b, 0, 0,
                 result, 0, 0);
        return result;
    }

    private void multiply(int[][] a, int aRowPtr, int aColPtr,
                          int[][] b, int bRowPtr, int bColPtr,
                          int[][] c, int cRowPtr, int cColPtr)
    {
        // Base Case
        if (c.length <= 2)
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
                    c[cRowPtr + rPos][cColPtr + cPos] = sum;
                }
            }
            return;
        }
        // Recursive Calls
        int quadrantSize = c.length / 2;
        // Need At Least 3 Temporary Matrices For Operations
        int[][] t1 = new int[quadrantSize][quadrantSize];
        int[][] t2 = new int[quadrantSize][quadrantSize];
        int[][] t3 = new int[quadrantSize][quadrantSize];
        
        // Calculate P
        // t1 = A11 + A22
        add(a,  aRowPtr,                aColPtr,                // A11
            a,  aRowPtr + quadrantSize, aColPtr + quadrantSize, // A22
            t1, 0,                      0,                      // (A11 + A22)
            quadrantSize, false);
        // t2 = B11 + B22
        add(b,  bRowPtr,                bColPtr,                // B11
            b,  bRowPtr + quadrantSize, bColPtr + quadrantSize, // B22
            t2, 0,                      0,                      // (B11 + B22)
            quadrantSize, false);
        // P = t3 = (A11 + A22)(B11 + B22)
        multiply(t1, 0, 0,  // (A11 + A22)
                 t2, 0, 0,  // (B11 + B22)
                 t3, 0, 0); // (A11 + A22)(B11 + B22)
        // Add P To C11 & C22 Immediately So Value Can Be Discarded
        add(t3, 0,                      0,                      // P
            c,  cRowPtr,                cColPtr,                // C11
            c,  cRowPtr,                cColPtr,                // C11
            quadrantSize, false);
        add(t3, 0,                      0,                      // P
            c,  cRowPtr + quadrantSize, cColPtr + quadrantSize, // C22
            c,  cRowPtr + quadrantSize, cColPtr + quadrantSize, // C22
            quadrantSize, false);
        
        // Calculate Q
        // t1 = A21 + A22
        add(a,  aRowPtr + quadrantSize, aColPtr,                // A21
            a,  aRowPtr + quadrantSize, aColPtr + quadrantSize, // A22
            t1, 0,                      0,                      // (A21 + A22)
            quadrantSize, false);
        // Q = t3 = (A21 + A22)B11
        multiply(t1, 0,       0,       // (A21 + A22)
                 b,  bRowPtr, bColPtr, // B11
                 t3, 0,       0);      // Q
        // Add Q To C21 & Subtract From C22 Immediately
        add(t3, 0,                      0,                      // Q
            c,  cRowPtr + quadrantSize, cColPtr,                // C21
            c,  cRowPtr + quadrantSize, cColPtr,                // C21
            quadrantSize, false);
        add(t3, 0,                      0,                      // Q
            c,  cRowPtr + quadrantSize, cColPtr + quadrantSize, // C22
            c,  cRowPtr + quadrantSize, cColPtr + quadrantSize, // C22
            quadrantSize, true);
        
        // Calculate R
        // t2 = (B12 - B22)
        add(b,  bRowPtr,                bColPtr + quadrantSize, // B12
            b,  bRowPtr + quadrantSize, bColPtr + quadrantSize, // B22
            t2, 0,                      0,                      // (B12 - B22)
            quadrantSize, true);
        // R = t3 = A11(B12 - B22)
        multiply(a,  aRowPtr, aColPtr, // A11
                 t2, 0,       0,       // (B12 - B22)
                 t3, 0,       0);      // R
        // Add R To C12 & C22 Immediately
        add(t3, 0,                      0,                      // R
            c,  cRowPtr,                cColPtr + quadrantSize, // C12
            c,  cRowPtr,                cColPtr + quadrantSize, // C12
            quadrantSize, false);
        add(t3, 0,                      0,                      // R
            c,  cRowPtr + quadrantSize, cColPtr + quadrantSize, // C22
            c,  cRowPtr + quadrantSize, cColPtr + quadrantSize, // C22
            quadrantSize, false);
        
        // Calculate S
        // t2 = (B21 - B11)
        add(b,  bRowPtr + quadrantSize, bColPtr,                // B21
            b,  bRowPtr,                bColPtr,                // B11
            t2, 0,                      0,                      // (B21 - B11)
            quadrantSize, true);
        // S = t3 = A22(B21 - B11)
        multiply(a,  aRowPtr + quadrantSize, aColPtr + quadrantSize, // A22
                 t2, 0,                      0,                      // (B21 - B11)
                 t3, 0,                      0);                     // S
        // Add S To C11 & C21 Immediately
        add(t3, 0,       0,                                     // S
            c,  cRowPtr, cColPtr,                               // C11
            c,  cRowPtr, cColPtr,                               // C11
            quadrantSize, false);
        add(t3, 0,                      0,                      // S
            c,  cRowPtr + quadrantSize, cColPtr,                // C21
            c,  cRowPtr + quadrantSize, cColPtr,                // C21
            quadrantSize, false);
        
        // Calculate T
        // t1 = (A11 + A12)
        add(a,  aRowPtr,                aColPtr,                // A11
            a,  aRowPtr,                aColPtr + quadrantSize, // A12
            t1, 0,                      0,                      // (A11 + A12)
            quadrantSize, false);
        // T = t3 = (A11 + A12)B22
        multiply(t1, 0,                      0,                      // (A11 + A12)
                 b,  bRowPtr + quadrantSize, bColPtr + quadrantSize, // B22
                 t3, 0,                      0);                     // T
        // Subtract T From C11 & Add To C12 Immediately
        add(t3, 0,                      0,                      // T
            c,  cRowPtr,                cColPtr,                // C11
            c,  cRowPtr,                cColPtr,                // C11
            quadrantSize, true);
        add(t3, 0,                      0,                      // T
            c,  cRowPtr,                cColPtr + quadrantSize, // C12
            c,  cRowPtr,                cColPtr + quadrantSize, // C12
            quadrantSize, false);

        // Calculate U
        // t1 = (A21 - A11)
        add(a,  aRowPtr + quadrantSize, aColPtr,                // A21
            a,  aRowPtr,                aColPtr,                // A11
            t1, 0,                      0,                      // (A21 - A11)
            quadrantSize, true);
        // t2 = (B11 + B12)
        add(b,  bRowPtr,                bColPtr,                // B11
            b,  bRowPtr,                bColPtr + quadrantSize, // B12
            t2, 0,                      0,                      // (B11 + B12)
            quadrantSize, false);
        // U = t3 = (A21 - A11)(B11 + B12)
        multiply(t1, 0, 0,  // (A21 - A11)
                 t2, 0, 0,  // (B11 + B12)
                 t3, 0, 0); // (A21 - A11)(B11 + B12)
        // Add U To C22 Immediately
        add(t3, 0,                      0,                      // U
            c,  cRowPtr + quadrantSize, cColPtr + quadrantSize, // C22
            c,  cRowPtr + quadrantSize, cColPtr + quadrantSize, // C22
            quadrantSize, false);

        // Calculate V
        // t1 = (A12 - A22)
        add(a,  aRowPtr,                aColPtr + quadrantSize, // A12
            a,  aRowPtr + quadrantSize, aColPtr + quadrantSize, // A22
            t1, 0,                      0,                      // (A12 - A22)
            quadrantSize, true);
        // t2 = (B21 + B22)
        add(b,  bRowPtr + quadrantSize, bColPtr,                // B21
            b,  bRowPtr + quadrantSize, bColPtr + quadrantSize, // B22
            t2, 0,                      0,                      // (B21 + B22)
            quadrantSize, false);
        // V = t3 = (A12 - A22)(B21 + B22)
        multiply(t1, 0, 0,  // (A12 - A22)
                 t2, 0, 0,  // (B21 + B22)
                 t3, 0, 0); // (A12 - A22)(B21 + B22)
        // Add To C11 Immediately
        add(t3, 0,                      0,                      // V
            c,  cRowPtr,                cColPtr,                // C11
            c,  cRowPtr,                cColPtr,                // C11
            quadrantSize, false);
    }

    /**
     * Adds two matrices, storing the result in
     * a third matrix provided as a parameter.
     * 
     * @param a        1st Matrix
     * @param aRowPtr  1st Matrix Row To Start From
     * @param aColPtr  1st Matrix Column To Start From
     * @param b        2nd Matrix
     * @param bRowPtr  2nd Matrix Row To Start From
     * @param bColPtr  2nd Matrix Column To Start From
     * @param c        Matrix To Store Result
     * @param cRowPtr  Row To Begin Storing Result
     * @param cColPtr  Column To Begin Storing Result
     * @param size     The size of the arrays being added.
     * @param subtract Perform A-B If True
     */
    private void add(int[][] a, int aRowPtr, int aColPtr,
                     int[][] b, int bRowPtr, int bColPtr,
                     int[][] c, int cRowPtr, int cColPtr,
                     int size, boolean subtract)
    {
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                c[cRowPtr + row][cColPtr + col] = a[aRowPtr + row][aColPtr + col]
                    + (subtract ? -b[bRowPtr + row][bColPtr + col] : b[bRowPtr + row][bColPtr + col]);
            }
        }
    }
}