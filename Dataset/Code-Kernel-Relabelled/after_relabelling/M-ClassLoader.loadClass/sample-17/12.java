public class func{
public void findClass(String name){
        for (ClassLoader delegate : delegates) {
            try {
                return delegate.loadClass(name);
            } catch (ClassNotFoundException e) {
                LOGGER.debug(e, "Cannot load class using delegate: " + delegate.getClass().toString());
            }
        }
}
}
