public class func{
public void readLine(BufferedReader r){
        while (!eol && (c = r.read()) >= 0) {
            sb.append((char) c);
            eol = (c == '\r' || c == '\n');
            if (c == '\r') {
                r.mark(1);
                c = r.read();
                if (c != '\n') {
                    r.reset();
                } else {
                    sWindowsLineBreaks = true;
                    sb.append((char) c);
                }
            }
        }
        return sb.length() == 0 ? null : sb.toString();
}
}
