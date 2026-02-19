public class func{
public void getEditorComponent(){
        DispatchXPathGroovyEditorModel editorModel = new DispatchXPathGroovyEditorModel();
        xpathEditor = new GroovyEditor(editorModel);
        xpathEditorPanel.add(xpathEditor, BorderLayout.CENTER);
        xpathEditorPanel.add(buildXPathEditorToolbar(editorModel), BorderLayout.PAGE_START);
}
}
