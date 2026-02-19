public class func{
public void resolveHostNameInUrl(DatacenterMO dcMo,String url){
          URI resolvedUri = new URI(uri.getScheme(), uri.getUserInfo(), hostIp, uri.getPort(), uri.getPath(), uri.getQuery(), uri.getFragment());
          s_logger.info("url " + url + " is resolved to " + resolvedUri.toString() + " through vCenter");
          return resolvedUri.toString();
}
}
