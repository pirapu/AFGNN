public class func{
public void MultiTaskSource(List<TaskSource> sources){
            ExecutorService service = Executors.newFixedThreadPool(sources.size());
            for (TaskSource source : sources) {
                service.submit(new TaskRunner(source));
            }
            service.shutdown();
}
}
