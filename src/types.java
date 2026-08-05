import java.util.ArrayList;

public class types {
    public enum lexer_token {
        // unary operators
        DIRECTIVE, NOT, UNASSIGNMENT, LABEL,

        // binary operators
        ASSIGNMENT, WHILE, EQUAL_TO, LESS_THAN, GREATER_THAN,
        LESS_THAN_EQUAL_TO, GREATER_THAN_EQUAL_TO, NOT_EQUAL_TO,
        BITSHIFT_LEFT, BITSHIFT_RIGHT, BITROTATE_LEFT,
        BITROTATE_RIGHT, DIVIDE, DOT, STRUCTURE_DEREFERNCE,
        NAMESPACE_RESOLUTION, LIST_SEPARATOR, MODULO, ELVIS,

        // binary and unary operators
        AMPERSAND, ASTERISK, PLUS, MINUS, CARET, VERTICAL_LINE,

        // pair operators
        TERNARY_PART1, FUNCTION_DEF, FUNCTION_END, PAREN_LEFT, 
        PAREN_RIGHT, SQUARE_BRACKET_LEFT, SQUARE_BRACKET_RIGHT, 
        DOUBLE_QUOTE, QUOTE, CURLY_LEFT, CURLY_RIGHT,

        // pair and binary and unary operators
        COLON,

        // generic
        STRING, CHAR, DECIMAL_NUMBER, OCTAL_NUMBER, BINARY_NUMBER, 
        SENARY_NUMBER, KEYWORD, TEXT,
        
        // other
        EOF
    }

    public enum type_attribute {
        NULL
    }

    public enum function_attribute {
        NULL
    }

    public class type {
        public String name;
        public ArrayList<type_attribute> attributes;

        public type() {
            name = ""; attributes = new ArrayList<>();
        }

        public type(String a) {
            name = a; attributes = new ArrayList<>();
        }

        public type add(type_attribute a) {
            attributes.add(a);
            return this;
        }
    }

    public class variable {
        public String name;
        public type datatype;

        public variable() {
            name = ""; datatype = new type();
        }

        public variable(String a, type b) {
            name = a; datatype = b;
        }
    }

    public class function {
        public String name;
        public ArrayList<variable> io_variables;
        public ArrayList<Boolean> is_input;
        public ArrayList<function_attribute> attributes;

        public function() {
            name = "";
            io_variables = new ArrayList<>();
            is_input = new ArrayList<>();
            attributes = new ArrayList<>();
        }

        public function(String a) {
            name = a;
            io_variables = new ArrayList<>();
            is_input = new ArrayList<>();
            attributes = new ArrayList<>();
        }

        public function(String a, ArrayList<variable> b, ArrayList<Boolean> c) {
            name = a;
            io_variables = b;
            is_input = c;
            attributes = new ArrayList<>();
        }

        public function add(function_attribute a) {
            attributes.add(a);
            return this;
        }
    }
}