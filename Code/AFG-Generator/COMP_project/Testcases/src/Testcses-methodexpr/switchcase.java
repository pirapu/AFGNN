public class main {
    public void switchcase(String[] args){
        int x = 0;
        int choice = 0;
        switch(x){
            case 1:
                choice = x + x + x;
                break;
            case 2:
                choice = x * x * x;
                break;
            default:
                choice = x - x - x;
                break;           
        }
        System.out.println(choice * x);
 
    }
    
}
