public class func{
public void buildRevisionFromMap(URI documentURI,Map<String,? extends Object> map){
                documentURI.getQuery();
                String attachmentURIPath = documentURI.getPath()+"/"+key;
                    attachmentURI = new URI(documentURI.getScheme(),
                            documentURI.getUserInfo(),
                            documentURI.getHost(),
                            documentURI.getPort(),
                            attachmentURIPath,
                            documentURI.getQuery(),
                            documentURI.getFragment());
}
}
