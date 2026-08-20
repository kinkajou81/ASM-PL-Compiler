import java.util.ArrayList;

public class types {
    public enum lexerToken {
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
        TERNARY_PART1, PAREN_LEFT, PAREN_RIGHT, SQUARE_BRACKET_LEFT,
        SQUARE_BRACKET_RIGHT, CURLY_LEFT, CURLY_RIGHT,

        // pair and binary and unary operators
        COLON,

        // generic
        STRING, NUMBER, KEYWORD, TEXT, SPACE,
        
        // other
        EOF, ENDLINE, NEWLINE
    }

    public enum typeAttribute {
        POINTER, CONST, VOLATILE, RESTRICT, ATOMIC, REGISTER, UNSIGNED, EXPORT, THREAD_LOCAL, STATIC, EXTERN, CONSTINIT
    }

    public enum functionAttribute {
        EXPORT, INLINE, STATIC
    }

    public class type {
        public String name;
        public ArrayList<typeAttribute> attributes;

        public type() {
            name = ""; attributes = new ArrayList<>();
        }

        public type(String a) {
            name = a; attributes = new ArrayList<>();
        }

        public type add(typeAttribute a) {
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
        public ArrayList<functionAttribute> attributes;

        public function() {
            name = "";
            io_variables = new ArrayList<>();
            attributes = new ArrayList<>();
        }

        public function(String a) {
            name = a;
            io_variables = new ArrayList<>();
            attributes = new ArrayList<>();
        }

        public function(String a, ArrayList<variable> b) {
            name = a;
            io_variables = b;
            attributes = new ArrayList<>();
        }

        public function add(functionAttribute a) {
            attributes.add(a);
            return this;
        }
    }
}