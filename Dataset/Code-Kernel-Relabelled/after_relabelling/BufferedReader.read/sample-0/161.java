public class func{
public void test_readLine_interaction_with_array_read_1(){
        BufferedReader r = new BufferedReader(new StringReader("1\r\n2"));
        assertEquals(2, r.read(new char[2], 0, 2));
        assertEquals("", r.readLine());
        assertEquals("2", r.readLine());
        assertNull(r.readLine());
}
}
