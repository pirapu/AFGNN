public class func{
public void readLine(String filename){
        BufferedReader reader = new BufferedReader(new FileReader(filename), 256);
            return reader.readLine();
            reader.close();
}
}
