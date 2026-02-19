public class func{
public void compressAsynchronously(String nameOfFile2Compress,String nameOfCompressedFile,String innerEntryName){
    Future<?> future = executor.submit(new CompressionRunnable(compressor,
        nameOfFile2Compress, nameOfCompressedFile, innerEntryName));
    executor.shutdown();
}
}
