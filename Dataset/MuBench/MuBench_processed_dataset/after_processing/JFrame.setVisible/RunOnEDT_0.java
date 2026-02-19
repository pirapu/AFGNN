public class func{
public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			JFrame f = new JFrame("Main Window");
			f.pack(); 
			f.setVisible(true); 
		}
	});
}
}
