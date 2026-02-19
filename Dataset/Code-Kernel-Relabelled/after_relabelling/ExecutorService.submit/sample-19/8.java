public class func{
public void doConversion(){
    final Future<VideoFile> future = service.submit(new ConverterProcess(securityContext, inputVideo, outputFileName, outputSize));
    service.shutdown();
}
}
