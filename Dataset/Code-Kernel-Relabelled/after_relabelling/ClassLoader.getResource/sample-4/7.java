public class func{
public void testSlaves(){
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                classLoader.getResource("slaves").openStream()));
        String line = in.readLine();
}
}
