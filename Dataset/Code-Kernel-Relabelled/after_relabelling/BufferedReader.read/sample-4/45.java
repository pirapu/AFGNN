public class func{
public void readFile(File file){
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[BUFFER_SIZE];
        }
        reader.close();
}
}
