public class func{
public void checkFeatureTimestamp(final String schema,final String uniquename,final GmodDAO dao,final String keyName,final Hashtable<String,Feature> featureIdStore,final ChadoTransaction tsn){
      timestamp.setNanos(0);
      if(now.compareTo(timestamp) != 0)
      {
        final SimpleDateFormat date_format = 
                   new SimpleDateFormat("dd.MM.yyyy hh:mm:ss z");
        int select = JOptionPane.showConfirmDialog(null, uniquename +
                                      " has been altered at :\n"+
                                      date_format.format(now)+"\nOverwite?", 
                                      "Feature Changed", 
                                      JOptionPane.OK_CANCEL_OPTION);
        if(select == JOptionPane.OK_OPTION)
          return true;
        else
          return false;
      }
}
}
