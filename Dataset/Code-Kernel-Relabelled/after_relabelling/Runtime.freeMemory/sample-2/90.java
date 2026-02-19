public class func{
public void _mark(String s){
    Runtime r = Runtime.getRuntime();
    long fmem = r.freeMemory();
    long tmem = r.totalMemory();
    out.printf("[%,18dns d %,18dns %,18d b mem]\t%s\n", cumulative, delta,
        umem, s);
    values.add("" + umem);
    last = System.nanoTime();
}
}
