import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;

public class DirPanel extends JPanel {
    private JScrollPane scrollPane;
    private JTree dirTree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode node;
    private DefaultMutableTreeNode subNode;
    private FileNode file;
    File[] paths;

    public DirPanel(){
        scrollPane = new JScrollPane();
        dirTree = new JTree();
        //file.getAbsolutePath();
        buildTree();
        scrollPane.setPreferredSize(new Dimension(250,500));
        scrollPane.setViewportView(dirTree);
        add(scrollPane);
    }

    /**
     *
     */
    private void buildTree(){
        File fileRoot = new File("C:/");
        root = new DefaultMutableTreeNode(new FileNode("C:/",fileRoot));
        treeModel = new DefaultTreeModel(root);

        paths = fileRoot.listFiles();
        for(File file : paths){
            node = new DefaultMutableTreeNode(new FileNode(file.getName(),file));
            root.add(node);
            }
        dirTree.setModel(treeModel);
    }

    /**
     *
     */
    class NewTreeSelectionListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) dirTree.getLastSelectedPathComponent();
            FileNode fn = (FileNode) node.getUserObject();

        }
    }
}
