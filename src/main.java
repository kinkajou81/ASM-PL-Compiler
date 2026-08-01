import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.atomic;

public class Main {
    public static int[][] mark_paired_delimiter(String s, String[][] array_delimiter_pairs, boolean[] persistent_delimiters, boolean[] string_pair, String end_line_symbol) {
        int delimiter_cnt = array_delimiter_pairs.length;
        ArrayList<Integer> delimiter_list = new ArrayList();
        int[][] delimiter_depth = new int[s.length()][delimiter_cnt];

        boolean inside_string = false;
        int which_string = -1;
        int line_num = 0;

        int i = 0;
        int j = 0;
        while(i < s.length()) {
            j = 0;
            if(i > 0) System.arraycopy(delimiter_depth[i - 1], 0, delimiter_depth[i], 0, (delimiter_depth[i - 1]).length);

            if((i + end_line_symbol.length()) <= s.length()) {
                if((s.substring(i, i + end_line_symbol.length())).equals(end_line_symbol)) {
                    for(int k = 0; k < delimiter_cnt; k++)
                        if(!persistent_delimiters[k]) {
                            if(delimiter_depth[i][k] != 0) {
                                System.err.printf("ERROR: Array delimiter pair of index %d defined as: " + Arrays.toString(array_delimiter_pairs[k]) + " is not closed on line: %d%n", k, line_num);
                                System.exit(-1);
                            }
                        }
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
                    if((s.substring(i, i + array_delimiter_pairs[j][1].length())).equals(array_delimiter_pairs[j][1])) {
                        if(string_pair[j] && (j == which_string)) {inside_string = false; which_string = -1;}
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
                        break;
                    }
                }

                if((i + array_delimiter_pairs[j][0].length()) <= s.length()) {
                    if((s.substring(i, i + array_delimiter_pairs[j][0].length())).equals(array_delimiter_pairs[j][0])) {
                        if(!inside_string) {
                            delimiter_list.add(j);
                            delimiter_depth[i][j] += 1; 
                            if(string_pair[j]) {inside_string = true; which_string = j;}
                        } else {j++; continue;}
                        break;
                    }
                }
                j++;
            }
            i++;
        }
        return delimiter_depth;
    }

    public static String parse(String s) {
        s = s.stripIndent();
        int[][] depth = mark_paired_delimiter(s, null, null, null, ";");
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.printf("Usage: <source file path> <destination file path>%n");
            System.exit(-1);
        }

        try {
            String source = Files.readString(Paths.get(args[0]), StandardCharsets.UTF_8);
        } catch(Exception e) {
            System.err.printf("ERROR: %s%n", e.getMessage());
            System.exit(-1);
        }

        String out = parse(source);
    }
}