import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void printLexerOutput(ArrayList<Object>[] lexer_output) {
        String outstr = "";
        for(ArrayList<Object> arr : lexer_output) {
            for(Object o : arr) {
                outstr += o.toString() + " ";
            }
            outstr += "; ";
        }
        outstr += "\n";
        System.out.printf(outstr);
    }

    public static String parse(String s) {
        Com.source = s.lines().map(String::strip).collect(Collectors.joining("\n"));
        Com.isString = Parser.findStrings(Com.source);
        Com.source = Parser.removeComments(Com.source, Com.isString);
        Com.source = Com.source.lines().map(String::strip).collect(Collectors.joining("\n"));
        Com.isString = Parser.findStrings(Com.source);    
        Com.loadLexerTokenMapData();
        Com.loadLexerTokens();
        Com.loadKeywordSetData();
        Com.initializeLexedCode();
        Lexer.lexSymbols(Com.source);
        Lexer.lexNumbers();
        Lexer.lexKeywords();
        Lexer.lexText();
        printLexerOutput(Com.lexedCode);
        return null; // temporary
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.printf("ERROR: Usage: <source file path> <destination file path>%n");
            System.exit(-1);
        }

        String sourceCode = null;
        try {
            sourceCode = Files.readString(Paths.get(args[0]), StandardCharsets.UTF_8);
        } catch(Exception e) {
            System.err.printf("ERROR: %s%n", e.getMessage());
            System.exit(-1);
        }

        String out = parse(sourceCode);

        File outputFile = new File(args[1]);
        try {
            if(outputFile.createNewFile()) {
                System.out.printf("File %s created successfully%n", args[1]);
            } else {
                System.out.printf("File %s already exists, would you like to overwrite it? (y/N)%n", args[1]);
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine().strip().toLowerCase();
                if(!input.equals("y") && !input.equals("yes")) {
                    System.exit(-2);
                }
                sc.close();
            }
        } catch (Exception e) {
            System.err.printf("ERROR: %s%n", e.getMessage());
            System.exit(-1);
        }

        try {
            Files.writeString(Paths.get(args[1]), out);
        } catch (Exception e) {
            System.err.printf("ERROR: %s%n", e.getMessage());
            System.exit(-1);
        }
    }
}
