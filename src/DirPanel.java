import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DirPanel extends JPanel {
    private JScrollPane scrollPane;
    private JTree dirTree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode root, node, subNode;

    public DirPanel(){
        setLayout(new BorderLayout());
        scrollPane = new JScrollPane();
        scrollPane.setMinimumSize(new Dimension(250,500));
        dirTree = new JTree();
        buildTree();
        scrollPane.setViewportView(dirTree);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     *
     */
    private void buildTree() {
        File[] roots = File.listRoots();
        File fileRoot = roots[0];
        root = new DefaultMutableTreeNode(new FileNode(fileRoot.getAbsolutePath(), fileRoot));
        treeModel = new DefaultTreeModel(root);
        dirTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        dirTree.addTreeSelectionListener(new NewTreeSelectionListener());
        File[] paths = fileRoot.listFiles();
        for (File path : paths) {
            node = new DefaultMutableTreeNode(new FileNode(path.getName(), path));
            if (path.isDirectory()) {
                File[] dir = path.listFiles();
                if (dir != null) {
                    for (File f : dir) {
                        subNode = new DefaultMutableTreeNode(new FileNode(f.getName(), f));
                        node.add(subNode);
                    }
                }
            }
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
