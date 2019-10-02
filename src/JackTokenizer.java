import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JackTokenizer {

    private static char[] SYMBOLS = new char[]{'{', '}', '(', ')', '[', ']', '.', ',', ';', '+', '-', '*', '/', '&', '|', '<', '>', '=', '~'};
    private static final char QUOTE = '"';
    private static final String[] KEYWORDS = new String[]{"class", "constructor", "function", "method", "field", "static", "var", "int", "char", "boolean", "void", "true", "false", "null", "this", "let", "do", "if", "else", "while", "return"};

    private static ArrayList<String> split(String word){
        ArrayList<String> output = new ArrayList<>();

        String inside = "";
        String buffer = "";
        char[] wordChars = word.toCharArray();

        for(int i = 0; i < wordChars.length; i++)
        {
            //prototype
            /*if(wordChars[i] == '"')
        {
            for(int j = i + 1; j < wordChars.length; j++)
            {
                inside += wordChars[i];
                if(wordChars[j] == '"')
                    break;
            }
        }*/
            boolean isSymbol = false;
            for(int j = 0; j < SYMBOLS.length; j++)
            {
                if(wordChars[i] == SYMBOLS[j]) {
                    isSymbol = true;
                    break;
                }
            }
            isSymbol |= wordChars[i] == QUOTE;

            if(isSymbol){
                if(buffer.length() > 0){
                    output.add(buffer);
                    buffer = "";
                }
                output.add(wordChars[i] + "");
            }

            else{
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
                } else if(word.length() != 0){
                    tokens.addAll(split(word));
                }


            }
        }

        for(int i = 0; i < tokens.size(); i++){
            System.out.println(tokens.get(i));
        }

        sc.close();
    }

}
