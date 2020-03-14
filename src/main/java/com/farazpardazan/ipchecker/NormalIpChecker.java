package com.farazpardazan.ipchecker;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IPAddressString;
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
public class NormalIpChecker implements IpChecker {


    private DataMiner dataMiner;

    public NormalIpChecker(@Autowired DataMiner dataMiner) throws IOException {
        this.dataMiner = dataMiner;
    }

    private String[][] Data = dataMiner.ArrayFiller();

    public long ipToLong(String ipAddress) {

        String[] ipAddressInArray = ipAddress.split(".");
        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {

            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);

        }
        return result;
    }


    @Override
    public boolean hasAccess(String clientIP) throws IOException, AddressStringException {
        for(String[] i : Data)
        {
            IPAddress lower = new IPAddressString(i[0]).toAddress();
            IPAddress upper = new IPAddressString(i[1]).toAddress();
            IPAddress addr = new IPAddressString(clientIP).toAddress();
            IPAddressSeqRange range = lower.toSequentialRange(upper);
            if(range.contains(addr))
            {
                return true;
            }
        }
        return false;
    }
}