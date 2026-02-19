public class func{
public void mouseClicked(MouseEvent e){
            Runtime currR = Runtime.getRuntime();
            long freeM = currR.freeMemory();
            long totalM = currR.totalMemory();
            long maxM = currR.maxMemory();
            logPanel.
            logMessage("[KnowledgeFlow] Memory (free/total/max.) in bytes: " 
                + String.format("%,d", freeM) + " / " 
                + String.format("%,d", totalM) + " / " 
                + String.format("%,d", maxM));
            logPanel.statusMessage("[KnowledgeFlow]|Memory (free/total/max.) in bytes: " 
                + String.format("%,d", freeM) + " / " 
                + String.format("%,d", totalM) + " / " 
                + String.format("%,d", maxM)); 
}
}
