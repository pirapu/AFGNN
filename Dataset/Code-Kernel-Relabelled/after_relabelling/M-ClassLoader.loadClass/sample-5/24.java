public class func{
public void loadClass(String className,ClassLoader loader){
      RmiConnectorActivator.log(LogService.LOG_DEBUG,"Loading class: " + className +" From "+loader,null);
      if (loader == null){
         loader= ProviderHelper.class.getClassLoader();
         RmiConnectorActivator.log(LogService.LOG_DEBUG,"a new loader "+loader,null);
      }
      return loader.loadClass(className);
}
}
