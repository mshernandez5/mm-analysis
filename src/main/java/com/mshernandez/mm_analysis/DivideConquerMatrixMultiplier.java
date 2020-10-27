package com.mshernandez.mm_analysis;

/**
 * A matrix multiplier using an iterative algorithm.
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
    public double[][] multiplySquareMatrices(double[][] a, double[][] b)
    {
        int size = a.length;
        double[][] result = new double[size][size];
        multiply(a, 0, b, 0, result, 0, result.length);
        return result;
    }

    private void multiply(double[][] a, int aPtr,
                                double[][] b, int bPtr,
                                double[][] result, int resultPtr,
                                int size)
    {
        if (size <= 2)
        {

            return;
        }
        for (int i = resultPtr; i < size; i++)
        {
            multiply(a, aPtr, b, bPtr, result, resultPtr, size);
        }
        return;
    }

    //multiplyTwoByTwo(int[][] a)
}