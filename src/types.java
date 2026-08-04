import java.util.ArrayList;

public class types {
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