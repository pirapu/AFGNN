public class func{
public void DownloadHistory(Context context){
        Config config = new Config(context);
        File historyFile = config.getPodcastRootPath("history.prop");
            DataInputStream dis = new DataInputStream(new FileInputStream(historyFile));
            String line = dis.readLine();
            if (!line.startsWith(HISTORY_TWO_HEADER)) {
                historyEntries.add(new HistoryEntry(UNKNOWN_SUBSCRIPTION, line));
                while ((line = dis.readLine()) != null) {
                    historyEntries.add(new HistoryEntry(UNKNOWN_SUBSCRIPTION, line));
                }
            } else {
                ObjectInputStream ois = new ObjectInputStream(dis);
                historyEntries = (List<HistoryEntry>) ois.readObject();
                ois.close();
            }
            Log.e(DownloadHelper.class.getName(), "error reading history file " + historyFile.toString(), e);
}
}
