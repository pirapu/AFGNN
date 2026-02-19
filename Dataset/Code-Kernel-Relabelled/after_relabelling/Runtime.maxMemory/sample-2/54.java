public class func{
public void memory(){
        Runtime rt = Runtime.getRuntime(); 
        long maxMemory=rt.maxMemory();
        long freeMemory=rt.freeMemory();
        long totalMemory=rt.totalMemory();
        response = ImmutableMap.of(
            "max_allocable_memory",maxMemory,
            "current_allocate_memory", totalMemory,
            "used_memory_in_the_allocate_memory",totalMemory - freeMemory,
            "free_memory_in_the_allocated_memory", freeMemory
            );
}
}
