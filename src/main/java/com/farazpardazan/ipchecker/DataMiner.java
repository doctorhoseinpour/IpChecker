package com.farazpardazan.ipchecker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@Getter
@Setter
public class DataMiner{

    private BufferedReader csvReader;

    public DataMiner() throws IOException {
        csvReader = new BufferedReader(new FileReader("src\\main\\resources\\Iranian-ip.csv"));
    }

    int GetFileSize() throws IOException {
        String row;
        int count = 0;
        while((row = csvReader.readLine()) != null) {
            count++;
        }
        csvReader.close();
        return count;
    }

    private String[][] Data = new String[2][this.GetFileSize()];

    public String[][] ArrayFiller() throws IOException {
        String row;
        int i = 0;
        while((row = csvReader.readLine()) != null) {
            String[] Line = row.split(",");
            Data[i][0] = Line[0];
            Data[i][1] = Line[1];
            i++;
        }
        csvReader.close();
        return Data;
    }
}