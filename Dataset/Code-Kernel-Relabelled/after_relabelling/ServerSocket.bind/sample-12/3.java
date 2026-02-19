public class func{
public void startFileTransferChannel(String dirName,FileChannel[] fileChannelList){
      ServerSocketChannel ssc = ServerSocketChannel.open();
      ssc.configureBlocking(false);
      ServerSocket serverSocket = ssc.socket();
      serverSocket.bind(null);
      port = serverSocket.getLocalPort();
}
}
