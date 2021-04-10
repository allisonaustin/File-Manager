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
    //Frame myFrame, my2ndFrame;

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
        this.setTitle("CECS 277 File Manager");
        panel.setLayout(new BorderLayout());
    }

    public void go(){
        buildMenu();
        this.add(panel);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void buildMenu(){
        JMenu fileMenu, treeMenu, windowMenu, helpMenu;
        fileMenu = new JMenu("File");
        treeMenu = new JMenu("Tree");
        windowMenu = new JMenu("Window");
        helpMenu = new JMenu("Help");

        JMenuItem rename = new JMenuItem("Rename");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem run = new JMenuItem("Run");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem expand_branch = new JMenuItem("Expand Branch");
        JMenuItem collapse_branch = new JMenuItem("Collapse Branch");
        JMenuItem nw = new JMenuItem("New");
        JMenuItem cascade = new JMenuItem("Cascade");
        JMenuItem help = new JMenuItem("Help");
        JMenuItem about = new JMenuItem("About");

        exit.addActionListener(new FileActionListener());
        about.addActionListener(new HelpActionListener());

        fileMenu.add(rename);
        fileMenu.add(copy);
        fileMenu.add(delete);
        fileMenu.add(run);
        fileMenu.add(exit);
        treeMenu.add(expand_branch);
        treeMenu.add(collapse_branch);
        windowMenu.add(nw);
        windowMenu.add(cascade);
        helpMenu.add(help);
        helpMenu.add(about);


        menuBar.add(fileMenu);
        menuBar.add(treeMenu);
        menuBar.add(windowMenu);
        menuBar.add(helpMenu);
        panel.add(menuBar, BorderLayout.NORTH);
    }

    private static class FileActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getActionCommand().equals("Exit")){
                System.exit(0);
            }
        }
    }

    private static class HelpActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getActionCommand().equals("About")){
                AboutDialog dlg = new AboutDialog();
                dlg.setVisible(true);
            }
        }
    }

}
