package com.farazpardazan.ipchecker;

import inet.ipaddr.AddressStringException;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Node implements IpChecker{
    @Value("${FILENAME}")
    static String FILENAME;
    static BufferedReader csvReader;

    static {
        try {
            csvReader = new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int value;
    public Node[] Children = new Node[256];
    static Node[] Roots = new Node[256];
    static void PreProcess() throws IOException {
        String row;
        int i = 0;
        while((row = csvReader.readLine()) != null) {
            String[] Line = row.split(",");
            String[] Lower = Line[0].split(".");
            String[] Upper = Line[1].split(".");
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
                Roots[j].value = j;
                for(int k = lower1 ; k<= upper1 ;k++)
                {
                    Roots[j].Children[k].value = k;
                    for(int l = lower2 ; l<= upper2 ;l++)
                    {
                        Roots[j].Children[k].Children[l].value = l;
                        for(int m = lower3 ; m<= upper3 ;m++)
                        {
                            Roots[j].Children[k].Children[l].Children[m].value = m;
                        }
                    }
                }
            }
            i++;
        }
        csvReader.close();
    }
    @Override
    public boolean hasAccess(String clientIP) throws IOException, AddressStringException {
        String[] IPSplit = clientIP.split(".");
        int clientIP0 = Integer.parseInt(IPSplit[0]);
        int clientIP1 = Integer.parseInt(IPSplit[1]);
        int clientIP2 = Integer.parseInt(IPSplit[2]);
        int clientIP3 = Integer.parseInt(IPSplit[3]);
        boolean flag1=false , flag2=false , flag3= false , flag4 = false;
        if(Roots[clientIP0].value == clientIP0)
        {
            flag1 = true;
        }
        if(Roots[clientIP0].Children[clientIP1].value == clientIP1)
        {
            flag2 = true;
        }
        if(Roots[clientIP0].Children[clientIP1].Children[clientIP2].value == clientIP2)
        {
            flag3 = true;
        }
        if(Roots[clientIP0].Children[clientIP1].Children[clientIP2].Children[clientIP3].value == clientIP3)
        {
            flag4 = true;
        }
        if(flag1 && flag2 && flag3 && flag4)
        {
            return true;
        }
        return false;
    }
}