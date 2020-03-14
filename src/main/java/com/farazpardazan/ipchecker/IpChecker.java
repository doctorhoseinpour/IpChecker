package com.farazpardazan.ipchecker;

import inet.ipaddr.AddressStringException;

import java.io.IOException;

public interface IpChecker {

    boolean hasAccess(String clientIP) throws IOException, AddressStringException;
}
