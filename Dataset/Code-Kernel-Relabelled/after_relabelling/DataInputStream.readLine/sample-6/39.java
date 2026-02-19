public class func{
public void ping(String hostname){
      while ((line = stdout.readLine()) != null) {
        pingResult = line;
        tmr.cancel();
        process.destroy();
        return pingResult;
      }
      process.waitFor();
}
}
