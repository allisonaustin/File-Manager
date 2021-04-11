import javax.swing.*;
import java.awt.event.*;

public class AboutDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public AboutDialog(java.awt.Frame parent, boolean model) {
        super(parent, model);
        setContentPane(contentPane);
        this.setSize(300, 300);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        // add your code here
        setVisible(false);
    }

    public static void main(String[] args) {
        AboutDialog dialog = new AboutDialog(null, true);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
