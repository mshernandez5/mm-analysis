package com.mshernandez.mm_analysis;

/**
 * An abstract class to represent a matrix multiplier,
 * leaving the specific algorithm and implementation to
 * child classes.
 */
public abstract class MatrixMultiplier
{
    private String algorithmName;

    /**
     * Initializes this matrix multiplier with
     * the name of the algorithm used.
     */
    public MatrixMultiplier(String algorithmName)
    {
        this.algorithmName = algorithmName;
    }

    /**
     * Multiplies two matrices A and B with the
     * assumption that A and B are both n*n matrices.
     * 
     * @param a The first matrix in the operation.
     * @param b The second matrix in the operation.
     * @return The resulting matrix.
     */
    public abstract int[][] multiplySquareMatrices(int[][] a, int[][] b);

    /**
     * Returns the name of the algorithm.
     */
    public String getAlgorithmName()
    {
        return algorithmName;
    }

    /**
     * Returns the name of the algorithm.
     */
    @Override
    public String toString()
    {
        return algorithmName;
    }
}