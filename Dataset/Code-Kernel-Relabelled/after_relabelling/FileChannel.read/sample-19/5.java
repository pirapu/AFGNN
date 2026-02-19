public class func{
public void dump(FileChannel fc){
        int sz = (int)fc.size();
        ByteBuffer bb = ByteBuffer.allocate(sz);
        fc.position(0);
        if (fc.read(bb) != sz)
            throw new IOException("Incomplete read");
        bb.flip();
        while (bb.hasRemaining() && (n < 32)) {
            byte b = bb.get();
            if (b == prev) {
                r++;
                continue;
            }
            if (r > 0) {
                int c = prev & 0xff;
                if (c < 0x10)
                    out.print('0');
                out.print(Integer.toHexString(c));
                if (r > 1) {
                    out.print("[");
                    out.print(r);
                    out.print("]");
                }
                n++;
            }
            prev = b;
            r = 1;
        }
}
}
