public class dummy {
public static void printBufferedReaderLines(BufferedReader bufferedReader) {
    String line;
    try {
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    } catch (IOException e) {
        System.err.println("Error reading from BufferedReader: " + e.getMessage());
        e.printStackTrace();
    }
}
}