import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FilePanel extends JPanel {
    private JList myList;
    private DefaultListModel listModel;

    public FilePanel(){
        setLayout(new BorderLayout());
        listModel = new DefaultListModel();
        File[] roots = File.listRoots();
        File drive = roots[0];
        File[] files = drive.listFiles();
        File file = files[0];
        displayFiles(new FileNode(file.getName(),file));
        myList = new JList(listModel);
        add(myList, BorderLayout.CENTER);
    }
    public FilePanel(FileNode fileNode){
        setLayout(new BorderLayout());
        listModel = new DefaultListModel();
        displayFiles(fileNode);
        myList = new JList(listModel);
        add(myList, BorderLayout.CENTER);
    }

    private void displayFiles(FileNode fileNode) {
        File[] files = fileNode.getFile().listFiles();
        if( files != null ) {
            for (File f : files) {
                listModel.addElement(f.getName());
            }
        }
    }
}
