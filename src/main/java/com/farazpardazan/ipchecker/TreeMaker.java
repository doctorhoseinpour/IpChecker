package com.farazpardazan.ipchecker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@Getter
@Setter
public class TreeMaker {

    private String[][] Data;

    public int[][] First = new int[256][256];
    public int[][] Second = new int[256][256];
    public int[][] Third = new int[256][256];
    private DataMiner dataMiner;
    @Autowired
    public TreeMaker(DataMiner dataMiner) throws IOException {
        this.dataMiner = dataMiner;

    }
    @PostConstruct
    public void Insert() throws IOException {
        this.Data = this.dataMiner.ArrayFiller();

        for(int[] row : First)
        {
            Arrays.fill(row , -1);
        }

        for(int[] row : Second)
        {
            Arrays.fill(row , -1);
        }
        for(int[] row : Third)
        {
            Arrays.fill(row , -1);
        }

        for(int i = 0 ; i < this.Data.length ; i++)
        {
            String[] Lower = Data[i][0].split("\\.");
            String[] Upper = Data[i][1].split("\\.");
            int lower0 = Integer.parseInt(Lower[0]);
            int lower1 = Integer.parseInt(Lower[1]);
            int lower2 = Integer.parseInt(Lower[2]);
            int lower3 = Integer.parseInt(Lower[3]);
            int upper0 = Integer.parseInt(Upper[0]);
            int upper1 = Integer.parseInt(Upper[1]);
            int upper2 = Integer.parseInt(Upper[2]);
            int upper3 = Integer.parseInt(Upper[3]);
            for(int j = lower0 ; j<= upper0 ;j++)
            {
                for(int k = lower1 ; k<= upper1 ;k++)
                {
                    First[j][k] = k;
                    for(int l = lower2 ; l<= upper2 ;l++)
                    {
                        Second[k][l] = l;
                        for(int m = lower3 ; m<= upper3 ;m++)
                        {
                            Third[l][m] = m;
                        }
                    }
                }
            }
        }
    }
}
