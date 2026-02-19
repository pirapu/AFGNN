public class func{
String pattern(Intent intent) {
  return intent.getStringExtra(UxArgument.SELECTED_ACCOUNT_UID, 0L);
}
}
