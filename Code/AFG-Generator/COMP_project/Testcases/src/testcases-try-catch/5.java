public class dummy {
public static int countLines(String filePath) {
    int lineCount = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        while (reader.readLine() != null) {
            lineCount++;
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return lineCount;
}
}
