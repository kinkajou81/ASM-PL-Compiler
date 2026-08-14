import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static String parse(String s) {
        com.source = s.lines().map(String::strip).collect(Collectors.joining("\n"));
        com.is_string = parser.find_strings(com.source);
        com.source = parser.remove_comments(com.source, com.is_string);
        com.is_string = parser.find_strings(com.source);
        com.load_lexer_token_map_data();
        com.load_lexer_tokens();
        return null; // temporary
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.printf("ERROR: Usage: <source file path> <destination file path>%n");
            System.exit(-1);
        }

        String source_code = null;
        try {
            source_code = Files.readString(Paths.get(args[0]), StandardCharsets.UTF_8);
        } catch(Exception e) {
            System.err.printf("ERROR: %s%n", e.getMessage());
            System.exit(-1);
        }

        String out = parse(source_code);

        File output_file = new File(args[1]);
        try {
            if(output_file.createNewFile()) {
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
