public class func{
public void fillsBuffer(){
        for (int i = 0; i < 1000; ++i) {
            sb.append((char)i);
        }
        BufferedReader reader = new BufferedReader(new StringReader(sb.toString()), 101);
        reader.read(buffer);
        assertEquals(499, buffer[499]);
}
}
