public class func{
public void testClassLoaderDoesNotNeedToSeeInvocationHandlerLoader(){
        Class[] interfacesA = { loaderA.loadClass(prefix + "$Echo") };
        Object proxy = Proxy.newProxyInstance(loaderA, interfacesA, invocationHandlerB);
        assertEquals(loaderA, proxy.getClass().getClassLoader());
        assertEquals("foo", proxy.getClass().getMethod("echo", String.class).invoke(proxy, "foo"));
}
}
