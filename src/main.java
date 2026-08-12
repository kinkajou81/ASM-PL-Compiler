import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static String parse(String s) {
        com.source = s.stripIndent();
        com.is_string = parser.find_strings(com.source);
        com.source = parser.remove_comments(com.source, com.is_string);
        com.load_lexer_token_map_data();
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
    }
}
