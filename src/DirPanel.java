import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DirPanel extends JPanel {
    private JScrollPane scrollPane;
    private JTree dirTree;
    private DefaultTreeModel treeModel;
    private File rootFile;
    private FilePanel filePanel;
    private FileManagerFrame thisFrame;

    public DirPanel(FilePanel fp, FileManagerFrame frame){
        thisFrame = frame;
        setLayout(new BorderLayout());
        scrollPane = new JScrollPane();
        scrollPane.setMinimumSize(new Dimension(250,500));
        setFilePanel(fp);
        rootFile = App.getDrives()[0]; //c drive
        dirTree = new JTree();
        setTree();
    }

    /**
     * Sets the root file of the filePanel.
     * @param root is the name of the new root file.
     */
    public void setRootFile(String root){
        rootFile = new File(root);
    }

    /**
     * @return path name of the root file.
     */
    public String getRootFile(){
        return rootFile.getPath();
    }

    /**
     * @return the directory tree.
     */
    public JTree getDirTree() {
        return dirTree;
    }

    /**
     * Sets the file panel of the dirTree.
     * Allows the dirPanel and the filePanel to communicate with eachother.
     * @param fp is the file panel linked to the dirPanel.
     */
    public void setFilePanel(FilePanel fp){
        filePanel = fp;
    }

    /**
     * Updates the filePanel by displaying the files starting with the selected directory.
     */
    public void changeFilePanel(){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) dirTree.getLastSelectedPathComponent();
        if(node==null){
            return;
        }
        FileNode dir = (FileNode) node.getUserObject();
        filePanel.displayFiles(dir.getFile());
    }

    /**
     * Sets the directory tree.
     */
    public void setTree() {
        FileNode root = new FileNode(rootFile.getPath(), rootFile);
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(root);
        treeModel = new DefaultTreeModel(top);
        dirTree.setRootVisible(true);
        dirTree = new JTree(top);
        dirTree.setCellRenderer(new MyTreeCellRenderer());
        dirTree.expandRow(0);

        FileManagerFrame.currentSelected = rootFile.getAbsolutePath();
        createNodes(top);
        dirTree.expandRow(0);
        //updating file panel
        filePanel.displayFiles(root.getFile());

        dirTree.setSelectionRow(FileManagerFrame.lastSelectedRow);
        dirTree.expandRow(FileManagerFrame.lastSelectedRow);

        dirTree.addTreeSelectionListener(new MyTreeSelectionListener());
        dirTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        dirTree.setModel(treeModel);

        scrollPane.setViewportView(dirTree);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Explores the nodes underneath the directories 1 level deep.
     * @param top
     */
    private void createNodes(DefaultMutableTreeNode top) {
        File directory;
        File[] files;
        DefaultMutableTreeNode category = null;

        directory = rootFile;
        if(directory.isDirectory()){
            files = directory.listFiles();
            for(int i=0; i<files.length; i++){
                if(files[i].isDirectory()){
                    FileNode fn = new FileNode(files[i].getName(),files[i]);
                    category = new DefaultMutableTreeNode(fn);
                    buildNodes(category, files[i]);
                    top.add(category);
                }
            }
        }
    }

    /**
     * @param top
     * @param directory
     */
    private void buildNodes(DefaultMutableTreeNode top, File directory) {
        File[] files;
        DefaultMutableTreeNode category;
        if (directory.isDirectory()) {
            files = directory.listFiles();
            if(files == null){
                return;
            }
            for(int i=0; i<files.length; i++){
                FileNode fn = new FileNode(files[i].getName(),files[i]);
                category = new DefaultMutableTreeNode(fn);
                category.setAllowsChildren(true);
                top.add(category);
                }
            }
        }

    /**
     * Allows the user to open folders that are deeper than one level in the tree. This class listens for a folder to be selected.
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
            DefaultMutableTreeNode category = null;
            FileNode fn = (FileNode) node.getUserObject();
            File file = fn.getFile();
            thisFrame.setFrameTitle(file.getAbsolutePath());
            File[] files = file.listFiles();
            if(files != null){
                for(int i=0; i<files.length; i++){
                    if(files[i].isDirectory()) {
                        FileNode fileNode = new FileNode(files[i].getName(), files[i]);
                        category = new DefaultMutableTreeNode(fileNode);
                        category.setAllowsChildren(true);
                        node.add(category);
                    }
                }
            }
        }
    }

    /**
     * Checks whether the FileNode is a directory. If so, sets the icon to show that it is a folder.
     */
    private static class MyTreeCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                if(node.getUserObject() instanceof FileNode) {
                    FileNode fn = (FileNode) node.getUserObject();
                    if (fn.isDirectory()) {
                        File[] files = fn.getFile().listFiles();
                        if(files == null){ //folder is empty
                            setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
                        } else{ //folder contains files
                            setIcon(UIManager.getIcon("FileChooser.upFolderIcon"));
                        }
                    }
                }
            }
            return this;
        }
    }
}
