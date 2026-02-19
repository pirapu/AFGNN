public class func{
public void start(DataInputStream in,OutputStream out,String[] parameters){
          in = new DataInputStream(rebuildInputStream());
        String line = in.readLine();
        stage = parseLine(line, stages);
        if (stage != null)
          break;
}
}
