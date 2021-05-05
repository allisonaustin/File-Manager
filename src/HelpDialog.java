import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class HelpDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public HelpDialog(java.awt.Frame parent, boolean model) {
        super(parent, model);
        setContentPane(contentPane);
        this.setSize(450, 400);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        try {
            Desktop.getDesktop().browse(new URL("http://www.google.com").toURI());
            setVisible(false);
        } catch (Exception e) {
            System.out.println("Uh oh");
        }
    }

    public static void main(String[] args) {
        HelpDialog dialog = new HelpDialog(new JFrame(), true);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
