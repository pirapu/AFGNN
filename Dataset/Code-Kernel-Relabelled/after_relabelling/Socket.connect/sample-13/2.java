public class func{
public void connectionIsAvailable(String host,int port,int timeout){
        if (endPoint.isUnresolved()) {
            System.out.println("Failure " + endPoint);
        } else {
            try {
                socket.connect(endPoint, timeout);
                logger.info("Connection Success:    " + endPoint);
                return true;
            } catch (Exception e) {
                logger.info("Connection Failure:    " + endPoint + " message: "
                        + e.getClass().getSimpleName() + " - " + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    }
                }
            }
        }
}
}
