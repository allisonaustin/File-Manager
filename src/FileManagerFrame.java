import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class FileManagerFrame extends JInternalFrame {
    JSplitPane splitPane;
    DirPanel dirPanel;
    FilePanel filePanel;
    static String frameTitle = "C:/";
    static String currentSelected;
    static int lastSelectedRow;

    public FileManagerFrame(JFrame frame) {
        filePanel = new FilePanel(this);
        dirPanel = new DirPanel(filePanel, this);
        filePanel.setDirPanel(dirPanel);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dirPanel, filePanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(250);
        splitPane.setSize(500,500);
        this.getContentPane().add(splitPane);

        setTitle(frameTitle);
        this.setSize(500,500);
        this.setResizable(true);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.toFront();
        this.addInternalFrameListener(new MyFocusListener());
        this.show();
        this.pack();
        this.requestFocusInWindow();
    }

    public void setFrameTitle(String ft){
        frameTitle = ft;
        setTitle(frameTitle);
        this.repaint();
    }

    class MyFocusListener implements InternalFrameListener {

        @Override
        public void internalFrameOpened(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameClosing(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameClosed(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameIconified(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameDeiconified(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameActivated(InternalFrameEvent e) {
            App.updateStatusBar(dirPanel.getRootFile());
        }

        @Override
        public void internalFrameDeactivated(InternalFrameEvent e) {
        }
    }
}
