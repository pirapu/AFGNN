public class func{
public void resolveArtifactFileURI(Dependency dependency){
    Artifact artifact = resolveArtifact(dependency);
    File file = artifact.getFile();
    return file.toURI();
}
}
