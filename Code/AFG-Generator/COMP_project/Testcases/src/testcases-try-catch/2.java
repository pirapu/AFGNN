public class dummy {
public static String readFirstLine(String filePath) {
    String firstLine = null;
    try {
        BufferedReader br;
        if(filePath != null){
            br = new BufferedReader(new FileReader(filePath));
        }
        firstLine = br.readLine();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return firstLine;
}
}