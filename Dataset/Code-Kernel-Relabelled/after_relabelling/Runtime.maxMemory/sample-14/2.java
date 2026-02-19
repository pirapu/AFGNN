public class func{
public void calculateCacheSize(int percentageOfMemoryForCache){
        Runtime runtime = Runtime.getRuntime();
        int calculatedSize = (int) (runtime.maxMemory() * percentageOfMemoryForCache / 100);
        int cacheSize = Math.min(calculatedSize, MAX_CACHE_SIZE);
}
}
