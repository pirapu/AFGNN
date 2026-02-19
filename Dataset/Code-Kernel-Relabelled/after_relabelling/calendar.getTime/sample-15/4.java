public class func{
public void toString(Object obj){
        if (obj instanceof Calendar) {
            obj = ((Calendar) obj).getTime();
        }
}
}
