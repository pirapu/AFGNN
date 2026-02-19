public class func{
public void forceGCAction(){
            info += decimalFormatter.format(runtime.maxMemory()) + " max, ";
            info += decimalFormatter.format(runtime.totalMemory()) + " total, ";
            info += decimalFormatter.format(runtime.totalMemory()-runtime.freeMemory()) + " used, ";
            info += decimalFormatter.format(runtime.freeMemory()) + " free\n";
            result.setValue(info);
            log.info("GC forced\n"+info);
}
}
