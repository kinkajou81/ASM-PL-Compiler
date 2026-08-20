import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class com {
    public static MathContext mc = new MathContext(40, RoundingMode.CEILING);

    public static String source = new String();

    public static char virtualNewLineSymbol = ';';
    public static char[] stringDelimiters = {'"', '\''};
    public static String[][] commentDelimiterPairs = {{"//", "\n"}, {"/*", "*/"}};

    public static ArrayList<Integer> semicolonPositions = new ArrayList<>();
    public static BitSet isString = new BitSet();

    public static ArrayList<Object>[] lexedCode = new ArrayList[5];
        // [0] == tokens and strings // later on just tokens
        // [1] == string token's original data // all elements should always be a String
        // [2] == numeric token's original data // all elements should always be a BigDecimal
        // [3] == keyword token's original data // all elements should always be a String
        // [4] == text token's original data // all elements should always be a String

    public static void initializeLexedCode() {
        for(int i = 0; i < lexedCode.length; i++) {
            lexedCode[i] = new ArrayList<>();
        }
    }
    
    public static Map<String, types.lexerToken> lexerTokenMap = new HashMap<>();
    public static ArrayList<String> lexerTokens = new ArrayList<>();
    public static Object[][] lexerTokenMapData = new Object[][]{ // of the form (String, types.lexerToken)
        {"#", types.lexerToken.DIRECTIVE},
        {"!", types.lexerToken.NOT},
        {".<-", types.lexerToken.UNASSIGNMENT},
        {":\n", types.lexerToken.LABEL},
        {"<-", types.lexerToken.ASSIGNMENT},
        {"~", types.lexerToken.WHILE},
        {"==", types.lexerToken.EQUAL_TO},
        {"<", types.lexerToken.LESS_THAN},
        {">", types.lexerToken.GREATER_THAN},
        {"<=", types.lexerToken.LESS_THAN_EQUAL_TO},
        {">=", types.lexerToken.GREATER_THAN_EQUAL_TO},
        {"!=", types.lexerToken.NOT_EQUAL_TO},
        {"<<", types.lexerToken.BITSHIFT_LEFT},
        {">>", types.lexerToken.BITSHIFT_RIGHT},
        {"^<", types.lexerToken.BITROTATE_LEFT},
        {"^>", types.lexerToken.BITROTATE_RIGHT},
        {"/", types.lexerToken.DIVIDE},
        {".", types.lexerToken.DOT},
        {"->", types.lexerToken.STRUCTURE_DEREFERNCE},
        {"::", types.lexerToken.NAMESPACE_RESOLUTION},
        {",", types.lexerToken.LIST_SEPARATOR},
        {"%", types.lexerToken.MODULO},
        {"?:", types.lexerToken.ELVIS},
        {"&", types.lexerToken.AMPERSAND},
        {"*", types.lexerToken.ASTERISK},
        {"+", types.lexerToken.PLUS},
        {"-", types.lexerToken.MINUS},
        {"^", types.lexerToken.CARET},
        {"|", types.lexerToken.VERTICAL_LINE},
        {"?", types.lexerToken.TERNARY_PART1},
        {"(", types.lexerToken.PAREN_LEFT},
        {")", types.lexerToken.PAREN_RIGHT},
        {"[", types.lexerToken.SQUARE_BRACKET_LEFT},
        {"]", types.lexerToken.SQUARE_BRACKET_RIGHT},
        {"{", types.lexerToken.CURLY_LEFT},
        {"}", types.lexerToken.CURLY_RIGHT},
        {":", types.lexerToken.COLON},
        {" ", types.lexerToken.SPACE},
        {";", types.lexerToken.ENDLINE},
        {"\n", types.lexerToken.NEWLINE}
    };

    public static void loadLexerTokenMapData() {
        for(Object[] entry_pair : lexerTokenMapData) {
            if(entry_pair.length == 2) {
                lexerTokenMap.put((String)entry_pair[0], (types.lexerToken)entry_pair[1]);
            } else {
                System.err.printf("ERROR: Malformed lexerTokenMapData");
                System.exit(-1);
            }
        }
    }

    public static void loadLexerTokens() {
        for(Object[] pair : lexerTokenMapData) {
            lexerTokens.add(String.valueOf(pair[0]));
        }
    }

    public static Set<String> keywordSet = new HashSet();
    public static String[] keywordSetData = new String[] {
        "const",
        "namespace",
        "fn",
        "endfn"
    };

    public static void loadKeywordSetData() {
        for(String s : keywordSetData) {
            keywordSet.add(s);
        }
    }

}
