public class func{
public void createExclusion(ClassLoader cl,File root){
        if (exclusion != null) {
            Class<MethodExclusion> clazz = (Class<MethodExclusion>) cl.loadClass(exclusion);
            return clazz.getConstructor(File.class).newInstance(root);
        } else {
            return FileMethodExclusion.create(root);
        }
}
}
