public class func{
public void main(String[] args){
        reader = new BufferedReader(new
                BoundedReader(strings[3][0]), strings[3][0].length());
        reader.readLine();
        reader.read(buf, 0, 9);
        String newStr = new String(buf);
        if (!newStr.equals(strings[3][1]))
            throw new RuntimeException("Read(char[],int,int) failed");
}
}
