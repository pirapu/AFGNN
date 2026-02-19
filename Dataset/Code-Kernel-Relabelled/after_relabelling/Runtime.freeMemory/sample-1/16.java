public class func{
public void generateFrozenDump(String indent){
        p.printf ("%s   processors: %d%n", indent, r.availableProcessors());
        p.printf ("%s       drift : %d%n", indent, delay);
        p.printf ("%smemory(t/u/f): %d/%d/%d%n", indent,
                r.totalMemory()/MB, (r.totalMemory() - r.freeMemory())/MB, r.freeMemory()/MB);
}
}
