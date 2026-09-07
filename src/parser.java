import java.util.ArrayList;
import java.util.BitSet;

public class Parser {
    public static BitSet findStrings(String s) {
        BitSet isString = new BitSet();

        boolean insideString = false;
        int stringType = -1;

        int characterPosition = 0;
        int currentDelimiter;
        while(characterPosition < s.length()) {
            currentDelimiter = 0;
            if(insideString) isString.set(characterPosition);

            while(currentDelimiter < Com.stringDelimiters.length) {
                if(s.charAt(characterPosition) == Com.stringDelimiters[currentDelimiter]) {
                    if(currentDelimiter == stringType && insideString) {
                        if(characterPosition > 0) if(s.charAt(characterPosition - 1) != '\\') {
                            insideString = false;
                            stringType = -1;
                        } // no (i==0) case since a string cannot be opened and closed in one character
                    } else if(!insideString) {
                        insideString = true;
                        stringType = currentDelimiter;
                        isString.set(characterPosition);
                    }
                    break;
                }
                currentDelimiter++;
            }
            characterPosition++;
        }
        return isString;
    }

    public static String removeComments(String s, BitSet isString) {
        BitSet isComment = new BitSet();

        boolean insideComment = false;
        int exitedComment = 0;
        int commentType = -1;

        int characterPosition = 0;
        int currentDelimiter;
        while(characterPosition < s.length()) {
            currentDelimiter = 0;
            if(insideComment) isComment.set(characterPosition);
            if(isString.get(characterPosition)) {characterPosition++; continue;}
            if(exitedComment > 0) {
                if(exitedComment == 1) {
                    insideComment = false;
                    isComment.clear(characterPosition);
                }
                exitedComment--;
            }

            while(currentDelimiter < Com.commentDelimiterPairs.length) {
                if(characterPosition + Com.commentDelimiterPairs[currentDelimiter][1].length() <= s.length()) {
                    if((s.substring(characterPosition, characterPosition+ Com.commentDelimiterPairs[currentDelimiter][1].length())).equals(Com.commentDelimiterPairs[currentDelimiter][1])
                        && currentDelimiter == commentType && insideComment) {
                            
                        commentType = -1;
                        if(Com.commentDelimiterPairs[currentDelimiter][1].equals("\n")) {
                            isComment.clear(characterPosition);
                            insideComment = false;
                        } else {
                            exitedComment = Com.commentDelimiterPairs[currentDelimiter][1].length();
                        }
                        break;
                    }
                }
                if(characterPosition+ Com.commentDelimiterPairs[currentDelimiter][0].length() <= s.length()) {
                    if((s.substring(characterPosition, characterPosition+ Com.commentDelimiterPairs[currentDelimiter][0].length())).equals(Com.commentDelimiterPairs[currentDelimiter][0])
                        && !insideComment) {

                        insideComment = true;
                        commentType = currentDelimiter;
                        isComment.set(characterPosition);
                        break;
                    }
                }
                currentDelimiter++;
            }
            characterPosition++;
        }
        characterPosition= 0;
        StringBuilder output_builder = new StringBuilder(s.length()); // size set to max size
        while(characterPosition< s.length()) {
            if(!isComment.get(characterPosition)) output_builder.append(s.charAt(characterPosition));
            characterPosition++;
        }

        return output_builder.toString();
    }

    public static void calculate_pair_delimiter_depths() {
        ArrayList<Object> active_delimiters = new ArrayList<>();
        int[] current_depths = new int[Com.pairDelimiterData.length];
        boolean remove_last_active_delimiter = false;

        int token_index = 0;
        int keyword_index = 0;
        while(token_index < Com.lexedCode[0].size()) {
            if(remove_last_active_delimiter) {
                current_depths[Com.pairDelimiterIndicies.get(active_delimiters.getLast())]--;
                active_delimiters.removeLast();
                remove_last_active_delimiter = false;
            }

            if(Com.pairDelimiterSetClosers.contains(Com.lexedCode[0].get(token_index))) {
                if(Com.pairDelimiterMapClosers.get(active_delimiters.getLast()).equals(Com.lexedCode[0].get(token_index))) {
                    remove_last_active_delimiter = true;
                }

            } else if(Com.pairDelimiterSetOpeners.contains(Com.lexedCode[0].get(token_index))) {
                
            } else if(Com.lexedCode[0].get(token_index) == Types.lexerToken.KEYWORD) {
                if(Com.pairDelimiterSetClosers.contains(Com.lexedCode[3].get(keyword_index))) {

                } else if(Com.pairDelimiterSetOpeners.contains(Com.lexedCode[3].get(keyword_index))) {

                }
                keyword_index++;
            }
            token_index++;
        }
    }
}
