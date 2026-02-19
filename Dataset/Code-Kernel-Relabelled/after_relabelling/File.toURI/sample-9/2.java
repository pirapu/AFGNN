public class func{
public void findInPaths(String iconPath){
        for(String dataDir : DEFAULT_XDG_DATA_DIRS) {
            File file = new File(dataDir + iconPath);
            if(file.exists()) {
                try {
                    return file.toURI().toURL();
                } catch (MalformedURLException e) {
                    LoggerFactory.getLogger().warn(e.toString());
                }
            }
        }
}
}
