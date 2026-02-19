public class func{
public void initDocumentCache(Book book){
    Thread documentIndexerThread = new Thread(new DocumentIndexer(book), "DocumentIndexer");
    documentIndexerThread.setPriority(Thread.MIN_PRIORITY);
    documentIndexerThread.start();
}
}
