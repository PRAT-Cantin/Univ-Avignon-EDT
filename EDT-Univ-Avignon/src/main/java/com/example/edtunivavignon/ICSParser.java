package com.example.edtunivavignon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ICSParser {
    public static ArrayList<String> ReadICS(String link) throws IOException {
        URL calendar = new URL(link);
        BufferedReader reader = new BufferedReader(new InputStreamReader(calendar.openStream()));
        reader.lines().forEach(line -> {

        });
        return new ArrayList<String>();
    }

}
