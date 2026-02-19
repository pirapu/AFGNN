public class func{
public void loadResponse(JFrame top,ResponsePanel rp,BufferedReader br){
            String file = br.readLine();
            if (file == null) {
                System.exit(0);
            }
            loadResponse(top, rp, file);
}
}
