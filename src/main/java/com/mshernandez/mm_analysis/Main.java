package com.mshernandez.mm_analysis;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Main
{
    public static final int MATRIX_SIZE_LIMIT = 128;
    public static final int NUMBER_INPUTS_PER_SIZE = 1000;
    public static final int NUMBER_SAMPLES_PER_INPUT = 20;

    /**
     * Measures the runtime to multiply randomly generated
     * matrices up to size MATRIX_SIZE_LIMIT using different algorithms,
     * taking NUMBER_SAMPLES_PER_SIZE samples for each matrix size.
     * 
     * @param args Program arguments, discarded.
     */
    public static void main(String[] args)
    {
        List<MatrixMultiplierBenchmarker> benchmarks = new ArrayList<>();
        benchmarks.add(new MatrixMultiplierBenchmarker(new IterativeMatrixMultiplier()));
        benchmarks.add(new MatrixMultiplierBenchmarker(new DivideConquerMatrixMultiplier()));
        benchmarks.add(new MatrixMultiplierBenchmarker(new StrassensMatrixMultiplier()));
        benchmarks.add(new MatrixMultiplierBenchmarker(new ControlMatrixMultiplier()));
        for (int size = 2; size <= MATRIX_SIZE_LIMIT; size = size << 1)
        {
            int[][] matrixA = new int[size][size];
            int[][] matrixB = new int[size][size];
            System.out.println();
            for (int input = 0; input < NUMBER_INPUTS_PER_SIZE; input++)
            {
                MatrixMultiplierBenchmarker.randomizeMatrix(matrixA);
                MatrixMultiplierBenchmarker.randomizeMatrix(matrixB);
                for (MatrixMultiplierBenchmarker benchmarker : benchmarks)
                {
                    showProgressBar("Size " + size, 50, input, NUMBER_INPUTS_PER_SIZE);
                    benchmarker.benchmarkInput(matrixA, matrixB, NUMBER_SAMPLES_PER_INPUT);
                    System.gc();
                }
            }
        }
        for (MatrixMultiplierBenchmarker benchmarker : benchmarks)
        {
            Map<Integer, Double> runtimes = benchmarker.getAverageRuntimes();
            for (int size : runtimes.keySet())
            {
                System.out.printf("\nSize %5d: %f\n", size, runtimes.get(size));
            }
        }
    }

    /**
     * Displays or updates a progress bar
     * on the current line.
     * 
     * @param label A label to show before the progress bar.
     * @param progressBarSize The length of the loading section.
     * @param currentCycle The number of cycles completed.
     * @param numberCycles The total number of cycles required.
     */
    public static void showProgressBar(String label, int progressBarSize,
                                       int currentCycle, int numberCycles)
    {
        int progress = (int) ((double) currentCycle / numberCycles * progressBarSize);
        System.out.printf("\r%-10s[", label);
        for (int i = 0; i < progressBarSize; i++)
        {
            System.out.print((i < progress) ? "▓" : " ");
        }
        System.out.print("]");
    }
}