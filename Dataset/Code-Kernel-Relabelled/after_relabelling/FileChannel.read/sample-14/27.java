public class func{
public void writeRequest(final OutputStream out){
            while ((readResult = channel.read(tmp)) >= 0) {
                out.write(tmp.array(), 0, readResult);
                tmp.clear();
                transferred += readResult;
                synchronized (mDataTransferListeners) {
                    it = mDataTransferListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onTransferProgress(readResult, transferred, size, mFile.getAbsolutePath());
                    }
                }
            }
}
}
