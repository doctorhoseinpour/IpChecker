package com.farazpardazan.ipchecker;

import inet.ipaddr.AddressStringException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.TreeSet;
@Getter
@Setter
public class TreeSetIpChecker implements IpChecker {

    static TreeSet<Range> Tree = new TreeSet<Range>(new Sort());

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


    static long ipToLong(String ipAddress) {

        String[] ipAddressInArray = ipAddress.split(".");
        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {

            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);

        }
        return result;
    }


    static void PreProcess() throws IOException {
        String row;
        int i = 0;
        while((row = csvReader.readLine()) != null) {
            String[] Line = row.split(",");
            Range range = new Range(ipToLong(Line[0]) , ipToLong(Line[1]));
            Tree.add(range);
            i++;
        }
        csvReader.close();
    }

    @Override
    public boolean hasAccess(String clientIP) throws IOException, AddressStringException {
        Range range = new Range(ipToLong(clientIP) , null);
        if(Objects.requireNonNull(Tree.floor(range)).upper >= range.lower )
        {
            return true;
        }
        return false;
    }
}