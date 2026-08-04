import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    // creates an array of an array of nesting depth for pair delimiters; also records positions of semicolons
    public static int[][] mark_paired_delimiter(String s) {
        int delimiter_cnt = com.array_delimiter_pairs.length;
        ArrayList<Integer> delimiter_list = new ArrayList<>();
        int[][] delimiter_depth = new int[s.length()][delimiter_cnt];

        boolean inside_string = false;
        int which_string = -1;
        int line_num = 0;
        boolean sharing_delimiter = false;

        com.semicolon_positions.add(0); // to reduce complexity later on

        int i = 0;
        int j = 0;
        while(i < s.length()) {
            j = 0;
            if(i > 0) System.arraycopy(delimiter_depth[i - 1], 0, delimiter_depth[i], 0, (delimiter_depth[i - 1]).length);

            if((i + com.end_line_symbol.length()) <= s.length()) {
                if((s.substring(i, i + com.end_line_symbol.length())).equals(com.end_line_symbol) && !inside_string) {
                    for(int k = 0; k < delimiter_cnt; k++)
                        if(!com.persistent_delimiters[k]) {
                            if(delimiter_depth[i][k] != 0) {
                                System.err.printf("ERROR: Array delimiter pair of index %d defined as: " + Arrays.toString(com.array_delimiter_pairs[k]) + " is not closed on line: %d%n", k, line_num);
                                System.exit(-1);
                            }
                        }
                    com.semicolon_positions.add(i);
                    line_num++;
                    i++;
                    continue;
                }
            }

            while(j < delimiter_cnt) {
                if(com.array_delimiter_pairs[j].length != 2) {
                    System.err.printf("ERROR: Array delimiter pair of index %d defined as: " + Arrays.toString(com.array_delimiter_pairs[j]) + " is not a pair of 2%n", j);
                    System.exit(-1);
                }

                if((i + com.array_delimiter_pairs[j][1].length()) <= s.length()) {
                    if((s.substring(i, i + com.array_delimiter_pairs[j][1].length())).equals(com.array_delimiter_pairs[j][1]) && ((sharing_delimiter == com.shareable_delimiter[j][1]) || com.shareable_delimiter[j][1])) {
                        if(com.string_pairs[j] && (j == which_string)) {inside_string = false; which_string = -1;}
                        else if(inside_string) {j++; continue;}

                        if(delimiter_list.size() < 1) {
                            System.err.printf("ERROR: Array delimiter pair of index %d defined as: " + Arrays.toString(com.array_delimiter_pairs[j]) + " is not closed correctly on line: %d%n", j, line_num);
                            System.exit(-1);
                        } else if(delimiter_list.get(delimiter_list.size() - 1) != j) {
                            System.err.printf("ERROR: Array delimiter pair of index %d defined as: " + Arrays.toString(com.array_delimiter_pairs[j]) + " is not closed correctly on line: %d%n", j, line_num);
                            System.exit(-1);
                        }

                        delimiter_list.remove(delimiter_list.size() - 1);
                        delimiter_depth[i][j] -= 1;

                        if(com.shareable_delimiter[j][1] && sharing_delimiter) {sharing_delimiter = false;}
                        else if(com.shareable_delimiter[j][1] && !sharing_delimiter) {sharing_delimiter = true; j++; continue;}
                    }
                }

                if((i + com.array_delimiter_pairs[j][0].length()) <= s.length()) {
                    if((s.substring(i, i + com.array_delimiter_pairs[j][0].length())).equals(com.array_delimiter_pairs[j][0]) && ((sharing_delimiter == com.shareable_delimiter[j][0]) || com.shareable_delimiter[j][0])) {
                        if(!inside_string) {
                            delimiter_list.add(j);
                            delimiter_depth[i][j] += 1;
                            if(com.string_pairs[j]) {inside_string = true; which_string = j;}
                        } else {j++; continue;}

                        if(com.shareable_delimiter[j][0] && sharing_delimiter) {sharing_delimiter = false;}
                        else if(com.shareable_delimiter[j][0] && !sharing_delimiter) {sharing_delimiter = true; j++; continue;}

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

    public static String parse(String s) {
        com.source = s.stripIndent();
        int[][] depth = mark_paired_delimiter(com.source);
        return null; // temporary
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.printf("Usage: <source file path> <destination file path>%n");
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
