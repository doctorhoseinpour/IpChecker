package com.farazpardazan.ipchecker;
import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IPAddressString;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import java.io.*;

@Getter
@Setter
public class BinaryIpChecker implements IpChecker {
    public BinaryIpChecker() throws FileNotFoundException {
    }
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

    static int GetFileSize() throws IOException {
        String row;
        int count = 0;
        while((row = csvReader.readLine()) != null) {
            count++;
        }
        csvReader.close();
        return count;
    }

    static int FileSize;

    static {
        try {
            FileSize = GetFileSize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String[][] data = new String[FileSize][2];

    public void PreProcess() throws IOException {
        String row;
        int i = 0;
        while((row = csvReader.readLine()) != null) {
            String[] Line = row.split(",");
            data[i][0] = Line[0];
            data[i][1] = Line[1];
            i++;
        }
        csvReader.close();
    }


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
    public boolean hasAccess(String clientIP) throws IOException, AddressStringException{
        int low = 0;
        int high = GetFileSize();
        int mid = (high + low)/2;
        while (true)
        {
            if(high < low)
            {
                break;
            }
            mid = low + (( high - low ) / 2);
            if(ipToLong(data[mid][0]) > ipToLong(clientIP))
            {
                high = mid - 1;
            }
            if(ipToLong(data[mid][0]) < ipToLong(clientIP))
            {
                low = mid + 1;
            }
            if(ipToLong(data[mid][0]) == ipToLong(clientIP))
            {
                break;
            }
        }
        IPAddress lower = new IPAddressString(data[mid][0]).toAddress();
        IPAddress upper = new IPAddressString(data[mid][1]).toAddress();
        IPAddress addr = new IPAddressString(clientIP).toAddress();
        IPAddressSeqRange range = lower.toSequentialRange(upper);
        if(range.contains(addr))
        {
            return true;
        }
        return false;
    }
}