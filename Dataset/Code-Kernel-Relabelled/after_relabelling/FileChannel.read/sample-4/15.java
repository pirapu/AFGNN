public class func{
public void fillBuffer(ByteBuffer buffer,File file,int rearOffset){
            fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            if(fc.size() >= buffer.capacity() + rearOffset) {
                fc.position(fc.size() - buffer.capacity() - rearOffset);
            } else {
                fc.position(0);
            }
            while(totalRead < buffer.capacity() && totalRead < fc.size()) {
                read = fc.read(buffer);
                totalRead += read;
                if(read == 0 || read == -1)
                    break;
            }
            buffer.limit(totalRead);
            IOUtils.close(fis);
}
}
