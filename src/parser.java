import java.util.ArrayList;
import java.util.BitSet;

public class parser {
    public static BitSet findStrings(String s) {
        BitSet isString = new BitSet();

        boolean insideString = false;
        int stringType = -1;

        int characterPosition = 0;
        int currentDelimiter;
        while(characterPosition < s.length()) {
            currentDelimiter = 0;
            if(insideString) isString.set(characterPosition);

            while(currentDelimiter < com.stringDelimiters.length) {
                if(s.charAt(characterPosition) == com.stringDelimiters[currentDelimiter]) {
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
        while(characterPosition< s.length()) {
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

            while(currentDelimiter < com.commentDelimiterPairs.length) {
                if(characterPosition + com.commentDelimiterPairs[currentDelimiter][1].length() <= s.length()) {
                    if((s.substring(characterPosition, characterPosition+ com.commentDelimiterPairs[currentDelimiter][1].length())).equals(com.commentDelimiterPairs[currentDelimiter][1])
                        && currentDelimiter == commentType && insideComment) {
                            
                        commentType = -1;
                        if(com.commentDelimiterPairs[currentDelimiter][1].equals("\n")) {
                            isComment.clear(characterPosition);
                            insideComment = false;
                        } else {
                            exitedComment = com.commentDelimiterPairs[currentDelimiter][1].length();
                        }
                        break;
                    }
                }
                if(characterPosition+ com.commentDelimiterPairs[currentDelimiter][0].length() <= s.length()) {
                    if((s.substring(characterPosition, characterPosition+ com.commentDelimiterPairs[currentDelimiter][0].length())).equals(com.commentDelimiterPairs[currentDelimiter][0])
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
}
