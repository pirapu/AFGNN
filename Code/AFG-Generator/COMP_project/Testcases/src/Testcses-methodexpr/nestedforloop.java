public class nestedforloop {
    public static void main(String[] args){
        int sum = 0;
        for(int i = 1; i <= 3; i++){
            for(int j = 1; j <=4; j++){
                sum += (i*j)+2;
            }
        }
        System.out.println(sum/sum);
    }
    
}
