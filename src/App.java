import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

class App extends JFrame{
    JPanel panel, topPanel;
    JFrame jframe = this;
    JMenuBar menuBar;
    JToolBar toolBar, statusBar;
    JButton details, simple;
    static JDesktopPane desktop;
    FileManagerFrame frame1, intframe;

    public App() {
        topPanel = new JPanel();
        panel = new JPanel();
        menuBar = new JMenuBar();
        toolBar = new JToolBar();
        statusBar = new JToolBar();
        desktop = new JDesktopPane();
        frame1 = new FileManagerFrame(this);
        frame1.setSize(750, 500);
    }

    // Our "main" method
    public void go(){
        this.setTitle("CECS 277 File Manager");
        this.setSize(950, 700);
        this.setLocationRelativeTo(null);
        panel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());

        buildMenu();
        topPanel.add(menuBar, BorderLayout.NORTH);

        desktop.add(frame1);
        panel.add(desktop, BorderLayout.CENTER);

        buildToolbar();
        topPanel.add(toolBar, BorderLayout.SOUTH);

        buildStatusBar();
        panel.add(statusBar, BorderLayout.SOUTH);
        panel.add(topPanel, BorderLayout.NORTH);

        this.add(panel);
        frame1.setVisible(true);
        //frame1.requestFocusInWindow();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Creates a menu bar for the topPanel of the GUI
     * Components: File, Tree, Window, Help
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
        JMenuItem help = new JMenuItem("Help ");
        JMenuItem about = new JMenuItem("About");

        // Creating actions for our menu items
        rename.addActionListener(new FileActionListener());
        copy.addActionListener(new FileActionListener());
        delete.addActionListener(new FileActionListener());
        run.addActionListener(new FileActionListener());
        exit.addActionListener(new FileActionListener());
        expand_branch.addActionListener(new TreeActionListener());
        collapse_branch.addActionListener(new TreeActionListener());
        nw.addActionListener(new WindowActionListener());
        cascade.addActionListener(new CascadeActionListener());
        help.addActionListener(new HelpActionListener());
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
     * Creates a toolbar below the menu bar.
     * Components: current drive, details, simple
     */
    JComboBox drives;
    private void buildToolbar(){
        details = new JButton("Details");
        simple = new JButton("Simple");
        details.addActionListener(new DetailsActionListener());
        simple.addActionListener(new DetailsActionListener());
        drives = new JComboBox();
        drives.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedDrive = drives.getSelectedItem().toString().substring(0, 3);
                String currDrive = selectedDrive;
                selectedDrive += "\\";
                FileManagerFrame active = (FileManagerFrame) desktop.getSelectedFrame();
                if(active == null){
                    return;
                }
                active.setFrameTitle(currDrive);
                //updating the tree to display the files of the new drive
                active.dirPanel.setRootFile(selectedDrive);
                active.dirPanel.setTree();
                updateStatusBar(currDrive);
            }
        });
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File[] paths = getDrives();
        for(File drive : paths){
            drives.addItem(drive + " " + fsv.getSystemDisplayName(drive));
        }
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new FlowLayout());
        toolPanel.add(drives);
        toolPanel.add(details);
        toolPanel.add(simple);
        toolBar.add(toolPanel);
        toolBar.setFloatable(false);
    }

    /**
     * Gets a list of drives on the computer.
     * @return list of files representing the drives
     */
    public static File[] getDrives(){
        File[] paths = File.listRoots();
        return paths;
    }

    /**
     * Creates a status bar at the bottom of the external frame. Gives information on system specifications.
     * Components: Drive, free space, used space, total space
     */
    static JLabel currentDrive, freeSpc, usedSpc, totalSpc;
    private void buildStatusBar(){
        File drive = getDrives()[0];
        currentDrive = new JLabel("Current Drive: " + drive + "    ");
        String freeSpace = toString(drive.getFreeSpace());
        String usedSpace = toString(drive.getTotalSpace() - drive.getFreeSpace());
        String totalSpace = toString(drive.getTotalSpace());
        freeSpc = new JLabel("Free Space: " + freeSpace + "    ");
        usedSpc = new JLabel("Used Space: " + usedSpace + "    ");
        totalSpc = new JLabel("Total Space: " + totalSpace + "    ");
        statusBar.add(currentDrive);
        statusBar.add(freeSpc);
        statusBar.add(usedSpc);
        statusBar.add(totalSpc);
    }

    /**
     * Updates the text fields of the statusBar's labels when a new drive is selected
     * @param currDrive
     */
    public static void updateStatusBar(String currDrive){
        File newDrive = new File(currDrive);
        String freeSpace = toString(newDrive.getFreeSpace());
        String usedSpace = toString(newDrive.getTotalSpace() - newDrive.getFreeSpace());
        String totalSpace = toString(newDrive.getTotalSpace());
        currentDrive.setText("Current Drive: " + newDrive + "    ");
        freeSpc.setText("Free Space: " + freeSpace + "    ");
        usedSpc.setText("Used Space: " + usedSpace + "    ");
        totalSpc.setText("Total Space: " + totalSpace + "    ");
    }

    // Constructing new classes that implement ActionListener to give actions to our menu items.
    public static class FileActionListener implements ActionListener{
        /**
         * Creates actions for our menu items in our File menu.
         * @param e is the action event
         */
        @Override
        public void actionPerformed(ActionEvent e){
            FileManagerFrame active = (FileManagerFrame) desktop.getSelectedFrame();
            if(e.getActionCommand().equals("Exit")){
                System.exit(0);
            } else if(e.getActionCommand().equals("Rename")){
                RenameFileDialog rename_dlg = new RenameFileDialog(null,true);
                rename_dlg.setVisible(true);
            } else if(e.getActionCommand().equals("Copy")){
                //todo
                RenameFileDialog copy_dlg = new RenameFileDialog(null, true);
                copy_dlg.setVisible(true);
            } else if(e.getActionCommand().equals("Delete")){
                int row = active.filePanel.getSelectedRow();
                FilePanel fp = active.filePanel;
                DeleteFileDialog delete_dlg = new DeleteFileDialog(null, true, fp, row);
                delete_dlg.setVisible(true);
            } else if(e.getActionCommand().equals("Run")){
                int row = active.filePanel.getSelectedRow();
                FilePanel fp = active.filePanel;
                File toRun = fp.getFilesInList().get(row);
                fp.runFile(toRun);
              //from FilePanel
            } else if(e.getActionCommand().equals("Paste")){
                //todo
                System.exit(0);
            }
        }
    }

    private static class TreeActionListener implements ActionListener{
        /**
         * Creates actions for our menu items in our Tree menu.
         * @param e is the action event
         */
        @Override
        public void actionPerformed(ActionEvent e){
            FileManagerFrame active = (FileManagerFrame) desktop.getSelectedFrame();
            if(active==null){
                return;
            }
            JTree temp = active.dirPanel.getDirTree();
            int row = temp.getMinSelectionRow();
            if(e.getActionCommand().equals("Expand Branch")){
                temp.expandRow(row);
            } else if(e.getActionCommand().equals("Collapse Branch")){
                temp.collapseRow(row);
            }
        }
    }

    private class WindowActionListener implements ActionListener{
        /**
         * Creates a new FileManagerFrame intframe and adds it to the desktop.
         * @param e is the action event
         */
        @Override
        public void actionPerformed(ActionEvent e){
            intframe = new FileManagerFrame(jframe);
            intframe.setSize(750,500);
            desktop.add(intframe);
            intframe.setVisible(true);
            }
        }


    private class HelpActionListener implements ActionListener{
        /**
         * Creates actions for our menu items in our Help menu.
         * @param e is the action event
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getActionCommand().equals("About")){
                AboutDialog dlg = new AboutDialog(null, true);
                dlg.setVisible(true);
            } else if(e.getActionCommand().equals("Help ")){
                System.exit(0);
            }
        }
    }

    private static class DetailsActionListener implements ActionListener {
        /**
         * Shows or hides details in the filePanel (the date the file was last modified and the size fo the file.)
         * @param e is the action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            FileManagerFrame active = (FileManagerFrame) desktop.getSelectedFrame();
            if(active==null){
                return;
            }
            if(e.getActionCommand().equals("Details")){
                if(!active.filePanel.getShowDetails()){
                    active.filePanel.setShowDetails(true);
                }
            }
            if(e.getActionCommand().equals("Simple")){
                if(active.filePanel.getShowDetails()){
                    active.filePanel.setShowDetails(false);
                }
            }
        }
    }

    class CascadeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int x = 0;
            int y = 0;
            JInternalFrame[] frames = desktop.getAllFrames();
            int count = frames.length;
            x = 30 * (count-1);
            y = 30 * (count-1);
            for(JInternalFrame frame : frames){
                frame.setLocation(x,y);
                x-=30;
                y-=30;
            }
        }
    }


    /**
     * Converts information returned from drive information (bytes) into a string that better describes the size of the space.
     * @param bytes
     * @return
     */
    private static String toString(long bytes){
        long kilobyte = 1024;
        long megabyte = kilobyte * 1024;
        long gigabyte = megabyte * 1024;
        long terabyte = gigabyte * 1024;
        if((bytes >= 0) && (bytes < kilobyte)){
            return bytes + " B";
        }
        if((bytes >= kilobyte) && (bytes < megabyte)){
            return (bytes/kilobyte) + " KB";
        }
        if((bytes >= megabyte) && (bytes < gigabyte)){
            return (bytes/megabyte) + " MB";
        }
        if((bytes >= gigabyte) && (bytes < terabyte)){
            return (bytes/gigabyte) + " GB";
        }
        if(bytes >= terabyte){
            return (bytes/terabyte) + " TB";
        } else{
            return bytes + " Bytes";
        }
    }
}
