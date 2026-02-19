public class func{
public void assertStored(ClassLoader loader,Object expected){
            Class<?> store = loader.loadClass("com.example.Store");
            Field field = store.getDeclaredField("result");
            Object result = field.get(null);
}
}
