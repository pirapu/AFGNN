public class func{
String pattern(Context context, int styleId, int attributeId) {
  Resources.Theme theme = context.getTheme();
  TypedValue value = new TypedValue();
  theme.resolveAttribute(styleId, value, true);
  TypedArray typedArray = theme.obtainStyledAttributes(value.resourceId, new int[]{attributeId});
  try {
    return typedArray.getString(0);
  } catch (Exception ignore) {
    return null;
  } finally {
    typedArray.recycle();
  }
}
}
