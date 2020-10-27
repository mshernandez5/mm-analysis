package com.mshernandez.mm_analysis;

/**
 * A matrix multiplier using an iterative algorithm.
 */
public class IterativeMatrixMultiplier extends MatrixMultiplier
{
    /**
     * Initializes this matrix multiplier with
     * the name of the algorithm used.
     */
    public IterativeMatrixMultiplier()
    {
        super("Iterative");
    }

    /**
     * Multiplies two matrices A and B with the
     * assumption that A and B are both n*n matrices
     * using a 3-loop iterative method.
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
        for (int resultRow = 0; resultRow < size; resultRow++)
        {
            for (int resultCol = 0; resultCol < size; resultCol++)
            {
                double sum = 0.0;
                for (int i = 0; i < size; i++)
                {
                    sum += a[resultRow][i] * b[i][resultCol];
                }
                result[resultRow][resultCol] = sum;
            }
        }
        return result;
    }
}