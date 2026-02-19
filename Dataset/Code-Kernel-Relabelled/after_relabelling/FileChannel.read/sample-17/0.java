public class func{
public void testReadFrames(){
        List<MkvBlock> bs = MKVMuxerTest.getBlocksByTrackNumber(me[0], 1);
        Assert.assertNotNull(bs);
        Assert.assertEquals(1, bs.size());
        MkvBlock videoBlock = bs.get(0);
        ByteBuffer source = ByteBuffer.allocate((int) videoBlock.size());
        channel.position(videoBlock.dataOffset);
        channel.read(source);
        source.flip();
        ByteBuffer[] frames = videoBlock.getFrames(source);
        byte[] frameBytes = MKVMuxerTest.bufferToArray(byteBuffer);
        Assert.assertNotNull(frames);
        Assert.assertEquals(1, frames.length);
        Assert.assertArrayEquals(rawFrame, frameBytes);
}
}
