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

import java.io.*;
@Component
@Getter
@Setter
public class BinaryIpChecker implements IpChecker {

    private DataMiner dataMiner;
    private String[][] Data;

    @Autowired
    public BinaryIpChecker(DataMiner dataMiner) throws IOException {
        this.dataMiner = dataMiner;
    }

    public long ipToLong(String ipAddress) {

        String[] octets = ipAddress.split("\\.");
        long ip = 0;
        for (int i = 3; i >= 0; i--) {
            long octet = Long.parseLong(octets[3 - i]);
            ip |= octet << (i * 8);
        }
        return ip;
    }


    @Override
    public boolean hasAccess(String clientIP) throws IOException, AddressStringException{
        this.Data = this.dataMiner.ArrayFiller();
        int low = 0;
        int high = this.Data.length;
        int mid = (high + low)/2;
        while (true)
        {
            if(high < low)
            {
                break;
            }
            mid = low + (( high - low ) / 2);
            if(ipToLong(Data[mid][0]) > ipToLong(clientIP))
            {
                high = mid - 1;
            }
            if(ipToLong(Data[mid][0]) < ipToLong(clientIP))
            {
                low = mid + 1;
            }
            if(ipToLong(Data[mid][0]) == ipToLong(clientIP))
            {
                break;
            }
        }
        if(ipToLong(clientIP) >= ipToLong(Data[mid][0]) && ipToLong(clientIP) <= ipToLong(Data[mid][1]))
        {
            return true;
        }
        return false;
    }
}