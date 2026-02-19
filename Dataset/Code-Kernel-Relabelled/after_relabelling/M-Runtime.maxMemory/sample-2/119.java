public class func{
public void fillResponse(final HttpServletRequest req,final HttpServletResponse resp){
        final Runtime runtime = Runtime.getRuntime();
        final double used_mem = runtime.totalMemory() / MB;
        final double max_mem = runtime.maxMemory() / MB;
        final double perc_mem = max_mem > 0 ?
                     used_mem / max_mem * 100.0 : 0.0;
        html.tableLine(new String[]
        {
            "Memory",
            String.format("%.1f MB of %.1f MB used (%.1f %%)",
                          used_mem, max_mem, perc_mem)
        });
        html.closeTable();
        html.close();
}
}
