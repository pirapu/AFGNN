public class func{
public void gzips(String zipFileName,String mDestFile){
        while ((c = in.read()) != -1) {
            out.write(String.valueOf((char) c).getBytes("UTF-8"));
        }
        in.close();
}
}
