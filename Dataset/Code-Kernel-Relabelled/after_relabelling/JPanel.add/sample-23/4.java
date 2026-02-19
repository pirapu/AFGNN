public class func{
public void testClear(){
        new JPanel().add(obj);
        assertEquals(0, obj.getCount());
        obj.add("Test");
        assertEquals(1, obj.getCount());
        obj.clear();
        assertEquals(0, obj.getCount());
}
}
