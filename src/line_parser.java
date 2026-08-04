import java.util.ArrayList;

public class line_parser {
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
