public class func{
public void readV2Tag(File file,int loadOptions,int startByte){
                fis = new FileInputStream(file);
                fc = fis.getChannel();
                bb = fc.map(FileChannel.MapMode.READ_ONLY,0,startByte);
                bb =  ByteBuffer.allocate(startByte);
                fc.read(bb,0);
                if (fc != null)
                {
                    fc.close();
                }
                if (fis != null)
                {
                    fis.close();
                }
}
}
