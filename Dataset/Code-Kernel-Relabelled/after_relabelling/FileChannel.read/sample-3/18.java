public class func{
public void testFilePath(){
            long pos = ch.size();
            p("size=" + pos);
            ch.write(buff);
            buff.clear();
            ch.read(buff, 0);
            buff.clear();
            ch.read(buff, 0);
            if (ch != null)
                ch.close();
}
}
