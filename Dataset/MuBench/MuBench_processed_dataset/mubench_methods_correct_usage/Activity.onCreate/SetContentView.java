protected void onCreate(Bundle state) {
  super.onCreate(state);
  setContentView(R.layout.main); // <-- required for findViewById to succeed
  findViewById(R.id.editText1);
}
