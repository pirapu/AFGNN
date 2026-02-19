public class func{
public void readAll(Reader reader){
                int charsRead = br.read(buffer);
                if (charsRead == -1) break;
                buf.append(buffer, 0, charsRead);
            return buf.toString();
}
}
