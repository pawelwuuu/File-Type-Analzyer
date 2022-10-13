package analyzer;

import java.io.*;


public class Main{
    public static void main(String[] args) throws IOException {
        MultiThreadAnalyzer multiThreadAnalyzer = new MultiThreadAnalyzer(args[0], args[1]);
        multiThreadAnalyzer.initMultiThreading();
    }
}

