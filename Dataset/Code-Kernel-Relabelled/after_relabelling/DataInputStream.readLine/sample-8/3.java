public class func{
public void configure(JobConf jconf){
        for (int i = 0; i < localArchives.length; i++) {
          File f = new File(localArchives[i].toString());
          File txt = new File(f, "test.txt");
          FileInputStream fin = new FileInputStream(txt);
          DataInputStream din = new DataInputStream(fin);
          String str = din.readLine();
          din.close();
          out.writeBytes(str);
          out.writeBytes("\n");
        }
        for (int i = 0; i < localFiles.length; i++) {
          File txt = new File(localFiles[i].toString());
          FileInputStream fin = new FileInputStream(txt);
          DataInputStream din = new DataInputStream(fin);
          String str = din.readLine();
          out.writeBytes(str);
          out.writeBytes("\n");
        }
}
}
