public class func{
public void notifyWriteSpillBegin(Spillable spillable,int spillSize,String spillReason){
      int numFiles = spillable.spillCount();
      if( numFiles % 10 == 0 )
        {
        LOG.info( "spilling group: {}, on grouping: {}, num times: {}, with reason: {}",
          new Object[]{joinField.printVerbose(), spillable.getGrouping().print(), numFiles + 1, spillReason} );
        Runtime runtime = Runtime.getRuntime();
        long freeMem = runtime.freeMemory() / 1024 / 1024;
        long maxMem = runtime.maxMemory() / 1024 / 1024;
        long totalMem = runtime.totalMemory() / 1024 / 1024;
        LOG.info( "mem on spill (mb), free: " + freeMem + ", total: " + totalMem + ", max: " + maxMem );
        }
      LOG.info( "spilling {} tuples in list to file number {}", spillSize, numFiles + 1 );
      flowProcess.increment( Spill.Num_Tuples_Spilled, spillSize );
}
}
