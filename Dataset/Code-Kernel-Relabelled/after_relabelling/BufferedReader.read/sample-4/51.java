public class func{
public void InputStreamToString(InputStream in){
            while ((charOut = reader.read()) != -1) {
                myStrBuf.append(String.valueOf((char) charOut));
            }
}
}
