public class func{
public void onResponse(EnhancedMockHttpServletResponse response){
                showResponse("requestFileMethod", response);
                assertEquals(response.getStatus(), 302, "Unexpected response code.");
                assertNotNull(response.getHeader("Location"), "Unexpected result: " + response.getHeader("Location"));
                    URI uri = new URI(response.getHeader("Location").toString());
                    assertNotNull(uri.getFragment(), "Query string is null");
                    Map<String, String> params = QueryStringDecoder.decode(uri.getFragment());
                    assertNotNull(params.get("access_token"), "The accessToken is null");
                    assertNotNull(params.get("id_token"), "The idToken is null");
                    assertNotNull(params.get(AuthorizeResponseParam.SCOPE), "The scope is null");
                    assertNotNull(params.get(AuthorizeResponseParam.STATE), "The state is null");
                    assertEquals(params.get(AuthorizeResponseParam.STATE), state);
}
}
