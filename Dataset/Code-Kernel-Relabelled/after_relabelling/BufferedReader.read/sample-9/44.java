public class func{
public void readUrl(String urlString){
        while ((read = reader.read(chars)) != -1)
            buffer.append(chars, 0, read); 
        return buffer.toString();
}
}
