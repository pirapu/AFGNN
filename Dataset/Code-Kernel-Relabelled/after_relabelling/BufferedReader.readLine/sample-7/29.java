public class func{
public void run(){
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        while ((line = br.readLine()) != null)
          result.append(line).append("\n");
}
}
