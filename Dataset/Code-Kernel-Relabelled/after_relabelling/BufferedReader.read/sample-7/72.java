public class func{
public void getTime(){
            read = reader.read(__buffer, 0, __buffer.length);
            if (read <= 0)
                break;
            result.append(__buffer, 0, read);
        return result.toString();
}
}
