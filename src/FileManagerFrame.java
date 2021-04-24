import java.awt.*;
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
    DirPanel dirPanel = new DirPanel();
    FilePanel filePanel = new FilePanel();
    public FileManagerFrame(JFrame frame) {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dirPanel, filePanel);
        this.setTitle("C:");
        this.getContentPane().add(splitPane);
        this.pack();
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        frame.add(this);
        this.setVisible(true);
    }
}
