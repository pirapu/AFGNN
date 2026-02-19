public class func{
public void copyLogicalLog(String storeDir){
        do
        {
            read = source.read( buffer );
            buffer.flip();
            dest.write( buffer );
            buffer.clear();
        }
        while ( read == 1024 );
}
}
