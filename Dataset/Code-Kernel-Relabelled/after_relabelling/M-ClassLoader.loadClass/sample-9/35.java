public class func{
public void createProviderInstance(final String providerClass,final ClassLoader classLoader){
        if (providerClass != null && providerClass.length() > 0 && !providerClass.equals(ProviderWrapper.class.getName())) {
            try {
                final Class<? extends Provider> clazz = classLoader.loadClass(providerClass).asSubclass(Provider.class);
                return clazz.newInstance();
            } catch (final Throwable e) {
                logger.warning("Unable to construct provider implementation " + providerClass, e);
            }
        }
}
}
