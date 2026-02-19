public class func{
public void buildTree(RandomAccessFile raf,boolean closeExit){
                    moovBuffer = ByteBuffer.allocate(boxHeader.getDataLength());
                    int bytesRead = fc.read(moovBuffer);
                    if(bytesRead < boxHeader.getDataLength())
                    {
                        String msg = ErrorMessage.ATOM_LENGTH_LARGER_THAN_DATA.getMsg(boxHeader.getId(), boxHeader.getDataLength(),bytesRead);
                        throw new CannotReadException(msg);
                    }
                    moovBuffer.rewind();
                    buildChildrenOfNode(moovBuffer, newAtom);
}
}
