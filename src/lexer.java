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
}
