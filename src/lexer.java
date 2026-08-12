import java.util.ArrayList;

public class lexer {
    public static String match_string(String s, int position) {
        if(position >= s.length() || position < 0) {
            System.err.printf("ERROR: Invalid position found in match_string(), returning NONE");
            return "NONE";
        }

        ArrayList<String> candidates = new ArrayList<>();
        ArrayList<Integer> candidate_lengths = new ArrayList<>();

        for(String token : com.lexer_tokens) {
            if(s.regionMatches(position, token, 0, token.length())) {
                candidates.add(token);
                candidate_lengths.add(token.length());
            }
        }

        if(candidates.isEmpty()) return "NONE";

        int largest_candidate_index = -1;
        int largest_candidate_length = -1;
        int index = 0;
        for(Integer candidate_length : candidate_lengths) {
            if(candidate_length > largest_candidate_length) {
                largest_candidate_length = candidate_length;
                largest_candidate_index = index;
            }
            index++;
        }

        return candidates.get(largest_candidate_index);
    }

    public static ArrayList<Object>[] lex_tokens(String[] lines) {
        ArrayList<Object>[] tokens_and_strings = new ArrayList[lines.length];

        for(int i = 0; i < lines.length; i++) {
            tokens_and_strings[i] = new ArrayList<>();
        }

        int character_position;
        String current_token;
        int line_number = 0;
        for(String line : lines) {
            character_position = 0;

            while(character_position < line.length()) {
                current_token = match_string(line, character_position);
                Object token_and_strings_last_element_of_this_line;
                if(!tokens_and_strings[line_number].isEmpty()) {
                    token_and_strings_last_element_of_this_line = tokens_and_strings[line_number].get(tokens_and_strings[line_number].size() - 1);
                } else {
                    token_and_strings_last_element_of_this_line = null;
                }

                if(current_token.equals("NONE")) {
                    if(token_and_strings_last_element_of_this_line instanceof String) {
                        tokens_and_strings[line_number].set(tokens_and_strings[line_number].size() - 1, token_and_strings_last_element_of_this_line + line.substring(character_position, character_position + 1));
                    } else {
                        tokens_and_strings[line_number].add(line.substring(character_position, character_position + 1));
                    }
                    character_position++;
                } else {
                    tokens_and_strings[line_number].add(com.lexer_token_map.get(current_token));
                    character_position += current_token.length();
                }
            }
            line_number++;
        }
        return tokens_and_strings;
    }
}
