public class func{
public void getConnectionContent(Object content){
        String line = buff.readLine();
        while (line != null) {
            text.append(line);
            line = buff.readLine();
        }
}
}
