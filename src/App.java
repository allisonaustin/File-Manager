import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class App extends JFrame{
    JPanel panel, topPanel;
    JMenuBar menuBar;
    JToolBar toolBar, statusBar;
    JButton details, simple;
    JDesktopPane desktop;
    //FileManagerFrame myFrame, my2ndFrame;

    public App(){
        topPanel = new JPanel();
        panel = new JPanel();
        menuBar = new JMenuBar();
        toolBar = new JToolBar();
        statusBar = new JToolBar();
        desktop = new JDesktopPane();
        details = new JButton("Details");
        simple = new JButton("Simple");
        this.setSize(700, 600);
        this.setLocationRelativeTo(null);
    }

    public void go(){
        this.setTitle("CECS 277 File Manager");
        panel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        topPanel.add(menuBar, BorderLayout.NORTH);
        panel.add(topPanel, BorderLayout.NORTH);

        this.add(panel);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void buildMenu(){

    }

}
