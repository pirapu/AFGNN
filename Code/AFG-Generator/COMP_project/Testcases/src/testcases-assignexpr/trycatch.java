public class trycatch {
    public static void main(String[] args) {
        int result;

        try {
            
            numerator = 10;
            denominator = 0;
            result = numerator / denominator; 
        } catch (ArithmeticException e) {
            
            result = (e.getMessage().equals("/ by zero")) ? 0 : -1;
            System.out.println("Error: " + e.getMessage());
        }

        
        System.out.println(result);
    }
}

