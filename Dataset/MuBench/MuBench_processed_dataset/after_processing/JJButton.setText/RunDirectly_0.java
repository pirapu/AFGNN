public class func{
public void pattern() {
  JButton button = new JButton(":some button:");
  button.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      button.setText(":clicked:");
    }
  });
}
}
