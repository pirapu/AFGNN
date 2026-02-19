public class func{
void pattern(StatisticalCategoryDataset dataset, int row, int column) {
  Number meanValue = dataset.getMeanValue(row, column);
  if (meanValue != null) {
    meanValue.doubleValue();
  }
}
}
