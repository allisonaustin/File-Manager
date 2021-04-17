import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class FileManagerFrame extends JInternalFrame {
    JSplitPane splitPane;
    public FileManagerFrame(JFrame frame){
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new DirPanel(), new FilePanel());
        splitPane.setDividerLocation(250);
        this.setTitle("C:");
        this.getContentPane().add(splitPane);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setVisible(true);
    }
}
