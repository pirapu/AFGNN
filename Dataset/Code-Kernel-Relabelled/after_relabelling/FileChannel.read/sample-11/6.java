public class func{
public void readString(String filePath){
        File file = new File(filePath);
        if (!file.exists())
            return null;
            fileInput = new FileInputStream(filePath);
            channel = fileInput.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            channel.read(buffer);
}
}
