public class func{
public void renderSettings(EntryManager entryManager,StringBuilder sb){
        final Runtime rt = Runtime.getRuntime();
        sb.append(String.format("-- Memory free: %4.2fMB total: %4.2fMB max: %4.2fMB\n",
                rt.freeMemory() / 1024 / 1024.0, rt.totalMemory() / 1024 / 1024.0, rt.maxMemory() / 1024 / 1024.0));
        sb.append("-- Model: " + Build.MODEL + "\n");
}
}
