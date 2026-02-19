public class func{
String pattern(Context context, AttributeSet attrs, int attributeId) {
  TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{attributeId});
    return typedArray.getString(0);
}
}
