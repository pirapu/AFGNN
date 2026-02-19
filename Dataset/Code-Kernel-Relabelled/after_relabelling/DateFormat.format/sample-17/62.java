public class func{
public void commentExists(SQLiteDatabase db,Comment comment){
        Cursor commentCountQuery = db.rawQuery(query, new String[] { comment.getUsername(), comment.getText(), dateFormat.format(comment.getDate()) });
        int count = commentCountQuery.getCount();
        commentCountQuery.close();
}
}
