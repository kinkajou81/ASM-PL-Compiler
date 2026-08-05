import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class com {
    public static String source = new String();

    public static char virtual_new_line_symbol = ';';
    public static char[] string_delimiters = {'"', '\''};
    public static String[][] comment_delimiter_pairs = {{"//", "\n"}, {"/*", "*/"}};

    public static ArrayList<Integer> semicolon_positions = new ArrayList<>();

    
    public static Map<String, types.lexer_token> lexer_token_map = new HashMap<>();
    public static Object[][] lexer_token_map_data = new Object[][]{ // of the form (String, types.lexer_token)
        {} // temporarily empty
    };

    public static void load_lexer_token_map_data() {
        for(Object[] entry_pair : lexer_token_map_data) {
            if(entry_pair.length == 2) {
                lexer_token_map.put((String)entry_pair[0], (types.lexer_token)entry_pair[1]);
            } else {
                System.err.printf("ERROR: Malformed lexer_token_map_data");
                System.exit(-1);
            }
        }
    }
}
