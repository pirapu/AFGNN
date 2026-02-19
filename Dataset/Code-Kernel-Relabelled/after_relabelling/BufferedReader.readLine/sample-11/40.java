public class func{
public void makeNewLinesCompatibleWithJUnit(String string){
      BufferedReader reader = new BufferedReader(new StringReader(string));
      while ((line = reader.readLine()) != null)
        writer.println(line);
}
}
