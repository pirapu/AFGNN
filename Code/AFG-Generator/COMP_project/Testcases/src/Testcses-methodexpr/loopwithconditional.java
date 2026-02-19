public class main {
    public void loopwithconditional(int a, int y){
        int result = 0;
        if(x > y){
            for(int i = 1; i <= x; i++){
                result += (i * i) + 3;
            }    
        }
        else{
            for(int i = 1; i <= y; i++){
                result += (i * i) - 1;
            }
        }
        System.out.println(result + result);
    }
    
}
