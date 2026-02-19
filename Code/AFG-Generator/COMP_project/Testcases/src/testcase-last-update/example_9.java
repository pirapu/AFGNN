public class example_9 {
    public void readFully(BufferedReader br) {
        while ((line = br.readLine()) != null) {
            buf.append(line);
            buf.append('\n');
        }
        return buf.toString();
    }
}
