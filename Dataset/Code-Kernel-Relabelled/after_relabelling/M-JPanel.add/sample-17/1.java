public class func{
public void buildActions(CControl control,MultipleCDockableFactory<DefaultMultipleCDockable,?> factory){
    panel.add( buildAction( "Red", new AddDockableAction( control, factory, Color.RED, new Path( "custom", "red" ))));
    panel.add( buildAction( "Green", new AddDockableAction( control, factory, Color.GREEN, new Path( "custom", "green" ))));
    panel.add( buildAction( "Blue", new AddDockableAction( control, factory, Color.BLUE, new Path( "custom", "blue" ))));
    panel.add( buildAction( "Yellow", new AddDockableAction( control, factory, Color.YELLOW, new Path( "custom", "yellow" ))));
    panel.add( buildAction( "Magenta", new AddDockableAction( control, factory, Color.MAGENTA, new Path( "custom", "magenta" ))));
    panel.add( buildAction( "Cyan", new AddDockableAction( control, factory, Color.CYAN, new Path( "custom", "cyan" ))));
}
}
