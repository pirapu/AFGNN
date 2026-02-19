public class func{
public void test1(){
    connection = (HttpURLConnection) urlToto.openConnection();
    contents = getContents(connection).toString();
    assertEquals("tata", contents.trim());
    connection = (HttpURLConnection) urlTiti.openConnection();
    contents = getContents(connection).toString();
    assertEquals("titi", contents.trim());
}
}
