public class func{
public void initialize(){
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL dataUrl = classLoader.getResource("org/richfaces/demo/countries.xml");
            countries = ((Countries) unmarshaller.unmarshal(dataUrl)).getCountries();
}
}
