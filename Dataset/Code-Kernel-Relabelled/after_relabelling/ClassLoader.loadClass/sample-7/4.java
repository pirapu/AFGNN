public class func{
public void verify_typedefstring_idl(ClassLoader cl){
        Class<?> clazz = cl.loadClass("test.MyStruct");
        Object obj = clazz.newInstance();
        Field f2 = clazz.getDeclaredField ("id");
        assertTrue ("".equals(f1.get(obj)));
        assertTrue ("".equals(f2.get(obj)));
}
}
