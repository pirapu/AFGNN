public class func{
    public void readPGM(InputStream is){
        DataInputStream dis = new DataInputStream(is);
        String p5 = dis.readLine();
        if (!p5.equals("P5")) {
          throw new IOException("Only P5 is supported");
        }
        String depth = dis.readLine();
        String[] tmp = dim.split(" ");
        int height = Integer.parseInt(tmp[1]);
        int read = dis.read(buf, 0, width * height);
        if (read != width * height) {
          throw new IOException("Could not read data fully");
        }
        for (int i = 0; i < width * height; i++) {
          y[i] = buf[i] & 0xff;
        }
        return new Picture(width, height, new int[][] {y}, ColorSpace.GREY);
    }
    }
    