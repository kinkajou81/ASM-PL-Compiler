import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Com {
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
    
    public static Map<String, Types.lexerToken> lexerTokenMap = new HashMap<>();
    public static ArrayList<String> lexerTokens = new ArrayList<>();
    public static Object[][] lexerTokenMapData = new Object[][]{ // of the form (String, Types.lexerToken)
        {"#", Types.lexerToken.DIRECTIVE},
        {"!", Types.lexerToken.NOT},
        {".<-", Types.lexerToken.UNASSIGNMENT},
        {":\n", Types.lexerToken.LABEL},
        {"<-", Types.lexerToken.ASSIGNMENT},
        {"~", Types.lexerToken.WHILE},
        {"==", Types.lexerToken.EQUAL_TO},
        {"<", Types.lexerToken.LESS_THAN},
        {">", Types.lexerToken.GREATER_THAN},
        {"<=", Types.lexerToken.LESS_THAN_EQUAL_TO},
        {">=", Types.lexerToken.GREATER_THAN_EQUAL_TO},
        {"!=", Types.lexerToken.NOT_EQUAL_TO},
        {"<<", Types.lexerToken.BITSHIFT_LEFT},
        {">>", Types.lexerToken.BITSHIFT_RIGHT},
        {"^<", Types.lexerToken.BITROTATE_LEFT},
        {"^>", Types.lexerToken.BITROTATE_RIGHT},
        {"/", Types.lexerToken.DIVIDE},
        {".", Types.lexerToken.DOT},
        {"->", Types.lexerToken.STRUCTURE_DEREFERNCE},
        {"::", Types.lexerToken.NAMESPACE_RESOLUTION},
        {",", Types.lexerToken.LIST_SEPARATOR},
        {"%", Types.lexerToken.MODULO},
        {"?:", Types.lexerToken.ELVIS},
        {"&", Types.lexerToken.AMPERSAND},
        {"*", Types.lexerToken.ASTERISK},
        {"+", Types.lexerToken.PLUS},
        {"-", Types.lexerToken.MINUS},
        {"^", Types.lexerToken.CARET},
        {"|", Types.lexerToken.VERTICAL_LINE},
        {"?", Types.lexerToken.TERNARY_PART1},
        {"(", Types.lexerToken.PAREN_LEFT},
        {")", Types.lexerToken.PAREN_RIGHT},
        {"[", Types.lexerToken.SQUARE_BRACKET_LEFT},
        {"]", Types.lexerToken.SQUARE_BRACKET_RIGHT},
        {"{", Types.lexerToken.CURLY_LEFT},
        {"}", Types.lexerToken.CURLY_RIGHT},
        {":", Types.lexerToken.COLON},
        {" ", Types.lexerToken.SPACE},
        {";", Types.lexerToken.ENDLINE},
        {"\n", Types.lexerToken.NEWLINE}
    };

    public static void loadLexerTokenMapData() {
        for(Object[] entry_pair : lexerTokenMapData) {
            if(entry_pair.length == 2) {
                lexerTokenMap.put((String)entry_pair[0], (Types.lexerToken)entry_pair[1]);
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

    public static Set<Object> pairDelimiterSetOpeners = new HashSet();
    public static Set<Object> pairDelimiterSetClosers = new HashSet();
    public static Map<Object, Object> pairDelimiterMapClosers = new HashMap();
    public static Map<Object, Integer> pairDelimiterIndicies = new HashMap();
    public static Object[][] pairDelimiterData = new Object[][] {
        {Types.lexerToken.PAREN_LEFT, Types.lexerToken.PAREN_RIGHT},
        {Types.lexerToken.SQUARE_BRACKET_LEFT, Types.lexerToken.SQUARE_BRACKET_RIGHT},
        {Types.lexerToken.CURLY_LEFT, Types.lexerToken.CURLY_RIGHT},
        {"fn", "endfn"}
    };

    public static void loadPairDelimiterSets() {
        int index = 0;
        for(Object[] arr : pairDelimiterData) {
            pairDelimiterSetOpeners.add(arr[0]);
            pairDelimiterSetClosers.add(arr[1]);
            pairDelimiterMapClosers.put(arr[0], arr[1]);
            pairDelimiterIndicies.put(arr[0], index);
            pairDelimiterIndicies.put(arr[1], index);
            index++;
        }
    }

    public static ArrayList<Integer>[] pair_delimiter_depths = new ArrayList[pairDelimiterData.length];
}
