public class line_parser {
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
