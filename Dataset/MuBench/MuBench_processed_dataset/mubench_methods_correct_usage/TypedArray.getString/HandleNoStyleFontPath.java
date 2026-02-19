String pattern(Context context, AttributeSet attrs, int attributeId) {
  TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{attributeId});
  try {
    return typedArray.getString(0);
  } catch (Exception ignore) {
    return null;
  } finally {
    typedArray.recycle();
  }
}