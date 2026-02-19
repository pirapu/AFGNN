public class dummy {
public static void readFileByLine(String filePath) {
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    } catch (IOException e) {
        System.err.println("Error reading the file: " + e.getMessage());
    } finally {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing the BufferedReader: " + e.getMessage());
        }
    }
}
}
