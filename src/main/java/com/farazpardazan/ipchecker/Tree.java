package com.farazpardazan.ipchecker;

import inet.ipaddr.AddressStringException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Component
@Getter
@Setter
public class Tree implements IpChecker{

    private TreeMaker treeMaker;
    private String[][] Data;
    private Node[] Roots;

    @Autowired
    public Tree(TreeMaker treeMaker) throws IOException {
        this.treeMaker = treeMaker;
    }

    @Override
    public boolean hasAccess(String clientIP) throws IOException, AddressStringException {

        this.Roots = this.treeMaker.Insert();
        String[] IPSplit = clientIP.split(".");
        int clientIP0 = Integer.parseInt(IPSplit[0]);
        int clientIP1 = Integer.parseInt(IPSplit[1]);
        int clientIP2 = Integer.parseInt(IPSplit[2]);
        int clientIP3 = Integer.parseInt(IPSplit[3]);
        boolean flag1=false , flag2=false , flag3= false , flag4 = false;
        if(Roots[clientIP0].Value == clientIP0)
        {
            flag1 = true;
        }
        if(Roots[clientIP0].Children[clientIP1].Value == clientIP1)
        {
            flag2 = true;
        }
        if(Roots[clientIP0].Children[clientIP1].Children[clientIP2].Value == clientIP2)
        {
            flag3 = true;
        }
        if(Roots[clientIP0].Children[clientIP1].Children[clientIP2].Children[clientIP3].Value == clientIP3)
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