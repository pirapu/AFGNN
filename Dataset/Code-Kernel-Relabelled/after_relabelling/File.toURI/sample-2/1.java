public class func{
public void addRuntimeConfigPath(File runtimeFile,ServerContainer runtime){
    if (this.configURItoRuntimeName.get(runtimeFile.toURI()) == null) {
      this.configURItoRuntimeName.put(runtimeFile.toURI(), new LinkedList<ServerContainer>());
    }
    this.configURItoRuntimeName.get(runtimeFile.toURI()).add(runtime);    
}
}
