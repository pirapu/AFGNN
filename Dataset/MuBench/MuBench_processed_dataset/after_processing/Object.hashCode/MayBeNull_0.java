public class func{
public void misuse(Object maybeNull) {
  if (maybeNull == null) {
    maybeNull.hashCode();
  }
}
}
