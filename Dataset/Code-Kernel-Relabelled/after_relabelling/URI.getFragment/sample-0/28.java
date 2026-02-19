public class func{
public void onResponse(EnhancedMockHttpServletResponse response){
                showResponse("requestAccessTokenCustomClientAuth2", response);
                assertEquals(response.getStatus(), 302, "Unexpected response code.");
                assertNotNull(response.getHeader("Location"), "Unexpected result: " + response.getHeader("Location"));
                if (response.getHeader("Location") != null) {
                    try {
                        URI uri = new URI(response.getHeader("Location").toString());
                        assertNotNull(uri.getFragment(), "Fragment is null");
                        Map<String, String> params = QueryStringDecoder.decode(uri.getFragment());
                        assertNotNull(params.get(AuthorizeResponseParam.ACCESS_TOKEN), "The access_token is null");
                        assertNotNull(params.get(AuthorizeResponseParam.ID_TOKEN), "The id_token is null");
                        assertNotNull(params.get(AuthorizeResponseParam.STATE), "The state is null");
                        assertNotNull(params.get(AuthorizeResponseParam.TOKEN_TYPE), "The token type is null");
                        assertNotNull(params.get(AuthorizeResponseParam.EXPIRES_IN), "The expires_in value is null");
                        assertNotNull(params.get(AuthorizeResponseParam.SCOPE), "The scope must be null");
                        assertNull(params.get("refresh_token"), "The refresh_token must be null");
                        assertEquals(params.get(AuthorizeResponseParam.STATE), state);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        fail("Response URI is not well formed");
                    }
                }
}
}
