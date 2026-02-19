public class func{
File pattern(PMContext context) throws Exception {
  File envDir = new File(context.getHomeDir(), "db");
  if (!envDir.exists())
    envDir.mkdirs();
  return envDir;
}
}
