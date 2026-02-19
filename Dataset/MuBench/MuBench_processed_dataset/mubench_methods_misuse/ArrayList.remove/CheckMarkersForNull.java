void pattern(Map foregroundDomainMarkers, int index, Marker marker) {
  ArrayList markers = (ArrayList) foregroundDomainMarkers.get(new Integer(index));
  
    markers.remove(marker);
  
}