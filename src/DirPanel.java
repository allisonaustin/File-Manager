import javax.swing.*;

public class DirPanel extends JPanel {
    private JScrollPane scrollPane;
    private JTree dirTree;

    public DirPanel(){
        scrollPane = new JScrollPane();
        dirTree = new JTree();
        scrollPane.setViewportView(dirTree);
        add(scrollPane);
    }
}
