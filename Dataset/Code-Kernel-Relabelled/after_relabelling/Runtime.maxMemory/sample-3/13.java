public class func{
public void initialize(Configuration config){
                long expirationTime = configuration.get(DB_CACHE_TIME);
                Preconditions.checkArgument(expirationTime>=0,"Invalid cache expiration time: %s",expirationTime);
                if (expirationTime==0) expirationTime=ETERNAL_CACHE_EXPIRATION;
                double cachesize = configuration.get(DB_CACHE_SIZE);
                Preconditions.checkArgument(cachesize>0.0,"Invalid cache size specified: %s",cachesize);
                if (cachesize<1.0) {
                    Runtime runtime = Runtime.getRuntime();
                    cacheSizeBytes = (long)((runtime.maxMemory()-(runtime.totalMemory()-runtime.freeMemory())) * cachesize);
                } else {
                    Preconditions.checkArgument(cachesize>1000,"Cache size is too small: %s",cachesize);
                    cacheSizeBytes = (long)cachesize;
                }
                log.info("Configuring total store cache size: {}",cacheSizeBytes);
                long cleanWaitTime = configuration.get(DB_CACHE_CLEAN_WAIT);
}
}
