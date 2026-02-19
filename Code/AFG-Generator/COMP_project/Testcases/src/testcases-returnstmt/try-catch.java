public class main{
    public int trycatch(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return str; 
        }
    }
    
    
}
