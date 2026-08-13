import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class com {
    public static String source = new String();

    public static char virtual_new_line_symbol = ';';
    public static char[] string_delimiters = {'"', '\''};
    public static String[][] comment_delimiter_pairs = {{"//", "\n"}, {"/*", "*/"}};

    public static ArrayList<Integer> semicolon_positions = new ArrayList<>();
    public static BitSet is_string = new BitSet();

    
    public static Map<String, types.lexer_token> lexer_token_map = new HashMap<>();
    public static ArrayList<String> lexer_tokens = new ArrayList<>();
    public static Object[][] lexer_token_map_data = new Object[][]{ // of the form (String, types.lexer_token)
        {"#", types.lexer_token.DIRECTIVE},
        {"!", types.lexer_token.NOT},
        {".<-", types.lexer_token.UNASSIGNMENT},
        {":\n", types.lexer_token.LABEL},
        {"<-", types.lexer_token.ASSIGNMENT},
        {"~", types.lexer_token.WHILE},
        {"==", types.lexer_token.EQUAL_TO},
        {"<", types.lexer_token.LESS_THAN},
        {">", types.lexer_token.GREATER_THAN},
        {"<=", types.lexer_token.LESS_THAN_EQUAL_TO},
        {">=", types.lexer_token.GREATER_THAN_EQUAL_TO},
        {"!=", types.lexer_token.NOT_EQUAL_TO},
        {"<<", types.lexer_token.BITSHIFT_LEFT},
        {">>", types.lexer_token.BITSHIFT_RIGHT},
        {"^<", types.lexer_token.BITROTATE_LEFT},
        {"^>", types.lexer_token.BITROTATE_RIGHT},
        {"/", types.lexer_token.DIVIDE},
        {".", types.lexer_token.DOT},
        {"->", types.lexer_token.STRUCTURE_DEREFERNCE},
        {"::", types.lexer_token.NAMESPACE_RESOLUTION},
        {",", types.lexer_token.LIST_SEPARATOR},
        {"%", types.lexer_token.MODULO},
        {"?:", types.lexer_token.ELVIS},
        {"&", types.lexer_token.AMPERSAND},
        {"*", types.lexer_token.ASTERISK},
        {"+", types.lexer_token.PLUS},
        {"-", types.lexer_token.MINUS},
        {"^", types.lexer_token.CARET},
        {"|", types.lexer_token.VERTICAL_LINE},
        {"?", types.lexer_token.TERNARY_PART1},
        {"fn", types.lexer_token.FUNCTION_DEF},
        {"endfn", types.lexer_token.FUNCTION_END},
        {"(", types.lexer_token.PAREN_LEFT},
        {")", types.lexer_token.PAREN_RIGHT},
        {"[", types.lexer_token.SQUARE_BRACKET_LEFT},
        {"]", types.lexer_token.SQUARE_BRACKET_RIGHT},
        {"\"", types.lexer_token.DOUBLE_QUOTE},
        {"'", types.lexer_token.QUOTE},
        {"{", types.lexer_token.CURLY_LEFT},
        {"}", types.lexer_token.CURLY_RIGHT},
        {":", types.lexer_token.COLON},
        {" ", types.lexer_token.SPACE},
        {";", types.lexer_token.ENDLINE}
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

    public static void load_lexer_tokens() {
        for(Object[] pair : lexer_token_map_data) {
            lexer_tokens.add(String.valueOf(pair[0]));
        }
    }
}
