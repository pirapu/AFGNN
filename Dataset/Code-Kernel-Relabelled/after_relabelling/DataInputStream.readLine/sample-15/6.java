public class func{
public void GenJCov(Environment env){
        File outFile = env.getcovFile();
        if( outFile.exists()) {
           DataInputStream JCovd = new DataInputStream(
                                                       new BufferedInputStream(
                                                                               new FileInputStream(outFile)));
           String CurrLine = null;
           boolean first = true;
           String Class;
           CurrLine = JCovd.readLine();
           if ((CurrLine != null) && CurrLine.startsWith(JcovMagicLine)) {
                   while((CurrLine = JCovd.readLine()) != null ) {
                      if ( CurrLine.startsWith(JcovClassLine) ) {
                             first = true;
                             for(Enumeration e = SourceClassList.elements(); e.hasMoreElements();) {
                                 String clsName = CurrLine.substring(JcovClassLine.length());
                                 int idx = clsName.indexOf(' ');
                                 if (idx != -1) {
                                     clsName = clsName.substring(0, idx);
                                 }
                                 Class = (String)e.nextElement();
                                 if ( Class.compareTo(clsName) == 0) {
                                     first = false;
                                     break;
                                 }
                             }
                      }
                      if (first)
                          TmpCovTable.addElement(CurrLine);
                   }
           }
           JCovd.close();
        }
        PrintStream CovFile = new PrintStream(new DataOutputStream(new FileOutputStream(outFile)));
        CovFile.println(JcovMagicLine);
}
}
