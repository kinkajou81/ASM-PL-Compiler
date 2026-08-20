import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class lexer {
    public static String matchString(String s, int position) {
        if(position >= s.length() || position < 0) {
            System.err.printf("ERROR: Invalid position found in matchString(), returning NONE");
            return "NONE";
        }

        ArrayList<String> candidates = new ArrayList<>();
        ArrayList<Integer> candidateLengths = new ArrayList<>();

        for(String token : com.lexerTokens) {
            if(s.regionMatches(position, token, 0, token.length())) {
                candidates.add(token);
                candidateLengths.add(token.length());
            }
        }

        if(candidates.isEmpty()) return "NONE";

        int largestCandidateIndex = -1;
        int LargestCandidateLength = -1;
        int index = 0;
        for(Integer candidate_length : candidateLengths) {
            if(candidate_length > LargestCandidateLength) {
                LargestCandidateLength = candidate_length;
                largestCandidateIndex = index;
            }
            index++;
        }

        return candidates.get(largestCandidateIndex);
    }

    public static void lexSymbols(String s) {
        int characterPosition = 0;
        String currentToken;

        while(characterPosition < s.length()) {
            currentToken = matchString(s, characterPosition);
            Object outputs_last_element;

            if(!com.lexedCode[0].isEmpty()) {
                outputs_last_element = com.lexedCode[0].get(com.lexedCode[0].size() - 1);
            } else {
                outputs_last_element = null;
            }

            if(com.isString.get(characterPosition)) {
                if(outputs_last_element == types.lexerToken.STRING) {
                    if(!com.lexedCode[1].isEmpty()) com.lexedCode[1].set(com.lexedCode[1].size() - 1, com.lexedCode[1].get(com.lexedCode[1].size() - 1) + s.substring(characterPosition, characterPosition + 1));
                    else com.lexedCode[1].add(s.substring(characterPosition, characterPosition + 1));
                } else {
                    com.lexedCode[0].add(types.lexerToken.STRING);
                    com.lexedCode[1].add(s.substring(characterPosition, characterPosition + 1));
                }
                characterPosition++;
            } else if(currentToken.equals("NONE")) {
                if(outputs_last_element instanceof String) {
                    com.lexedCode[0].set(com.lexedCode[0].size() - 1, outputs_last_element + s.substring(characterPosition, characterPosition + 1));
                } else {
                    com.lexedCode[0].add(s.substring(characterPosition, characterPosition + 1));
                }
                characterPosition++;
            } else {
                com.lexedCode[0].add(com.lexerTokenMap.get(currentToken));
                characterPosition += currentToken.length();
            }
        }
    }

    public static int countFractionalDigits(String s) {
        int decimalPointIndex = s.indexOf('.');
        if (decimalPointIndex == -1) {
            return 0;
        }
        return s.length() - decimalPointIndex - 1;
    }

    public static String removeDecimalPoint(String s) {
        int decimalPointIndex = s.indexOf('.');
        if (decimalPointIndex == -1) {
            return s;
        }
        return s.substring(0, decimalPointIndex) + s.substring(decimalPointIndex + 1);
    }

    public static BigDecimal stringToBigDecimal(String s, int base) {
        if(base == 10) {
            return new BigDecimal(s);
        }
        return (new BigDecimal(new BigInteger(removeDecimalPoint(s), base)))
               .divide(new BigDecimal((BigInteger.valueOf(base)).pow(countFractionalDigits(s))), com.mc);
    }

    public static void lexNumbers() {
        boolean isNumber;
        int numberBase;
        int offset;
        int characterPosition;
        boolean isFractional;

        int index = 0;
        Object o;
        while(index < com.lexedCode[0].size()) {
            o = com.lexedCode[0].get(index);

            if(o instanceof String) {
                isNumber = true;
                isFractional = true;
                String current_string = (String) o;

                if((current_string).startsWith("0b")) numberBase = 2;
                else if((current_string).startsWith("0o")) numberBase = 8;
                else if((current_string).startsWith("0x")) numberBase = 16;
                else numberBase = 10;

                offset = (numberBase == 10)? 0: 2;
                characterPosition = offset;
                while(characterPosition < (current_string).length()) {
                    if(!Character.isDigit((current_string).charAt(characterPosition))) {
                        isNumber = false;
                    }
                    characterPosition++;
                }

                if(index + 2 < com.lexedCode[0].size() && isNumber) {
                    if((com.lexedCode[0].get(index + 1) == types.lexerToken.DOT)
                       && (com.lexedCode[0].get(index + 2) instanceof String)) {
                        current_string = (String) com.lexedCode[0].get(index + 2);
                        characterPosition = 0;
                        while(characterPosition < (current_string).length()) {
                            if(!Character.isDigit((current_string).charAt(characterPosition))) {
                                isFractional = false;
                            }
                            characterPosition++;
                        }
                    } else isFractional = false;
                } else isFractional = false;

                if(isNumber && !isFractional) {
                    if(numberBase != 10) {
                        com.lexedCode[2].add(stringToBigDecimal(((String) com.lexedCode[0].get(index)).substring(2), numberBase));
                    } else {
                        com.lexedCode[2].add(stringToBigDecimal((String) com.lexedCode[0].get(index), numberBase));
                    }
                    com.lexedCode[0].set(index, types.lexerToken.NUMBER);
                } else if(isFractional) {
                    if(numberBase != 10) {
                        com.lexedCode[2].add(stringToBigDecimal(((String) com.lexedCode[0].get(index)).substring(2) + "." + com.lexedCode[0].get(index + 2), numberBase));
                    } else {
                        com.lexedCode[2].add(stringToBigDecimal(com.lexedCode[0].get(index) + "." + com.lexedCode[0].get(index + 2), numberBase));
                    }
                    com.lexedCode[0].set(index, types.lexerToken.NUMBER);
                    com.lexedCode[0].set(index + 1, (Integer)0); // Integer marks it for deletetion
                    com.lexedCode[0].set(index + 2, (Integer)0);
                    index += 2;
                }
            }
            index++;
        }
        com.lexedCode[0].removeIf(obj -> obj instanceof Integer);
    }

    public static void lexKeywords() {
        int index = 0;
        Object o;
        while(index < com.lexedCode[0].size()) {
            o = com.lexedCode[0].get(index);
            if(o instanceof String) {
                if(com.keywordSet.contains(o)) {
                    com.lexedCode[3].add(o);
                    com.lexedCode[0].set(index, types.lexerToken.KEYWORD);
                }
            }
            index++;
        }
    }

    public static void lexText() {
        int index = 0;
        Object o;
        while(index < com.lexedCode[0].size()) {
            o = com.lexedCode[0].get(index);
            if(o instanceof String) {
                com.lexedCode[4].add(o);
                com.lexedCode[0].set(index, types.lexerToken.TEXT);
            }
            index++;
        }
    }
}
