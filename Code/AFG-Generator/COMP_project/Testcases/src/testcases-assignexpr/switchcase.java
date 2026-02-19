public class switchcase {
    public static void main(String[] args) {
        int dayOfWeek = 3;
        x = DatatInputStream.readLine(dayOfWeek);
        switch (x) {
            case 1:
                System.out.println(x);
                break;
            case 2:
                System.out.println(x+x);
                break;
            case 3:
                System.out.println(x*x);
                break;
          
            default:
                System.out.println(dayOfWeek + x);
        }
    }
}
