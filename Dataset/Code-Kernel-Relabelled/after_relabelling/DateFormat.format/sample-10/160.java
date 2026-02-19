public class func{
public void testDateSerializationWithPattern(){
    Date now = new Date();
    String json = gson.toJson(now);
    assertEquals("\"" + formatter.format(now) + "\"", json);
}
}
