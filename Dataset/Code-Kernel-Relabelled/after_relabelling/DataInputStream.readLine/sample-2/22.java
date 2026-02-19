public class func{
public void decode(){
        String rawAuthor = n.substring("author ".length());
        n = br.readLine();
        if (n == null || !n.startsWith("committer ")) {
          throw new CorruptObjectException(commitId, "no committer");
        }
}
}
