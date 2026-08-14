import java.math.BigDecimal;
import java.math.BigInteger;
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

    public static int count_fractional_digits(String s) {
        int decimal_point_index = s.indexOf('.');
        if (decimal_point_index == -1) {
            return 0;
        }
        return s.length() - decimal_point_index - 1;
    }

    public static String remove_decimal_point(String s) {
        int decimal_point_index = s.indexOf('.');
        if (decimal_point_index == -1) {
            return s;
        }
        return s.substring(0, decimal_point_index) + s.substring(decimal_point_index + 1);
    }

    public static BigDecimal string_to_bigdecimal(String s, int base) {
        if(base == 10) {
            return new BigDecimal(s);
        }
        return (new BigDecimal(new BigInteger(remove_decimal_point(s), base)))
               .divide(new BigDecimal((BigInteger.valueOf(base)).pow(count_fractional_digits(s))), com.mc);
    }

    public static ArrayList<Object>[] lex_numbers(ArrayList<Object>[] partially_lexed_code) {
        ArrayList<Object>[] output = new ArrayList[3];

        output[0] = partially_lexed_code[0];
        output[1] = partially_lexed_code[1];
        output[2] = new ArrayList<>(); // 2 == numeric token's original data // all elements should always be a BigDecimal

        boolean is_number;
        int number_base;
        int offset;
        int character_position;
        boolean is_fractional;

        int index = 0;
        Object o;
        while(index < output[0].size()) {
            o = output[0].get(index);

            if(o instanceof String) {
                is_number = true;
                is_fractional = true;
                String current_string = (String) o;

                if((current_string).startsWith("0b")) number_base = 2;
                else if((current_string).startsWith("0o")) number_base = 8;
                else if((current_string).startsWith("0x")) number_base = 16;
                else number_base = 10;

                offset = (number_base == 10)? 0: 2;
                character_position = offset;
                while(character_position < (current_string).length()) {
                    if(!Character.isDigit((current_string).charAt(character_position))) {
                        is_number = false;
                    }
                    character_position++;
                }

                if(index + 2 < output[0].size() && is_number) {
                    if((output[0].get(index + 1) == types.lexer_token.DOT)
                       && (output[0].get(index + 2) instanceof String)) {
                        current_string = (String) output[0].get(index + 2);
                        character_position = 0;
                        while(character_position < (current_string).length()) {
                            if(!Character.isDigit((current_string).charAt(character_position))) {
                                is_fractional = false;
                            }
                            character_position++;
                        }
                    } else is_fractional = false;
                } else is_fractional = false;

                if(is_number && !is_fractional) {
                    if(number_base != 10) {
                        output[2].add(string_to_bigdecimal(((String) output[0].get(index)).substring(2), number_base));
                    } else {
                        output[2].add(string_to_bigdecimal((String) output[0].get(index), number_base));
                    }
                    output[0].set(index, types.lexer_token.NUMBER);
                } else if(is_fractional) {
                    if(number_base != 10) {
                        output[2].add(string_to_bigdecimal(((String) output[0].get(index)).substring(2) + "." + output[0].get(index + 2), number_base));
                    } else {
                        output[2].add(string_to_bigdecimal(output[0].get(index) + "." + output[0].get(index + 2), number_base));
                    }
                    output[0].set(index, types.lexer_token.NUMBER);
                    output[0].set(index + 1, (Integer)0); // Integer marks it for deletetion
                    output[0].set(index + 2, (Integer)0);
                    index += 2;
                }
            }
            index++;
        }
        output[0].removeIf(obj -> obj instanceof Integer);
        return output;
    }
}
