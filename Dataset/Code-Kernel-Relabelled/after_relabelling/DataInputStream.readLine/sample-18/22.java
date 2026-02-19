public class func{
public void run(){
                DataInputStream input = new DataInputStream(connection.getInputStream());
                while (null != ((response = input.readLine()))) {
                    System.err.println(response);
                }
                input.close();
}
}
