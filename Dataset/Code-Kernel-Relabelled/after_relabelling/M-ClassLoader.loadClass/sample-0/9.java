public class func{
public void loadCompiledClasses(String... loadList){
        for (int i = 0; i < loadedClasses.length; i++)
            loadedClasses[i] = cl.loadClass(loadList[i]);
}
}
