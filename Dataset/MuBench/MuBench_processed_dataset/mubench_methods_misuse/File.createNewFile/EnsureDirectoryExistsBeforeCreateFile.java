void pattern(File lockDir, String lockFilename) throws IOException {
  File lockFile = new File(lockDir, lockFilename);
 
  lockFile.createNewFile();
}

