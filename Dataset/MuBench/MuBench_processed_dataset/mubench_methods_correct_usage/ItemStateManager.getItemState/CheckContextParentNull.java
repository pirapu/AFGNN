
void pattern(NodeState context, ItemStateManager ism) {
  NodeId parentId = context.getParentId()
  if (parentId != null) {
    ism.getItemState(parentId);
  }
}

