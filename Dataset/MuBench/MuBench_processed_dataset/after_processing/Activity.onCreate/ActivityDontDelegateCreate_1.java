public class func{
protected void onCreate(Bundle state) {
	super.onCreate(state);
	String someText = ":some saved text:";
	mEditText = (EditText) findViewById(R.id.editText1);
	mEditText.setText(someText);
}
}
