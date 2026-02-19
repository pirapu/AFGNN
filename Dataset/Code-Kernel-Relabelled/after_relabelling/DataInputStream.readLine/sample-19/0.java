public class func{
public void transferFileToPublicServer(String kmlFileName,File file){
      inStream = new DataInputStream(conn.getInputStream());
      while ((str = inStream.readLine()) != null) {
        if (str.equalsIgnoreCase("done")) {
          logger.info("Transfer complete.");
          inStream.close();
          return true;
        } else
          return false;
      }
      inStream.close();
}
}
