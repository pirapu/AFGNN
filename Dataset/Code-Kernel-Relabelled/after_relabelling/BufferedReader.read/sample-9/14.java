public class func{
public void deserializeXml(String argument,boolean isPath){
                while ((lengthRead = br.read(buffer)) != -1) {
                    xmlSb.append(buffer, 0, lengthRead);
                }
                return deserializeXmlString(xmlSb.toString());
}
}
