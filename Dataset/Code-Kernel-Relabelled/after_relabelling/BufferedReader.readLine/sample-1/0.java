public class func{
public void readJournalLines(){
    BufferedReader reader = new BufferedReader(new FileReader(journalFile));
    while ((line = reader.readLine()) != null) {
      result.add(line);
    }
    reader.close();
}
}
