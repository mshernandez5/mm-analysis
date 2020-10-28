package com.mshernandez.mm_analysis;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RunningAverageTest
{
    @Test
    public void basicAverageTest()
    {
        RunningAverage avg = new RunningAverage();
        avg.addData(5);
        avg.addData(20);
        avg.addData(5);
        avg.addData(10);
        assertEquals(10.0, avg.getAverage(), 0);
    }
}