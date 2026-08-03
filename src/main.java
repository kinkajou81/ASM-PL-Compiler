import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
java.util.Arrays;

public class Main {
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
    public static String end_line_symbol = ";";

    public static ArrayList<Integer> semicolon_positions = new ArrayList<>();

    public enum type_attribute {
        NULL
    }

    public enum function_attribute {
        NULL
    }

    public class type {
        public String name;
        public ArrayList<type_attribute> attributes;

        public type() {
        	name = ""; attributes = new ArrayList<>();
        }

        public type(String a) {
        	name = a; attributes = new ArrayList<>();
        }

        public type add(type_attribute a) {
            attributes.add(a);
        }
    }

    public class variable {
        public String name;
        public type datatype;

        public variable() {
            name = ""; datatype = new type();
        }

        public variable(String a, type b) {
            name = a; datatype = b;
        }
    }

    public class function {
        public String name;
        public ArrayList<variable> io_variables;
        public ArrayList<Boolean> is_input;
        public ArrayList<function_attribute> attributes;

        public function() {
            name = ""; io_variables = new ArrayList<>(); is_input = new ArrayList<>(); attributes = new ArrayList<>();
        }

        public function(String a) {
            name = a; io_variables = new ArrayList<>(); is_input = new ArrayList<>(); attributes = new ArrayList<>();
        }

        public function(String a, ArrayList<variable> b, ArrayList<Boolean> c) {
            name = a; io_variables = b; is_input = c; attributes = new ArrayList<>();
        }

        public function add(function_attribute a) {
            attributes.add(a);
        }
    }

    public static int[][] mark_paired_delimiter(String s) {
        int delimiter_cnt = array_delimiter_pairs.length;
        ArrayList<Integer> delimiter_list = new ArrayList<>();
        int[][] delimiter_depth = new int[s.length()][delimiter_cnt];

        boolean inside_string = false;
        int which_string = -1;
        int line_num = 0;
        boolean sharing_delimiter = false;

        int i = 0;
        int j = 0;
        while(i < s.length()) {
            j = 0;
            if(i > 0) System.arraycopy(delimiter_depth[i - 1], 0, delimiter_depth[i], 0, (delimiter_depth[i - 1]).length);

            if((i + end_line_symbol.length()) <= s.length()) {
                if((s.substring(i, i + end_line_symbol.length())).equals(end_line_symbol) && !inside_string) {
                    for(int k = 0; k < delimiter_cnt; k++)
                        if(!persistent_delimiters[k]) {
                            if(delimiter_depth[i][k] != 0) {
                                System.err.printf("ERROR: Array delimiter pair of index %d defined as: " + Arrays.toString(array_delimiter_pairs[k]) + " is not closed on line: %d%n", k, line_num);
                                System.exit(-1);
                            }
                        }
                    semicolon_positions.add(i);
                    line_num++;
                    i++;
                    continue;
                }
            }

            while(j < delimiter_cnt) {
                if(array_delimiter_pairs[j].length != 2) {
                    System.err.printf("ERROR: Array delimiter pair of index %d defined as: " + Arrays.toString(array_delimiter_pairs[j]) + " is not a pair of 2%n", j);
                    System.exit(-1);
                }

                if((i + array_delimiter_pairs[j][1].length()) <= s.length()) {
                    if((s.substring(i, i + array_delimiter_pairs[j][1].length())).equals(array_delimiter_pairs[j][1]) && ((sharing_delimiter == shareable_delimiter[j][1]) || shareable_delimiter[j][1])) {
                        if(string_pairs[j] && (j == which_string)) {inside_string = false; which_string = -1;}
                        else if(inside_string) {j++; continue;}

                        if(delimiter_list.size() < 1) {
                            System.err.printf("ERROR: Array delimiter pair of index %d defined as: " + Arrays.toString(array_delimiter_pairs[j]) + " is not closed correctly on line: %d%n", j, line_num);
                            System.exit(-1);
                        } else if(delimiter_list.get(delimiter_list.size() - 1) != j) {
                            System.err.printf("ERROR: Array delimiter pair of index %d defined as: " + Arrays.toString(array_delimiter_pairs[j]) + " is not closed correctly on line: %d%n", j, line_num);
                            System.exit(-1);
                        }

                        delimiter_list.remove(delimiter_list.size() - 1);
                        delimiter_depth[i][j] -= 1;

                        if(shareable_delimiter[j][1] && sharing_delimiter) {sharing_delimiter = false;}
                        else if(shareable_delimiter[j][1] && !sharing_delimiter) {sharing_delimiter = true; j++; continue;}
                    }
                }

                if((i + array_delimiter_pairs[j][0].length()) <= s.length()) {
                    if((s.substring(i, i + array_delimiter_pairs[j][0].length())).equals(array_delimiter_pairs[j][0]) && ((sharing_delimiter == shareable_delimiter[j][0]) || shareable_delimiter[j][0])) {
                        if(!inside_string) {
                            delimiter_list.add(j);
                            delimiter_depth[i][j] += 1;
                            if(string_pairs[j]) {inside_string = true; which_string = j;}
                        } else {j++; continue;}

                        if(shareable_delimiter[j][0] && sharing_delimiter) {sharing_delimiter = false;}
                        else if(shareable_delimiter[j][0] && !sharing_delimiter) {sharing_delimiter = true; j++; continue;}

                        break;
                    }
                }
                j++;
            }
            sharing_delimiter = false;
            i++;
        }
        return delimiter_depth;
    }

    public static String classify_line(String s) {

    }

    public static String parse(String s) {
        s = s.stripIndent();
        int[][] depth = mark_paired_delimiter(s);
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.printf("Usage: <source file path> <destination file path>%n");
            System.exit(-1);
        }

        String source = null;
        try {
            source = Files.readString(Paths.get(args[0]), StandardCharsets.UTF_8);
        } catch(Exception e) {
            System.err.printf("ERROR: %s%n", e.getMessage());
            System.exit(-1);
        }

        String out = parse(source);
    }
}
