public class func{
    public void grabPage(String url){
          u = new URL(url);
          is = u.openStream();
          dis = new DataInputStream(new BufferedInputStream(is));
          while ((s = dis.readLine()) != null) {
            result.append(s).append("\n");
          }
          if (dis != null)
            dis.close();
          if (is != null)
            is.close();
    }
    }
    