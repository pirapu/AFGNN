public class func{
public void try2Load(String newName,ClassLoader loader){
            if (loader == null)
                return (Class<T>) getClass().getClassLoader().loadClass(newName);
            return (Class<T>) loader.loadClass(newName);
}
}
