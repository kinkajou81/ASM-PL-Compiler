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

    public static ArrayList<Object>[] lex_symbols(String s) {
        ArrayList<Object>[] output = new ArrayList[2];

        output[0] = new ArrayList<>(); // 0 == tokens and strings
        output[1] = new ArrayList<>(); // 1 == string token's original data // all elements should always be a String object

        int character_position = 0;
        String current_token;

        while(character_position < s.length()) {
            current_token = match_string(s, character_position);
            Object outputs_last_element;

            if(!output[0].isEmpty()) {
                outputs_last_element = output[0].get(output[0].size() - 1);
            } else {
                outputs_last_element = null;
            }

            if(com.is_string.get(character_position)) {
                if(outputs_last_element == types.lexer_token.STRING) {
                    if(!output[1].isEmpty()) output[1].set(output[1].size() - 1, output[1].get(output[1].size() - 1) + s.substring(character_position, character_position + 1));
                    else output[1].add(s.substring(character_position, character_position + 1));
                } else {
                    output[0].add(types.lexer_token.STRING);
                    output[1].add(s.substring(character_position, character_position + 1));
                }
                character_position++;
            } else if(current_token.equals("NONE")) {
                if(outputs_last_element instanceof String) {
                    output[0].set(output[0].size() - 1, outputs_last_element + s.substring(character_position, character_position + 1));
                } else {
                    output[0].add(s.substring(character_position, character_position + 1));
                }
                character_position++;
            } else {
                output[0].add(com.lexer_token_map.get(current_token));
                character_position += current_token.length();
            }
        }
        return output;
    }
}
