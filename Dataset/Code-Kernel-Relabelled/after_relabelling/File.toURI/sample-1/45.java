public class func{
public void actionPerformed(final ActionEvent event){
                final File file = daeFiles.get(fileIndex);
                    loadColladaModel(new URLResourceSource(file.toURI().toURL()));
                    t1.setText(file.getName());
}
}
