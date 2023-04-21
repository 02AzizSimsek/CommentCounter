/**  
Aziz	�im�ek
b201210394
bilgisayar m�hendisligi
1. ��retim C 
  
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

        // Dosya ad� arg�man olarak al�n�r
        String fileName = args[0];

        // Dosya okuyucusu olu�turulur
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        // Regex'ler tan�mlan�r
        Pattern functionPattern = Pattern.compile("(?s)(?<=\\n|\\A)\\s*(public|private|protected)?\\s*\\w+\\s+\\w+\\s*\\([^)]*\\)\\s*(?=\\{|;)");
        Pattern singleLineCommentPattern = Pattern.compile("//.*");
        Pattern multiLineCommentPattern = Pattern.compile("/\\*(.|\\s)*?\\*/");
        Pattern javadocPattern = Pattern.compile("/\\*\\*(.|\\s)*?\\*/");

        // De�i�kenler tan�mlan�r
        String currentFunctionName = null;
        int singleLineCommentCount = 0;
        int multiLineCommentCount = 0;
        int javadocCount = 0;
        String singleComment = "";
        String multiComment = "";
        String javadoc = "";
        

        // Dosya sat�r sat�r okunur
        String line;
        while ((line = reader.readLine()) != null) {

            // Fonksiyon bulunursa fonksiyon ismi kaydedilir
            Matcher functionMatcher = functionPattern.matcher(line);
            if (functionMatcher.find()) {
                currentFunctionName = functionMatcher.group().trim().replaceAll("\\s+", " ");
            }

            // Yorumlar bulunur ve say�lar� hesaplan�r
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

            // Sat�rda fonksiyon sonu varsa sonu�lar� ekrana yazd�r ve de�i�kenleri s�f�rla
            if (line.matches("\\s*\\}.*") && currentFunctionName != null) {
                System.out.println("S�n�f: " + fileName.substring(0, fileName.indexOf(".")));
                System.out.println("Fonksiyon: " + currentFunctionName);
                System.out.println("Tek Sat�r Yorum Say�s�: " + singleLineCommentCount);
                System.out.println("�ok Sat�rl� Yorum Say�s�: " + multiLineCommentCount);
                System.out.println("Javadoc Yorum Say�s�: " + javadocCount);
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