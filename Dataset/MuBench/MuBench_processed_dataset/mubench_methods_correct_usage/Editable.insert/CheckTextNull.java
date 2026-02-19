void pattern(WPEditText mContentEditText, int mSelectionEnd) {
  Editable str = mContentEditText.getText();
  if (str != null) {
    if (mSelectionEnd > str.length())
      mSelectionEnd = str.length();
    str.insert(mSelectionEnd, "\n<!--more-->\n");
  }
}
