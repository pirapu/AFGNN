public class func{
public void load(final Reader reader){
        checkNotNull(reader);
        BufferedReader input = new BufferedReader(reader);
        while ((item = input.readLine()) != null) {
            internalAdd(item);
        }
}
}
