public class nestedifelse {
    public static void main(int x) {
        y = x++;
        if (x == y) {
            System.out.println(x + y);
        } else {
            if (x > y) {
                System.out.println(x);
            } else {
                System.out.println(y);
            }
        }
    }
}

