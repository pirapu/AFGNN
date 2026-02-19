public class func{
public void createEndpoint(String uri,String remaining,Map<String,Object> parameters){
        URI httpUri = URISupport.createRemainingURI(
                new URI(uriHttpUriAddress.getScheme(),
                        uriHttpUriAddress.getUserInfo(),
                        uriHttpUriAddress.getHost(),
                        uriHttpUriAddress.getPort(),
                        uriHttpUriAddress.getPath(),
                        uriHttpUriAddress.getQuery(),
                        uriHttpUriAddress.getFragment()),
                        parameters);
        endpoint.setHttpUri(httpUri);
        if (headerFilterStrategy != null) {
            endpoint.setHeaderFilterStrategy(headerFilterStrategy);
        } else {
            setEndpointHeaderFilterStrategy(endpoint);
        }
        endpoint.setHttpBinding(getHttpBinding());
}
}
