public class func{
public JSType pattern(UnionTypeBuilder builder) {
  JSType result = builder.build();
  if(!result.isNoType()) {
    return result;
  } else {
    return null;
  }
}
}
