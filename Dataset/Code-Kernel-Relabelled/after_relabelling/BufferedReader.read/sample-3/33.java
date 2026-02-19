public class func{
public void readFile(IFile file){
          while((numRead=reader.read(buf)) != -1){
            sb.append(buf, 0, numRead);
          }
}
}
