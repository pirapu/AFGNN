public class func{
public void update(){
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
            reader.mark(1);
            if (reader.read() != UTF8_BOM) {
                reader.reset();
            }
            ScriptReader r = new ScriptReader(reader);
}
}
