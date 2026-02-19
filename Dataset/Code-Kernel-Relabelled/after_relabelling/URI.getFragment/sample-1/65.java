public class func{
public void onResponse(EnhancedMockHttpServletResponse response){
                showResponse("requestParameterMethodHS256Step2", response);
                assertEquals(response.getStatus(), 302, "Unexpected response code.");
                assertNotNull(response.getHeader("Location"), "Unexpected result: " + response.getHeader("Location"));
                    URI uri = new URI(response.getHeader("Location").toString());
                    assertNotNull(uri.getFragment(), "Query string is null");
                    Map<String, String> params = QueryStringDecoder.decode(uri.getFragment());
                    assertNotNull(params.get("access_token"), "The accessToken is null");
                    assertNotNull(params.get("scope"), "The scope is null");
                    assertNotNull(params.get("state"), "The state is null");
}
}
