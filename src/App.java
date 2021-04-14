import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

class App extends JFrame{
    JPanel panel, topPanel;
    JMenuBar menuBar;
    JToolBar toolBar, statusBar;
    JButton details, simple;
    JDesktopPane desktop;
    FileManagerFrame frame1, frame2;

    public App() {
        topPanel = new JPanel();
        panel = new JPanel();
        menuBar = new JMenuBar();
        toolBar = new JToolBar();
        statusBar = new JToolBar();
        desktop = new JDesktopPane();
        details = new JButton("Details");
        simple = new JButton("Simple");
        frame1 = new FileManagerFrame(this);
        frame1.setSize(700, 500);
    }

    // Our "main" method
    public void go(){
        this.setTitle("CECS 277 File Manager");
        this.setSize(900, 700);
        this.setLocationRelativeTo(null);
        panel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());

        buildMenu();
        topPanel.add(menuBar, BorderLayout.NORTH);
        panel.add(topPanel, BorderLayout.NORTH);
        this.add(panel);
        frame1.setVisible(true);

        desktop.add(frame1);
        panel.add(desktop, BorderLayout.CENTER);

        buildToolbar();
        topPanel.add(toolBar, BorderLayout.SOUTH);

        buildStatusBar();
        panel.add(statusBar, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /** Creates a menu bar for the topPanel of the GUI
     *
     */
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

        // Creating actions for our menu items
        rename.addActionListener(new FileActionListener());
        exit.addActionListener(new FileActionListener());
        about.addActionListener(new HelpActionListener());

        // Adding menu items to our menus
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

        // Adding menus to our menu bar
        menuBar.add(fileMenu);
        menuBar.add(treeMenu);
        menuBar.add(windowMenu);
        menuBar.add(helpMenu);
    }

    /**
     *
     */
    private void buildToolbar(){
        toolBar.add(details);
        toolBar.add(simple);
    }

    /**
     *
     */
    private void buildStatusBar(){

    }

    // Constructing new classes that implement ActionListener to give actions to our menu items.
    private static class FileActionListener implements ActionListener{
        /** Creates actions for our menu items in our File menu.
         * @param e is the action event
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getActionCommand().equals("Exit")){
                System.exit(0);
            } else if(e.getActionCommand().equals("Rename")){
                RenameFileDialog rename_dlg = new RenameFileDialog(null,true);
                rename_dlg.setVisible(true);
            }
        }
    }

    private static class HelpActionListener implements ActionListener{
        /** Creates actions for our menu items in our Help menu.
         * @param e is the action event
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getActionCommand().equals("About")){
                AboutDialog dlg = new AboutDialog(null, true);
                dlg.setVisible(true);
            }
        }
    }
}
