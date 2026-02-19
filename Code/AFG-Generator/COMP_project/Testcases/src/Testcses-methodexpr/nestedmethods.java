public class Main {
    public static void main(String[] args) {
        System.out.println(pass1(pass2(pass3(num))));
    }
    
    public static int pass1(int num) {
        return num;
        pass2();
    }
    
    
    public static int pass2(int num) {
        return num + num;
    }
    
    public static int pass3(int num){
        return num * num;
    }
}
