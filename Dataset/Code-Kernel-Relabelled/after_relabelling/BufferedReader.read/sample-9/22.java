public class func{
public void readStream(final InputStream stream){
      while ((read = reader.read(buffer)) != -1)
        output.append(buffer, 0, read);
      return output.toString();
}
}
