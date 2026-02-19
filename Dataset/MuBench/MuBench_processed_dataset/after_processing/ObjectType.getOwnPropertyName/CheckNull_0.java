public class func{
void pattern(ObjectType interfaceType) {
  ObjectType implicitProto = interfaceType.getImplicitPrototype();
  Set<String> currentPropertyNames;
  if (implicitProto == null) {
    currentPropertyNames = ImmutableSet.of();
  } else {
    currentPropertyNames = implicitProto.getOwnPropertyNames();
  }
  for (String name : currentPropertyNames) {
  }
}
}
