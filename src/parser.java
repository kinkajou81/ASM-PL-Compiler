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

    public static String remove_comments(String s, BitSet is_string) {
        BitSet is_comment = new BitSet();

        boolean inside_comment = false;
        int comment_type = -1;

        int i = 0;
        int j;
        while(i < s.length()) {
            j = 0;
            if(inside_comment) is_comment.set(i);
            if(is_string.get(i)) {i++; continue;}

            while(j < com.comment_delimiter_pairs.length) {
                if(i + com.comment_delimiter_pairs[j][1].length() <= s.length()) {
                    if((s.substring(i, i + com.comment_delimiter_pairs[j][1].length())).equals(com.comment_delimiter_pairs[j][1])
                        && j == comment_type && inside_comment) {
                            
                        inside_comment = false;
                        comment_type = -1;
                        is_comment.clear(i);
                        break;
                    }
                }
                if(i + com.comment_delimiter_pairs[j][0].length() <= s.length()) {
                    if((s.substring(i, i + com.comment_delimiter_pairs[j][0].length())).equals(com.comment_delimiter_pairs[j][0])
                        && !inside_comment) {

                        inside_comment = true;
                        comment_type = j;
                        is_comment.set(i);
                        break;
                    }
                }
                j++;
            }
            i++;
        }
        i = 0;
        StringBuilder output_builder = new StringBuilder(s.length()); // size set to max size
        while(i < s.length()) {
            if(!is_comment.get(i)) output_builder.append(s.charAt(i));
            i++;
        }

        return output_builder.toString();
    }
}
