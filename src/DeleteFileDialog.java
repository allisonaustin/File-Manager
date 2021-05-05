import javax.swing.*;
import java.awt.event.*;

public class DeleteFileDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonYes;
    private JButton buttonNo;
    private JLabel delete;
    private JLabel icon;
    private static FilePanel filePanel;
    private static int row;

    public DeleteFileDialog(java.awt.Frame parent, boolean model, FilePanel fp, int row) {
        super(parent, model);
        super.setTitle("Deleting!!!");
        filePanel = fp;
        this.row = row;
        delete.setText("Delete " + filePanel.getFilesInList().get(row).getName());
        icon.setIcon(UIManager.getIcon("OptionPane.questionIcon"));
        setContentPane(contentPane);
        this.setLocationRelativeTo(null);
        pack();
        setModal(true);
        getRootPane().setDefaultButton(buttonYes);

        buttonYes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onYES();
            }
        });

        buttonNo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onNO();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onNO();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onNO();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onYES() {
        filePanel.deleteFile(row);
        dispose();
    }

    private void onNO() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DeleteFileDialog dialog = new DeleteFileDialog(new JFrame(), true, filePanel, row);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

}
