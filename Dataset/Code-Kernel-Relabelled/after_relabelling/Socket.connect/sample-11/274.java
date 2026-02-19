public class func{
public void createAsync(SocketDestination dest,KeyedResourcePool<SocketDestination,SocketAndStreams> pool){
        socket.connect(new InetSocketAddress(dest.getHost(), dest.getPort()), soTimeoutMs);
        recordSocketCreation(dest, socket);
        SocketAndStreams sands = new SocketAndStreams(socket, dest.getRequestFormatType());
        negotiateProtocol(sands, dest.getRequestFormatType());
        pool.checkin(dest, sands);
}
}
