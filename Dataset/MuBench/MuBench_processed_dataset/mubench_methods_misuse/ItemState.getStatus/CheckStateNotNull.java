void canRead(ItemData data) throws AccessDeniedException, RepositoryException {
  ItemState state = data.getState();
  
  if (state.getStatus() == ItemState.STATUS_NEW) {
    // do something...
  }
}

