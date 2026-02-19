public class func{
public void convertStreamToString(InputStream is){
        int count = reader.read(buf);
        if (count<0) break;
        sb.append(buf, 0, count);
      is.close();
}
}
