public class func{
void pattern(PlotRenderingInfo plotState, AxisLabelEntity someEntity) {
  ChartRenderingInfo owner = plotState.getOwner();
  if (owner != null) {
    EntityCollection entities = owner.getEntityCollection();
    if (entities != null) {
      entities.add(someEntity);
    }
  }
}
}
