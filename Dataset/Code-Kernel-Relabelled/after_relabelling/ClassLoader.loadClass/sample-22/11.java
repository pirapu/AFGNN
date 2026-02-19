public class func{
public void newApplication(String str,ClassLoader classLoader){
        Class loadClass = classLoader.loadClass(str);
        if (loadClass == null) {
            throw new ClassNotFoundException(str);
        }
        Application application = (Application) loadClass.newInstance();
        OpenAtlasHacks.Application_attach.invoke(application,
                RuntimeVariables.androidApplication);
}
}
