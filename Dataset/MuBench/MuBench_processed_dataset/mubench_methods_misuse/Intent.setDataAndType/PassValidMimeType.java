void pattern(File file) {
  Intent intent = new Intent();
  intent.setAction(Intent.ACTION_VIEW);
  intent.setDataAndType(Uri.fromFile(file), "image/jpg");
  return intent;
}