public class func{
class GetStringParameterAsString {
  String pattern(Intent intent) {
    return intent.getStringExtra(UxArgument.SELECTED_ACCOUNT_UID);
  }
}
}
