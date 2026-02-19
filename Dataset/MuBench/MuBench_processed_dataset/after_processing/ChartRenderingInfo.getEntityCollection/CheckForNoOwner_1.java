public class func{
void pattern(PlotRenderingInfo plotState, AxisLabelEntity someEntity) {
  ChartRenderingInfo owner = plotState.getOwner();
    EntityCollection entities = owner.getEntityCollection();
      entities.add(someEntity);
}
}
