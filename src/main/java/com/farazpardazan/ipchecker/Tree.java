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
    public int[][] First = new int[256][256];
    public int[][] Second = new int[256][256];
    public int[][] Third = new int[256][256];
    @Autowired
    public Tree(TreeMaker treeMaker) throws IOException {
        this.treeMaker = treeMaker;
    }

    @Override
    public boolean hasAccess(String clientIP) throws IOException, AddressStringException {
        this.First = this.treeMaker.First;
        this.Second = this.treeMaker.Second;
        this.Third = this.treeMaker.Third;
        String[] IpSplit = clientIP.split("\\.");
        if(this.First[Integer.parseInt(IpSplit[0])][Integer.parseInt(IpSplit[1])] == Integer.parseInt(IpSplit[1]))
        {
            if(this.Second[Integer.parseInt(IpSplit[1])][Integer.parseInt(IpSplit[2])] == Integer.parseInt(IpSplit[2]))
            {
                if(this.Third[Integer.parseInt(IpSplit[2])][Integer.parseInt(IpSplit[3])] == Integer.parseInt(IpSplit[3]))
                {
                    return true;
                }
            }
        }
        return false;
    }
}