public class func{
public void ImageLoaderTestCase(){
        FopFactoryBuilder builder = new FopFactoryBuilder(new File(".").toURI());
        builder.setSourceResolution(72);
        builder.setTargetResolution(300);
        fopFactory = builder.build();
}
}
