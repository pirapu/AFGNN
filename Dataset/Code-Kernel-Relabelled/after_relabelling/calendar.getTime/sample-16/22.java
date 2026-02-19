public class func{
public void put(final URL url,final Bitmap bitmap){
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, +1);
                mHardCache.put(url, new ExpiringBitmap(bitmap, cal.getTime()));
}
}
