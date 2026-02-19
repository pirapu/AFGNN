public class func{
public void getCodeLines(final IFile file){
        while ((n = reader.read(readBuffer)) > 0) {
          buffer.append(readBuffer, 0, n);
        }
        final IContentType contentType= IDE.getContentType(file);
        final ICodeSubmitContentHandler handler = RCodeLaunching.getCodeSubmitContentHandler(
            (contentType != null) ? contentType.getId() : null );
}
}
