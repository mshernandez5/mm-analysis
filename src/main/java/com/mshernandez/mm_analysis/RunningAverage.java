package com.mshernandez.mm_analysis;

/**
 * Maintains a running average of data points.
 * As more data is added, the average is updated.
 */
public class RunningAverage
{
    private double average;
    private int qtyOfItems;

    public RunningAverage()
    {
        average = 0.0;
        qtyOfItems = 0;
    }

    public void addData(double data)
    {
        average = (average * qtyOfItems + data) / ++qtyOfItems;
    }

    public double getAverage()
    {
        return average;
    }

    public int getNumberItems()
    {
        return qtyOfItems;
    }
}