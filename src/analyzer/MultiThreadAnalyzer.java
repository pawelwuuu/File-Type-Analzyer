package analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class MultiThreadAnalyzer{
    ArrayList<FileTypeAnalyzer> analyzers = new ArrayList<>();

    MultiThreadAnalyzer(String dirName, String pattern, String documentType){
        File[] files = new File(dirName).listFiles();

        for (File file: files) {
            analyzers.add(new FileTypeAnalyzer(file.getPath(), pattern, documentType));
        }
    }

    MultiThreadAnalyzer(String dirName, String patternBase){
        String path = System.getProperty("user.dir") + File.separator + dirName;
        File[] files = new File(path).listFiles();

        for (File file: files) {
            analyzers.add(new FileTypeAnalyzer(file.getPath(), patternBase));
        }
    }

    void initMultiThreading(){
        ExecutorService executor = Executors.newFixedThreadPool(25);
        for (FileTypeAnalyzer analyzer : analyzers) {
            executor.submit(() -> {
                try {
                    System.out.println(analyzer.analyzeFileTypePrior());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        try{
            executor.awaitTermination(10, TimeUnit.SECONDS);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}