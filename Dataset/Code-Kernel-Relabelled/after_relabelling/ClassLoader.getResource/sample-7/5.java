public class func{
public void createImageApplication(String appTitle){
        ClassLoader loader = getClass().getClassLoader();
        loadIconField.sendKeys(loader.getResource(FILE_ICON_NAME).getPath().substring(1).replace('/','\\'));
        loadImageField.sendKeys(loader.getResource(FILE_IMAGE_NAME).getPath().substring(1).replace('/','\\'));
}
}
