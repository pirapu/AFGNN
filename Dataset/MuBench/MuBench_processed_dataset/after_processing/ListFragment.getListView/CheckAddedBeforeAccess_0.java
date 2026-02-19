public class func{
void pattern(ListFragment lf, int position) {
  if (lf.isAdded() && lf.getListView() != null) {
    lf.getListView().setSelectionFromTop(position, 0);
  }
}
}
