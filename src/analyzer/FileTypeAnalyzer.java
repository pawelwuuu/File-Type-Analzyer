package analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class FileTypeAnalyzer {
        protected String filename, pattern, documentType, patternBase;

        FileTypeAnalyzer(String filename, String pattern, String documentType) {
            this.filename = filename;
            this.pattern = pattern;
            this.documentType = documentType;
        }

        FileTypeAnalyzer(String filename, String patternsBase){
            this.patternBase = patternsBase;
            this.filename = filename;
        }

        List<List<String>> laodPatterns() throws FileNotFoundException {
            List<List<String>> patterns = new ArrayList<>();
            try(Scanner input = new Scanner(new File(patternBase));){
                while(input.hasNextLine()) {
                    String[] actualLine = input.nextLine().split(";");
                    patterns.add(Arrays.asList(actualLine));
                }
            }catch (FileNotFoundException e){
                System.out.println("Cannot find file by a provided filename");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            Collections.reverse(patterns);
            return patterns;
        }
        String loadDataIntoString(String filename){
            String fileContent = "";
            try(FileInputStream inputStream = new FileInputStream(filename)){
                int fileLenght = (int) new File(filename).length();
                byte[] allBytes = new byte[fileLenght];
                inputStream.read(allBytes);
                for (byte i: allBytes){
                    fileContent += (char)i;
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return fileContent;
        }

        int[] prefixFunction() {
            int[] lps = new int[pattern.length()];
            int len = 0;
            int i = 1;
            lps[0] = 0;
            int M = pattern.length();

            while (i < M) {
                if (pattern.charAt(i) == pattern.charAt(len)) {
                    len++;
                    lps[i] = len;
                    i++;
                }
                else
                {
                    if (len != 0) {
                        len = lps[len - 1];
                    }
                    else
                    {
                        lps[i] = len;
                        i++;
                    }
                }
            }
            return lps;
        }

        String analyzeFileTypeNaive() {
            String text = loadDataIntoString(this.filename);
            if (text.contains(pattern)){
                return filename + ": " + documentType;
            }
            return filename + ": Unknown file type";
        }

        String analyzeFileTypeKMP() {
            int M = pattern.length();
            String text = loadDataIntoString(filename);
            File file = new File(filename);
            int N = text.length();


            int lps[] = new int[M];
            int j = 0;

            lps = prefixFunction();

            int i = 0; // index for txt[]
            while (i < N) {
                if (pattern.charAt(j) == text.charAt(i)) {
                    j++;
                    i++;
                }
                if (j == M) {
                    return file.getName() + ": " + documentType;
                }

                else if (i < N && pattern.charAt(j) != text.charAt(i)) {
                    if (j != 0)
                        j = lps[j - 1];
                    else
                        i = i + 1;
                }
            }
            return file.getName() + ": Unknown file type";
        }

        //Copied from geeksforgeeks
        String analyzeFileRabinKarp() {
            int M = pattern.length();
            File file = new File(filename);
            String text = loadDataIntoString(filename);
            int N = text.length();
            int i, j;
            int p = 0; // hash value for pattern
            int t = 0; // hash value for txt
            int h = 1;
            int d = 256;
            int q = 3;

            // The value of h would be "pow(d, M-1)%q"
            for (i = 0; i < M-1; i++)
                h = (h*d)%q;

            // Calculate the hash value of pattern and first
            // window of text
            for (i = 0; i < M; i++)
            {
                p = (d*p + pattern.charAt(i))%q;
                t = (d*t + text.charAt(i))%q;
            }
            // Slide the pattern over text one by one
            for (i = 0; i <= N - M; i++)
            {

                // Check the hash values of current window of text
                // and pattern. If the hash values match then only
                // check for characters one by one
                if ( p == t )
                {
                    /* Check for characters one by one */
                    for (j = 0; j < M; j++)
                    {
                        if (text.charAt(i+j) != pattern.charAt(j))
                            break;
                    }

                    // if p == t and pat[0...M-1] = txt[i, i+1, ...i+M-1]
                    if (j == M)
                        return file.getName() + ": " + documentType;
                }

                // Calculate hash value for next window of text: Remove
                // leading digit, add trailing digit
                if ( i < N-M )
                {
                    t = (d*(t - text.charAt(i)*h) + text.charAt(i+M))%q;

                    // We might get negative value of t, converting it
                    // to positive
                    if (t < 0)
                        t = (t + q);
                }
            }
            return file.getName() + ": Unknown file type";
        }

        String analyzeFileTypePrior() throws FileNotFoundException {
            List<List<String>> patterns = laodPatterns();

            for (int i = 0; i < patterns.size(); i++){
                this.pattern = patterns.get(i).get(1).replaceAll("\"", "");
                this.documentType = patterns.get(i).get(2).replaceAll("\"", "");

                String result = analyzeFileTypeKMP();
                if (!result.equals(new File(filename).getName() + ": Unknown file type")){
                    return result;
                }
            }
            return new File(filename).getName() + ": Unknown file type";
        }
}
