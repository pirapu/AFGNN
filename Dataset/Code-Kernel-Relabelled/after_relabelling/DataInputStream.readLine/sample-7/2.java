public class func{
public void configure(JobConf jconf){
          File txt = new File(localFiles[i].toString());
          FileInputStream fin = new FileInputStream(txt);
          DataInputStream din = new DataInputStream(fin);
          String str = din.readLine();
          out.writeBytes("\n");
        out.close();
}
}
