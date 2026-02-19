public class func{
public void test_equals2(){
        URI uri = new URI("http:///~/dictionary");
        URI uri2 = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(),
                uri.getQuery(), uri.getFragment());
        assertTrue(uri2.equals(uri));
        uri = new URI("http://abc.com%E2%82%AC:88/root/news");
}
}
