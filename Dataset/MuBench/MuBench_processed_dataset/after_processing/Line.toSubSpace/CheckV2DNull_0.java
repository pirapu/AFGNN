public class func{
void pattern(Line line, Line other) {
  Vector2D v2D = line.intersection(other);
  if (v2D != null) {
    line.toSubSpace(v2D);
    other.toSubSpace(v2D);
  }
}
}
