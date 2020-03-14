package com.farazpardazan.ipchecker;

import java.util.Comparator;

public class Sort implements Comparator<Range> {
    public int compare(Range range1, Range range2)
    {
        if(range1.lower < range2.lower)
        {
            return 1;
        }
        else if(range2.lower < range1.lower)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}
