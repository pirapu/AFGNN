public class func{
public void ServicePostTreeRenderer(){
          ClassLoader loader = this.getClass().getClassLoader();
          workspaceIcon = new ImageIcon(loader.getResource("images/WorkspaceNodeImage.gif"));
          serviceIcon = new ImageIcon(loader.getResource("images/ServiceNodeImage.gif"));
          collectionIcon = new ImageIcon(loader.getResource("images/CollectionNodeImage.gif"));
          fileIcon = new ImageIcon(loader.getResource("images/ServiceNodeImage.gif"));
}
}
