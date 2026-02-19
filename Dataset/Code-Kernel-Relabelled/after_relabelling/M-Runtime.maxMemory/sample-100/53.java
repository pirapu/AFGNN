public class func{
public void addMemoryMetrics(List<MetricDatum> targetList,Set<MachineMetric> customSet){
        Runtime rt = Runtime.getRuntime();
        long totalMem = rt.totalMemory();
        long freeMem = rt.freeMemory();
        long spareMem = rt.maxMemory() - usedMem;
        List<Long> values = Arrays.asList(totalMem, freeMem, usedMem, spareMem);
        MetricValues metricValues = memoryMetricValues(customSet, values);
        addMetrics(targetList, metricValues, StandardUnit.Bytes);
}
}
