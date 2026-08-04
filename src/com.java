import java.util.ArrayList;

public class com {
    public static String source = new String();

    public static String[][] array_delimiter_pairs = new String[][]{
        {"(", ")"},
        {"[", "]"},
        {"{", "}"},
        {"//", "\n"},
        {"\"", "\""},
        {"'", "'"},
        {"#", "\n"},
        {"fn", "endfn"},
        {"<", ">"},
        {"tp", "endtp"}
    };
    public static boolean[][] shareable_delimiter = new boolean[][]{
        {false, false},
        {false, false},
        {false, false},
        {false, true},
        {false, false},
        {false, false},
        {false, true},
        {false, false},
        {false, false},
        {false, false}

    };
    public static boolean[] persistent_delimiters = new boolean[]{false, false, true, false, false, false, false, true, false, true};
    public static boolean[] string_pairs = new boolean[]{false, false, false, true, true, true, true, false, false, false};
    public static char virtual_new_line_symbol = ';';

    public static ArrayList<Integer> semicolon_positions = new ArrayList<>();
}
