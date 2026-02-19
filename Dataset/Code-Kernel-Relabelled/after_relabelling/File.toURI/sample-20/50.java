public class func{
public void getLocalMetadataRepository(MavenContext context,LocalRepositoryP2Indices localRepoIndices){
            File localMavenRepoRoot = context.getLocalRepositoryRoot();
            RepositoryReader contentLocator = new LocalRepositoryReader(localMavenRepoRoot);
            localMetadataRepository = new LocalMetadataRepository(localMavenRepoRoot.toURI(),
                    localRepoIndices.getMetadataIndex(), contentLocator);
}
}
