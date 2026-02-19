public class func{
public void chatLinkClicked(URI url){
        String action = url.getPath();
        if (action.equals("/SHOWPREVIEW"))
        {
            currentMessageID = url.getQuery();
            currentLinkPosition = url.getFragment();
            this.setVisible(true);
            this.setLocationRelativeTo(chatPanel);
        }
}
}
