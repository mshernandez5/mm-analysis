package com.mshernandez.mm_analysis;

import java.util.Map;
import java.util.TreeMap;

/**
 * Given a matrix multiplier, this class measures
 * the runtimes of given inputs and keeps track of
 * the average runtimes corresponding to each tested
 * input size. Testing multiple inputs of the same size
 * will automatically update the overall average times
 * of that input size.
 */
public class MatrixMultiplierBenchmarker
{
    private static final int RANDOM_NUMBER_RANGE = 1000;

    private MatrixMultiplier algorithm;
    private Map<Integer, RunningAverage> sizeRuntimeAverages;

    /**
     * Initializes a new benchmark object.
     * 
     * @param algorithm The matrix multiplier to use.
     */
    public MatrixMultiplierBenchmarker(MatrixMultiplier algorithm)
    {
        this.algorithm = algorithm;
        sizeRuntimeAverages = new TreeMap<>();
    }

    /**
     * Gets the average time to multiply the
     * provided matrices over a given number of samples.
     * 
     * @param multiplier The matrix multiplier to use.
     * @param a The first matrix input.
     * @param b The second matrix input.
     * @param numSamples The number of measurements to take.
     * @return The average time to multiply the given inputs, in nanoseconds.
     */
    public void benchmarkInput(int[][] a, int[][] b, int numSamples)
    {
        long[] sampleTimes = new long[numSamples];
        for (int sample = 0; sample < numSamples; sample++)
        {
            sampleTimes[sample] = measureRuntime(a, b);
        }
        double averageTimeForInput = 0.0;
        for (long time : sampleTimes)
        {
            averageTimeForInput += time;
        }
        averageTimeForInput /= (double) numSamples;
        int inputSize = a.length;
        if (!sizeRuntimeAverages.containsKey(inputSize))
        {
            sizeRuntimeAverages.put(inputSize, new RunningAverage());
        }
        sizeRuntimeAverages.get(inputSize).addData(averageTimeForInput);
    }

    /**
     * Measures the time it takes to multiply two matrices.
     *
     * @param a The first matrix in the operation.
     * @param b The second matrix in the operation.
     * @return The time the multiplication took in nanoseconds.
     */
    public long measureRuntime(int[][] a, int[][] b)
    {
        long startingTime = System.nanoTime();
        algorithm.multiplySquareMatrices(a, b);
        return System.nanoTime() - startingTime;
    }

    /**
     * Gets the average runtimes for every input
     * size tested.
     * 
     * @return A mapping from input size to average runtime.
     */
    public Map<Integer, Double> getAverageRuntimes()
    {
        Map<Integer, Double> averageRuntimes = new TreeMap<>();
        for (int key : sizeRuntimeAverages.keySet())
        {
            averageRuntimes.put(key, sizeRuntimeAverages.get(key).getAverage());
        }
        return averageRuntimes;
    }

    /**
     * Gets the average runtimes for a specific
     * size tested.
     * 
     * @param size The input size to get a runtime average for.
     * @return The average runtime for the input size.
     */
    public double getAverageRuntime(int size)
    {
        if (sizeRuntimeAverages.containsKey(size))
        {
            return sizeRuntimeAverages.get(size).getAverage();
        }
        return 0.0;
    }

    /**
     * Fills a matrix with random values.
     * 
     * @param matrix The matrix to randomize.
     */
    public static void randomizeMatrix(int[][] matrix)
    {
        for (int row = 0; row < matrix.length; row++)
        {
            for (int col = 0; col < matrix[row].length; col++)
            {
                matrix[row][col] = (int) Math.random() * RANDOM_NUMBER_RANGE + 1;
            }
        }
    }

    /**
     * Gets the name of the algorithm being
     * used by the matrix multiplier in use.
     * 
     * @return Name of the algoritm being used.
     */
    public String getAlgorithmName()
    {
        return algorithm.getAlgorithmName();
    }
}
