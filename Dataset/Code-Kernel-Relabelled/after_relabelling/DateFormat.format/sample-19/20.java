public class func{
public void setCreated(Workspace workspace,Date created){
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formatted = df.format(created);
        workspace.setAttribute(WORKSPACE_ATTRIBUTE_CREATED, formatted);
}
}
