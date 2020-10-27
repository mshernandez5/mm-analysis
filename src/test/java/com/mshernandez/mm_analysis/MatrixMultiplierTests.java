package com.mshernandez.mm_analysis;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests matrix multiplication algorithms
 * with 2x2, 4x4, and 8x8 matrices.
 */
public class MatrixMultiplierTests 
{

    private static final int[][] a =
    {
        {1, 2},
        {3, 4}
    };

    private static final int[][] b =
    {
        {5, 6},
        {7, 8}
    };

    private static final int[][] ab =
    {
        {19, 22},
        {43, 50}
    };

    private static final int[][] c =
    {
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12},
        {13, 14, 15, 16}
    };

    private static final int[][] d =
    {
        {17, 18, 19, 20},
        {21, 22, 23, 24},
        {25, 26, 27, 28},
        {29, 30, 31, 32}
    };

    private static final int[][] cd =
    {
        {250, 260, 270, 280},
        {618, 644, 670, 696},
        {986, 1028, 1070, 1112},
        {1354, 1412, 1470, 1528}
    };

    private static final int[][] e =
    {
        {5, 7, 4, 3, 3, 6, 9, 2},
        {4, 7, 5, 0, 0, 0, 6, 2},
        {4, 3, 5, 8, 8, 8, 2, 4},
        {8, 8, 2, 7, 0, 7, 1, 5},
        {5, 2, 8, 4, 4, 3, 7, 2},
        {3, 4, 9, 3, 7, 9, 9, 6},
        {0, 5, 3, 2, 9, 4, 9, 1},
        {2, 7, 5, 8, 7, 6, 8, 5}
    };

    private static final int[][] f =
    {
        {2, 3, 1, 1, 5, 9, 1, 4},
        {7, 7, 3, 7, 4, 1, 3, 5},
        {4, 5, 8, 3, 7, 0, 7, 6},
        {1, 3, 9, 9, 5, 0, 6, 3},
        {7, 3, 3, 0, 7, 3, 0, 2},
        {3, 5, 4, 8, 2, 7, 8, 9},
        {8, 3, 1, 3, 3, 0, 9, 1},
        {6, 4, 3, 8, 5, 5, 1, 6}
    };

    private static final int[][] ef =
    {
        {201, 167, 133, 184, 166, 113, 203, 169},
        {137, 112, 77, 102, 111, 53, 116, 99},
        {177, 168, 195, 214, 205, 139, 182, 199},
        {146, 169, 155, 232, 163, 154, 158, 199},
        {165, 137, 148, 140, 174, 90, 180, 144},
        {257, 208, 198, 232, 233, 145, 255, 235},
        {202, 134, 112, 129, 154, 65, 162, 118},
        {242, 199, 203, 250, 223, 113, 231, 203}
    };

    @Test
    public void iterativeMultiplier2x2()
    {
        MatrixMultiplier m = new IterativeMatrixMultiplier();
        assertTrue(multiply2x2(m));
    }

    @Test
    public void iterativeMultiplier4x4()
    {
        MatrixMultiplier m = new IterativeMatrixMultiplier();
        assertTrue(multiply4x4(m));
    }

    @Test
    public void iterativeMultiplier8x8()
    {
        MatrixMultiplier m = new IterativeMatrixMultiplier();
        assertTrue(multiply8x8(m));
    }

    @Test
    public void divideMultiplier2x2()
    {
        MatrixMultiplier m = new DivideConquerMatrixMultiplier();
        assertTrue(multiply2x2(m));
    }

    @Test
    public void divideMultiplier4x4()
    {
        MatrixMultiplier m = new DivideConquerMatrixMultiplier();
        assertTrue(multiply4x4(m));
    }

    @Test
    public void divideMultiplier8x8()
    {
        MatrixMultiplier m = new DivideConquerMatrixMultiplier();
        assertTrue(multiply8x8(m));
    }

    @Test
    public void strassensMultiplier2x2()
    {
        MatrixMultiplier m = new StrassensMatrixMultiplier();
        assertTrue(multiply2x2(m));
    }

    @Test
    public void strassensMultiplier4x4()
    {
        MatrixMultiplier m = new StrassensMatrixMultiplier();
        assertTrue(multiply4x4(m));
    }

    @Test
    public void strassensMultiplier8x8()
    {
        MatrixMultiplier m = new StrassensMatrixMultiplier();
        assertTrue(multiply8x8(m));
    }

    public static boolean multiply2x2(MatrixMultiplier m)
    {
        int[][] abResult = m.multiplySquareMatrices(a, b);
        return matricesEqual(abResult, ab);
    }

    public static boolean multiply4x4(MatrixMultiplier m)
    {
        int[][] cdResult = m.multiplySquareMatrices(c, d);
        return matricesEqual(cdResult, cd);
    }

    public static boolean multiply8x8(MatrixMultiplier m)
    {
        int[][] efResult = m.multiplySquareMatrices(e, f);
        return matricesEqual(efResult, ef);
    }

    public static boolean matricesEqual(int[][] a, int[][] b)
    {
        if (a.length != b.length)
        {
            System.out.println("Number Rows Mismatch");
            return false;
        }
        for (int r = 0; r < a.length; r++)
        {
            if (a[r].length != b[r].length)
            {
                System.out.println("Number Columns Mismatch");
                return false;
            }
            for (int c = 0; c < a[r].length; c++)
            {
                if (a[r][c] != b[r][c])
                {
                    printArray(a);
                    System.out.println("Does Not Match");
                    printArray(b);
                    return false;
                }
            }
        }
        return true;
    }

    public static void printArray(int[][] a)
    {
        System.out.println();
        for (int r = 0; r < a.length; r++)
        {
            for (int c = 0; c < a[r].length; c++)
            {
                System.out.print(a[r][c]);
                if (c == a[r].length - 1)
                {
                    System.out.println();
                }
                else
                {
                    System.out.print(", ");
                }
            }
        }
        System.out.println();
    }
}
