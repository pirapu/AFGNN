public class func{
public void loadStrategyFromClassName(String strategyClassName){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            try {
                Class<?> clazz = classLoader.loadClass(strategyClassName);
                return (CellMappingStrategy) clazz.newInstance();
            } catch (Throwable e) {
                LOG.warn("Failed to load HBase cell mapping strategy from class {}.", strategyClassName);
            }
        }
}
}
