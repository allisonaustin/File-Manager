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
    private File rootFile;
    private FilePanel filePanel;

    public DirPanel(FilePanel fp, FileManagerFrame frame){
        setLayout(new BorderLayout());
        scrollPane = new JScrollPane();
        scrollPane.setMinimumSize(new Dimension(250,500));
        setFilePanel(fp);
        rootFile = App.getDrives()[0];
        dirTree = new JTree();
        setTree();
        buildTree();
    }

    public void setRootFile(String root){
        rootFile = new File(root);
    }

    public String getRootFile(){
        return rootFile.getPath();
    }

    public JTree getDirTree() {
        return dirTree;
    }

    public void setFilePanel(FilePanel fp){
        filePanel = fp;
    }

    public void changeFilePanel(){

    }

    /**
     *
     */
    public void setTree() {
        FileNode root = new FileNode(rootFile.getPath(), rootFile);
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(root);
        treeModel = new DefaultTreeModel(top);
        dirTree.setRootVisible(true);
        dirTree = new JTree(top);
        dirTree.expandRow(0);

        FileManagerFrame.currentSelected = rootFile.getAbsolutePath();
        createNodes(top);
        dirTree.expandRow(0);

        dirTree.setSelectionRow(FileManagerFrame.lastSelectedRow);
        dirTree.expandRow(FileManagerFrame.lastSelectedRow);

        changeFilePanel();
        dirTree.addTreeSelectionListener(new MyTreeSelectionListener());
        dirTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        dirTree.setModel(treeModel);

        scrollPane.setViewportView(dirTree);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createNodes(DefaultMutableTreeNode top) {
        File directory;
        File[] files;
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;

        directory = rootFile;

        if(directory.isDirectory()){
            files = directory.listFiles();
        }
    }

    /**
     *
     */
    private void buildTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootFile);
        treeModel = new DefaultTreeModel(root);
        File[] paths = rootFile.listFiles();
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
        scrollPane.setViewportView(dirTree);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     *
     */
    class MyTreeSelectionListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            FileManagerFrame.lastSelectedRow = dirTree.getMinSelectionRow();
            changeFilePanel();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) dirTree.getLastSelectedPathComponent();
            if(node == null){
                return;
            }
            FileNode fn = (FileNode) node.getUserObject();
        }
    }
}
