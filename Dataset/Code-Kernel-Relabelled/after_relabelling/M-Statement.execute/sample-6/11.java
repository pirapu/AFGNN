public class func{
public void doRun(Connection cx){
                Statement st = open(cx.createStatement());
                st.execute(log(sql, LOG));
                st.execute(log(sql, LOG));
}
}
