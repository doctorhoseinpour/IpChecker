package com.farazpardazan.ipchecker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Getter
@Setter
public class DataMiner {

    private BufferedReader csvReader;
    private String[][] Data;

    public DataMiner() throws IOException {
    }

    public String[][] ArrayFiller() throws IOException {
        String row;
        csvReader = new BufferedReader(new FileReader("src\\main\\resources\\Iranian-ip.csv"));
        List<String[]> list = new ArrayList<>();
        while ((row = csvReader.readLine()) != null) {
            String[] Line = row.split(",");
            list.add(new String[]{
                    Line[0],
                    Line[1]
            });
        }
        csvReader.close();
        this.Data = new String[list.size()][2];
        for (int i = 0; i < list.size(); ++i) {
            this.Data[i][0] = list.get(i)[0];
            this.Data[i][1] = list.get(i)[1];
        }
        return Data;
    }
}