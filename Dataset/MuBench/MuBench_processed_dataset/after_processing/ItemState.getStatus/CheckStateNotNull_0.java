public class func{
void canRead(ItemData data) throws AccessDeniedException, RepositoryException {
  ItemState state = data.getState();
  if (state == null) {
    throw new InvalidItemStateException(data.getId().toString());
  }
  if (state.getStatus() == ItemState.STATUS_NEW) {
  }
}
}
