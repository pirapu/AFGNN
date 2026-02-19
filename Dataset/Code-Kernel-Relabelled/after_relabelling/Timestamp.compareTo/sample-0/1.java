public class func{
public void compare(Object o1,Object o2){
                        if (t2 == null)
                            t2 = UIUtilities.getDefaultTimestamp();
                        int r = t1.compareTo(t2);
                        if (r < 0) v = -1;
                        else if (r > 0) v = 1;
}
}
