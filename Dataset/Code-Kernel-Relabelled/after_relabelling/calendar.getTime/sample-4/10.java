public class func{
public void assertEquals(Date expected,Calendar actual){
        if (expected == null || actual == null) {
            assertEquals((Object) expected, (Object) actual);
        } else {
            assertEquals(expected, actual.getTime());
        }
}
}
