public class func{
public void pattern() {
  JJButton button = new JButton(":some button:");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            button.setText(":clicked:");
          }
        });
      }
    });
}
}
