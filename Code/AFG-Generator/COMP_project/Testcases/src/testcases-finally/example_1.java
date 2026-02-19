public class example_1 {
    public static void readFileByLine(String filePath) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(filePath));
                String line = reader.readLine();
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            } finally {
                reader.close();
            }
        }
    }
    
