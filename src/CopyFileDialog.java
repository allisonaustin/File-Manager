import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class CopyFileDialog extends JDialog {
    private JPanel contentPane;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField fromField;
    private JTextField toField;
    private JLabel currDirectory;
    private static FilePanel filePanel;
    private static int row;
    private static File file;

    public CopyFileDialog(java.awt.Frame parent, boolean model, FilePanel fp, int row) {
        super(parent, model);
        super.setTitle("Copying");
        filePanel = fp;
        this.row = row;
        file = filePanel.getFilesInList().get(row);
        currDirectory.setText("Current Directory: "+ file.getAbsolutePath());
        setFromField(file.getName());
        setContentPane(contentPane);
        pack();
        toField.requestFocusInWindow();
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
                //copy file somehow
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
                CopyFileDialog dialog = new CopyFileDialog(new JFrame(), true, filePanel, row);
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