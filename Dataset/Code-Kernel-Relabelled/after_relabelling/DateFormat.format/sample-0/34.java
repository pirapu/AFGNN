public class func{
public void showValidity(Date notBefore,Date notAfter,View dialogView){
        DateFormat dateFormat = DateFormat.getDateInstance();
        fromView.setText(dateFormat.format(notBefore));
        toView.setText(dateFormat.format(notAfter));
}
}
