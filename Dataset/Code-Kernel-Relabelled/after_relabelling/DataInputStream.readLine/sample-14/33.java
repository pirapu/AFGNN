public class func{
public void readNotify(){
                    DataInputStream din = new DataInputStream(in);
                    while ((line = din.readLine()) != null)
                        message += line + lineSeparator;
}
}
