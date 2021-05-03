import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class RenameFileDialog extends JDialog {
    private JPanel contentPane;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField fromField;
    private JTextField toField;
    private JLabel currDirectory;
    private static String command;
    private static FilePanel filePanel;
    private static int row;
    private static File file;

    public RenameFileDialog(java.awt.Frame parent, boolean model, FilePanel fp, int row, String command) {
        super(parent, model);
        super.setTitle(command);
        filePanel = fp;
        this.row = row;
        file = filePanel.getFilesInList().get(row);
        currDirectory.setText("Current Directory:   "+ file.getAbsolutePath());
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
                if(command.equals("Rename")){
                    filePanel.renameFile(toField);
                }
                else if(command.equals("Copying")){
                    filePanel.copyFile(row);
                }
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
                RenameFileDialog dialog = new RenameFileDialog(new JFrame(), true, filePanel, row, command);
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
