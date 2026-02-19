public class func{
public void addUIButton(final JPanel panel,final String tag,final UIMain m){
    panel.add(new JButton(new AbstractAction(tag) {
      static final long serialVersionUID=20071015;
      @Override
      public void actionPerformed(ActionEvent ev) {
        m.callMain(defaultArgs);
      }
    }));
}
}
