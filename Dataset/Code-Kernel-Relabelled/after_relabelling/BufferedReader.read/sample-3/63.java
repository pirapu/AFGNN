public class func{
public void fileToString(FileSystem fs,Path path){
    while((read = br.read(buff)) != -1) {
      sb.append(buff, 0, read);
    }
    br.close();
}
}
