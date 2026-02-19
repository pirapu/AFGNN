public class func{
void getParent() throws ItemNotFoundException, RepositoryException {
  NodeEntry parent = getHierarchyEntry().getParent();
  if (parent != null) {
      parent.getNodeState();
  }
}
}
