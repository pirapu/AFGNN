public class func{
public void widgetSelected(final SelectionEvent e){
                    reader = new BufferedReader(new FileReader(filename));
                    while ((numRead = reader.read(buf)) != -1) {
                        String readData = String.valueOf(buf, 0, numRead);
                        fileData.append(readData);
                        buf = new char[1024];
                    }
                    reader.close();
}
}
