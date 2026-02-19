public class func{
public void run(){
        DataInputStream in = new DataInputStream(p.getInputStream());
        while ((line = in.readLine()) != null)
            pw.println(line);
            in.close();
}
}
