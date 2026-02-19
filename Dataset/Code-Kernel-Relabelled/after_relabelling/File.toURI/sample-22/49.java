public class func{
public void validateXliffFile(String fileLocalPath){
    IFile file = root.getFileForLocation(URIUtil.toPath(new File(fileLocalPath).toURI()));
    if (file == null) {
      Shell shell = Display.getDefault().getActiveShell();
      MessageDialog.openError(shell, Messages.getString("file.XLFValidator.msgTitle"),
          Messages.getString("file.XLFValidator.msg9"));
      return false;
    }
    return validateXliffFile(file);
}
}
