public class nestedconditional {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamreader(System.in));
        String str = br.readLine();
        int a = Integer.parseInt(str);
        if(a>=5){
            System.out.println((a + a) * 2);
        }
        else{
            System.out.println((a - a) / 3);
        }
        System.out.println(a);

    }
    
}
