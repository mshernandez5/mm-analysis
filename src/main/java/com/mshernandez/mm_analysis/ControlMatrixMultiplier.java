package com.mshernandez.mm_analysis;

/**
 * Does not perform actual matrix multiplication,
 * simply allocates array memory to measure the
 * overhead of this operation so that the actual
 * rate of growth of the multiplication itself
 * can be interpreted more accurately.
 */
public class ControlMatrixMultiplier extends MatrixMultiplier
{
    /**
     * Initializes this matrix multiplier with
     * the name of the algorithm used.
     */
    public ControlMatrixMultiplier()
    {
        super("Control");
    }

    /**
     * Allocates the space needed to store a
     * result if one were actually calculated.
     * Designed only as a control to measure the
     * overhead of this operation.
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
        return result;
    }
}