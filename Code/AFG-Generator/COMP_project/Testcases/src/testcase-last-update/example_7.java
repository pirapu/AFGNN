public class example_7 {
    public void createOutput(String namespaceURI, String suggestedFileName) {
        String filePath = this.modifyFileName(namespaceURI);
        File file = new File(filePath);
        StreamResult result = new StreamResult(file);
        result.setSystemId(file.toURI().toURL().toString());
        schemaFiles.put(namespaceURI, file);
    }
}
