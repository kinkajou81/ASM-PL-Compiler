import java.util.ArrayList;
import java.util.BitSet;

public class parser {
    public static ArrayList<String> listify_virtual_lines(String s) {
        int last_new_line_position = 0;
        ArrayList<String> virtual_lines = new ArrayList<>();

        int current_char_position = 0;
        while (current_char_position < s.length()) {
            if(s.charAt(current_char_position) == com.virtual_new_line_symbol) {
                virtual_lines.add(s.substring(last_new_line_position, current_char_position));
                last_new_line_position = current_char_position + 1;
            }
            current_char_position++;
        }
        if(current_char_position > last_new_line_position) {
            virtual_lines.add(s.substring(last_new_line_position, current_char_position));
        }
        return virtual_lines;
    }

    public static BitSet find_strings(String s) {
        BitSet is_string = new BitSet();

        boolean inside_string = false;
        int string_type = -1;

        int i = 0;
        int j;
        while(i < s.length()) {
            j = 0;
            if(inside_string) is_string.set(i);

            while(j < com.string_delimiters.length) {
                if(s.charAt(i) == com.string_delimiters[j]) {
                    if(j == string_type && inside_string) {
                        if(i > 0) if(s.charAt(i - 1) != '\\') {
                            inside_string = false;
                            string_type = -1;
                        } // no (i==0) case since a string cannot be opened and closed in one character
                    } else if(!inside_string) {
                        inside_string = true;
                        string_type = j;
                        is_string.set(i);
                    }
                    break;
                }
                j++;
            }
            i++;
        }
        return is_string;
    }

    public static String parse_lines(String s) {
        int i = 0;
        int j;
        int k = 0;
        int number_of_stages = 0;
        while(i < com.semicolon_positions.size()) {
            if(i == 0) {i++; continue;}
            j = com.semicolon_positions.get(i);
            while(j > com.semicolon_positions.get(i - 1)) {
                while(k < number_of_stages) {
                    
                }
            }
            i++;
        }
        return null; // temporary
    }
}
