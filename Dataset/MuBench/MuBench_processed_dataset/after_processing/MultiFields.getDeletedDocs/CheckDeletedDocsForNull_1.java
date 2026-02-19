public class func{
int pattern(PerfRunData runData) throws Exception {
  IndexReader r = runData.getIndexReader();
  int maxDoc = r.maxDoc();
  int numDeleted = 0;
  int numToDelete = ((int) (maxDoc * percent)) - r.numDeletedDocs();
  if (numToDelete < 0) {
    r.undeleteAll();
    numToDelete = (int) (maxDoc * percent);
  }
  while (numDeleted < numToDelete) {
    double delRate = ((double) (numToDelete-numDeleted))/r.numDocs();
    Bits delDocs = MultiFields.getDeletedDocs(r);
    int doc = 0;
    while (doc < maxDoc && numDeleted < numToDelete) {
      if ((delDocs == null || !delDocs.get(doc)) && random.nextDouble() <= delRate) {
        r.deleteDocument(doc);
        numDeleted++;
          delDocs = MultiFields.getDeletedDocs(r);
      }
      doc++;
    }
  }
  r.decRef();
  return numDeleted;
}
}
