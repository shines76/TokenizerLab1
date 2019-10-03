import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JackTokenizer {

    private static char[] SYMBOLS = new char[]{'{', '}', '(', ')', '[', ']', '.', ',', ';', '+', '-', '*', '/', '&', '|', '<', '>', '=', '~'};
    private static final char QUOTE = '"';
    private static final String[] KEYWORDS = new String[]{"class", "constructor", "function", "method", "field", "static", "var", "int", "char", "boolean", "void", "true", "false", "null", "this", "let", "do", "if", "else", "while", "return"};

    private static ArrayList<String> split(String word) {
        ArrayList<String> output = new ArrayList<>();


        String buffer = "";
        char[] wordChars = word.toCharArray();

        for (int i = 0; i < wordChars.length; i++) {

            boolean isSymbol = false;
            for (int j = 0; j < SYMBOLS.length; j++) {
                if (wordChars[i] == SYMBOLS[j]) {
                    isSymbol = true;
                    break;
                }
            }
            isSymbol |= wordChars[i] == QUOTE;

            if (isSymbol) {
                if (buffer.length() > 0) {
                    output.add(buffer);
                    buffer = "";
                }
                output.add(wordChars[i] + "");
            } else {
                buffer += wordChars[i];
            }


        }
        if (buffer.length() != 0) {

            output.add(buffer);
        }

        return output;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File(args[0]);
        Scanner sc = new Scanner(input);
        ArrayList<String> tokens = new ArrayList<>();

        boolean inMultiLineComment = false;
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();


            ArrayList<String> savedQuotes = new ArrayList<>();
            int QuoteIndex = 0;
            for(int i = 0; i < line.length(); i++)
            {
                boolean nextWord = false;
                if (line.charAt(i) == '"')
                    if(nextWord) {
                        QuoteIndex++;
                        nextWord = false;
                    }
                    for(int j = i + 1; j < line.length(); j++)
                    {
                        if (line.charAt(j) == '"')
                        {
                            nextWord = true;
                            savedQuotes.add(QuoteIndex, "" + line.charAt(j));
                            line = line.substring(0,j+1) + line.substring(j + 1, line.length());

                        }
                        else
                        {
                            savedQuotes.add(QuoteIndex, "" + line.charAt(j));
                            line = line.substring(0,j+1) + line.substring(j + 1, line.length());

                        }
                    }
            }



            String[] words = line.split(" ");
            for (int i = 0; i < words.length; i++) {
                String word = words[i];

                if (inMultiLineComment) {
                    if (word.startsWith("*/"))
                        inMultiLineComment = false;

                } else if (word.startsWith("//")) {
                    break;
                } else if (word.startsWith("/*")) {
                    inMultiLineComment = true;
                } else if (word.length() != 0) {
                    tokens.addAll(split(word));
                }


            }
        }

        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(tokens.get(i));
        }

        sc.close();
    }

}
