public class func{
public void getMemoryBar(int size,Runtime run){
    double percentUsed = (run.totalMemory() - run.freeMemory())
        / run.maxMemory();
    int pivot = (int) Math.floor(size * percentUsed);
    for (int i = 0; i < pivot - 1; i++)
      line += "=";
    if (pivot < size - 1)
      line += "+";
    for (int i = pivot + 1; i < size; i++)
      line += "-";
}
}
