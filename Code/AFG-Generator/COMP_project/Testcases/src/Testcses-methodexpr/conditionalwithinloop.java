public class main{
    public void conditionalwithinloop(int n){
        int x = 0;
        for(int i = 1; i <= n; i++){
            x += (i % 2 == 0)?(i * 2):(i * 3);
        }
        System.out.println(x * i);
    }

}