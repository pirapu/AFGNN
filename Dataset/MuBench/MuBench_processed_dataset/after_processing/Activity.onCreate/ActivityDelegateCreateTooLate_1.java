public class func{
protected void onCreate(Bundle state) {
  setContentView(R.layout.main);
  super.onCreate(state);
  String someText = ":some saved text:";
  mEditText = (EditText) findViewById(R.id.editText1);
  mEditText.setText(someText);
}
}
