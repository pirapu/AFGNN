public class func{
void pattern(Map foregroundDomainMarkers, int index, Marker marker) {
  ArrayList markers = (ArrayList) foregroundDomainMarkers.get(new Integer(index));
  if (markers != null) {
    markers.remove(marker);
  }
}
}
