public class forandif {
    public static void main(String[] args){
        int x = 0;
        for(int i = 0; i <= 10; i++){
            if(i % 2 == 0){
                x += i*i;
            }
            else{
                x -= i+i;
            }
        }
        System.out.println(x);
    }
    
}
