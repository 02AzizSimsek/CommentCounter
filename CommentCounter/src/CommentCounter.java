/**  
Aziz	þimþek
b201210394
bilgisayar mühendisligi
1. Öðretim C 
  
**/ 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.PrintWriter;
import java.io.File;
public class CommentCounter {

    public static void main(String[] args) throws IOException {

        // Dosya adý argüman olarak alýnýr
        String fileName = args[0];

        // Dosya okuyucusu oluþturulur
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        // Regex'ler tanýmlanýr
        Pattern functionPattern = Pattern.compile("(?s)(?<=\\n|\\A)\\s*(public|private|protected)?\\s*\\w+\\s+\\w+\\s*\\([^)]*\\)\\s*(?=\\{|;)");
        Pattern singleLineCommentPattern = Pattern.compile("//.*");
        Pattern multiLineCommentPattern = Pattern.compile("/\\*(.|\\s)*?\\*/");
        Pattern javadocPattern = Pattern.compile("/\\*\\*(.|\\s)*?\\*/");

        // Deðiþkenler tanýmlanýr
        String currentFunctionName = null;
        int singleLineCommentCount = 0;
        int multiLineCommentCount = 0;
        int javadocCount = 0;
        String singleComment = "";
        String multiComment = "";
        String javadoc = "";
        

        // Dosya satýr satýr okunur
        String line;
        while ((line = reader.readLine()) != null) {

            // Fonksiyon bulunursa fonksiyon ismi kaydedilir
            Matcher functionMatcher = functionPattern.matcher(line);
            if (functionMatcher.find()) {
                currentFunctionName = functionMatcher.group().trim().replaceAll("\\s+", " ");
            }

            // Yorumlar bulunur ve sayýlarý hesaplanýr
            Matcher singleLineCommentMatcher = singleLineCommentPattern.matcher(line);
            while (singleLineCommentMatcher.find()) {
                singleLineCommentCount++;
                singleComment += singleLineCommentMatcher.group(0) + System.lineSeparator();
               
                PrintWriter writer1 = new PrintWriter(new File("teksatir.txt"));
                writer1.write(singleComment);
                writer1.close();
            }

            Matcher multiLineCommentMatcher = multiLineCommentPattern.matcher(line);
            while (multiLineCommentMatcher.find()) {
                multiLineCommentCount++;
                multiComment +=multiLineCommentMatcher.group(0) + System.lineSeparator();
                
                PrintWriter writer2 = new PrintWriter(new File("coksatir.txt"));
                writer2.write(multiComment);
                writer2.close();
            }

            Matcher javadocMatcher = javadocPattern.matcher(line);
            while (javadocMatcher.find()) {
                javadocCount++;
                javadoc += javadocMatcher.group(0) + System.lineSeparator();
               
                PrintWriter writer3 = new PrintWriter(new File("javadoc.txt"));
                writer3.write(javadoc);
                writer3.close();
            }

            // Satýrda fonksiyon sonu varsa sonuçlarý ekrana yazdýr ve deðiþkenleri sýfýrla
            if (line.matches("\\s*\\}.*") && currentFunctionName != null) {
                System.out.println("Sýnýf: " + fileName.substring(0, fileName.indexOf(".")));
                System.out.println("Fonksiyon: " + currentFunctionName);
                System.out.println("Tek Satýr Yorum Sayýsý: " + singleLineCommentCount);
                System.out.println("Çok Satýrlý Yorum Sayýsý: " + multiLineCommentCount);
                System.out.println("Javadoc Yorum Sayýsý: " + javadocCount);
                System.out.println("------------------------------------");

                currentFunctionName = null;
                singleLineCommentCount = 0;
                multiLineCommentCount = 0;
                javadocCount = 0;
            }
        }

        reader.close();
    }
}