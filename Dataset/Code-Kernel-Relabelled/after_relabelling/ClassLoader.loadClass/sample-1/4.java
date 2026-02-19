public class func{
public void load(final ClassLoader loader,final Collection<String> classes){
            final Collection<Class<?>> loaded = new ArrayList<>(classes.size());
            for (final String n : classes) {
                try {
                    loaded.add(loader.loadClass(n));
                } catch (final ClassNotFoundException e) {
                }
            }
}
}
