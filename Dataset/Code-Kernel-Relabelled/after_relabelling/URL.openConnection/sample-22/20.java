public class func{
public void parseProperties(final URL url,final FreeplaneVersion currentVersion){
        versionProperties.load(new InputStreamReader(url.openConnection().getInputStream()));
        if (versionProperties.getProperty("version") != null) {
            remoteVersion = FreeplaneVersion.getVersion(versionProperties.getProperty("version"));
            successful = true;
            remoteVersionDownloadUrl = parseUrl(versionProperties.getProperty("downloadUrl"));
            remoteVersionChangelogUrl = parseUrl(versionProperties.getProperty("changelogUrl"));
            return remoteVersion.compareTo(currentVersion) > 0;
        } else {
            LogUtils.warn("add-on update: no version found in " + url);
            return false;
        }
}
}
