public class loop{
    public static void main(String[] args) {
         rows = 5;
        for (int i = 1; i <= rows; i++) {
             k = 0;
            while (k != 2 * i - 1) {
                System.out.print(k);
                k++;
            }
            System.out.println(k+rows);
        }
    }
}


