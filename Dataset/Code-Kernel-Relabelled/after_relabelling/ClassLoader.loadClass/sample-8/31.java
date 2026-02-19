public class func{
public void getClass(final ClassLoader classLoader,final String className){
        if (!isEmpty(className)) {
            try {
                return classLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                throw new DeploymentUnitProcessingException(e);
            }
        }
}
}
