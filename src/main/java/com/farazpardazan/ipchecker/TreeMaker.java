package com.farazpardazan.ipchecker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Getter
@Setter
public class TreeMaker {

    private String[][] Data;

    private Node[] Roots;

    private DataMiner dataMiner;
    @Autowired
    public TreeMaker(DataMiner dataMiner) throws IOException {
        this.dataMiner = dataMiner;
    }

    public Node[] Insert() throws IOException {
        this.Data = this.dataMiner.ArrayFiller();
        this.Roots = new Node[256];
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
                Roots[j].Value = j;
                for(int k = lower1 ; k<= upper1 ;k++)
                {
                    Roots[j].Children[k].Value = k;
                    for(int l = lower2 ; l<= upper2 ;l++)
                    {
                        Roots[j].Children[k].Children[l].Value = l;
                        for(int m = lower3 ; m<= upper3 ;m++)
                        {
                            Roots[j].Children[k].Children[l].Children[m].Value = m;
                        }
                    }
                }
            }
        }
        return Roots;
    }
}
