public class func{
public void readOvfContent(String ovfFilePath){
    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(ovfFilePath)));  
    while ((lineStr = in.readLine()) != null) {  
      strContent.append(lineStr);  
    }  
    in.close();  
}
}
