public class func{
public void readFile(File f){
        while ((c = r.read()) != -1) {
            builder.append((char) c);
        }
        return builder.toString();
}
}
