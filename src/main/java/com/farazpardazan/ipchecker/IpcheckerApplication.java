package com.farazpardazan.ipchecker;

import inet.ipaddr.AddressStringException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class IpcheckerApplication {

    public static void main(String[] args) throws IOException, AddressStringException {
        ApplicationContext context= SpringApplication.run(IpcheckerApplication.class, args);
        NormalIpChecker ipChecker = context.getBean(NormalIpChecker.class);

        Scanner scanner = new Scanner(System.in);
        String ClientIp = scanner.nextLine();
        System.out.println(ipChecker.hasAccess(ClientIp));
    }

}
