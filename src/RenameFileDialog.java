import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RenameFileDialog extends JDialog {
    private JPanel contentPane;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField fromField;
    private JTextField toField;

    public RenameFileDialog(java.awt.Frame parent, boolean model) {
        super(parent, model);
        super.setTitle("Rename");
        setContentPane(contentPane);
        pack();
        this.setLocationRelativeTo(null);
        setModal(true);

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                setVisible(false);
            }
        });

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String toField = getToField();
                setFromField(toField);
                setVisible(false);
            }
        });
    }

    public String getToField(){
        return toField.getText();
    }

    public void setFromField(String toField){
        fromField.setText(toField);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RenameFileDialog dialog = new RenameFileDialog(new JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing (java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

}
