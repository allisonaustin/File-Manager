import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.Format;
import java.util.ArrayList;

public class FilePanel extends JPanel {
    JList myList = new JList();
    JScrollPane scrollPane = new JScrollPane();
    DefaultListModel listModel = new DefaultListModel();
    final DragSource ds = new DragSource();
    public boolean showDetails;
    private FileManagerFrame thisFrame;
    ArrayList<File> filesInList;

    public FilePanel(FileManagerFrame frame){
        thisFrame = frame;
        myList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, selected, cellHasFocus);
                if( value instanceof String ) {
                    if (filesInList.get(index).isDirectory()) {
                        setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
                    } else if (filesInList.get(index).isFile()) {
                        setIcon(UIManager.getIcon("FileView.fileIcon"));
                    }
                    String name = (String) value;
                    setText(name);
                }
                return this;
            }
        });
        myList.addMouseListener(new ListMouseListener());
        myList.setFont(new Font("Lucida Console", Font.PLAIN, 12));
        filesInList = new ArrayList();
        this.setDropTarget(new MyDropTarget());
        myList.setDragEnabled(true);

        setLayout(new BorderLayout());

        myList.setModel(listModel);
        showDetails = false;
        File top = App.getDrives()[0];
        displayFiles(top);
        scrollPane.setViewportView(myList);
        add(scrollPane, BorderLayout.CENTER);
    }

    public ArrayList<File> getFilesInList(){
        return filesInList;
    }

    public int getSelectedRow(){
        return myList.getMaxSelectionIndex();
    }

    public void displayFiles(File dir) {
        File[] files;
        files = dir.listFiles();
        if(files == null){
            return;
        }

        listModel.clear();
        myList.removeAll();
        filesInList.clear();
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()){
                listModel.addElement(files[i].getName());
                filesInList.add(files[i]);
            }
        }
        for(int j=0; j < files.length; j++){
            if(!files[j].isDirectory()){
                if(this.showDetails){
                    String fileStats = formatFileStats(files[j]);
                    listModel.addElement(fileStats);
                } else{
                    listModel.addElement(files[j].getName());
                }
                filesInList.add(files[j]);
            }
        }
        myList.setModel(listModel);
    }

    /**
     *
     * @param f is the file to be executed
     */
    public void runFile(File f) {
        Desktop desktop = Desktop.getDesktop();
        if(f.exists() && f.isFile())
            try {
                desktop.open(f);
            } catch (IOException e){
                e.printStackTrace();
            }
    }

    public void deleteFile(int index){
        listModel.remove(index);
        filesInList.remove(index);
    }

    /**
     *
     * @param f
     * @return
     */
    public String formatFileStats( File f ){
        int length = 30;
        String fileName = f.getName();
        if(f.getName().length() > length){
            fileName = f.getName().substring(0,30) + "...";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        DecimalFormat df = new DecimalFormat("#,###");
        return String.format("%-35s %20s %20s", fileName, sdf.format(f.lastModified()), df.format(f.length()));
    }


    /**
     *
     */
    private class FilePopMenu extends JPopupMenu {
        JMenuItem rename = new JMenuItem("Rename");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem delete = new JMenuItem("Delete");

        public FilePopMenu() {
            rename.addActionListener(new App.FileActionListener());
            copy.addActionListener(new App.FileActionListener());
            paste.addActionListener(new App.FileActionListener());
            delete.addActionListener(new App.FileActionListener());
            add(rename);
            add(copy);
            add(paste);
            this.addSeparator();
            add(delete);
        }
    }


    /**
     * Allows the user to drag and drop (DnD) files within the filePanel using a DropTarget
     */
    class MyDropTarget extends DropTarget {

        public void drop(DropTargetDropEvent e){
            e.acceptDrop(DnDConstants.ACTION_COPY);
            ArrayList result = new ArrayList();
            // looks at type of file being dragged in
            // from outside: String flavor
            // from inside: File flavor
            if (e.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String temp = null;
                try {
                    temp = (String) e.getTransferable().getTransferData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException | IOException unsupportedFlavorException) {
                    unsupportedFlavorException.printStackTrace();
                }
                String[] next = temp.split("\\n");
                for (int i = 0; i < next.length; i++) {
                    listModel.addElement(next[i]);
                }
            } else{
                try {
                    result = (ArrayList) e.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                } catch (UnsupportedFlavorException | IOException unsupportedFlavorException) {
                    unsupportedFlavorException.printStackTrace();
                }
                for(Object o : result){
                    System.out.println(o.toString());
                    listModel.addElement(o.toString());
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public boolean getShowDetails( ){
        return showDetails;
    }

    /**
     *
     * @param b
     */
    public void setShowDetails(boolean b){
        showDetails = b;
        File top = getFilesInList().get(0);
        this.displayFiles(top.getParentFile());
    }

    /**
     *
     */
    private class ListMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            File f = filesInList.get(getSelectedRow());
            if(e.getClickCount() == 2) {
                if(f.isDirectory()){
                    displayFiles(f);
                    thisFrame.setFrameTitle(f.getAbsolutePath());
                } else{
                    runFile(f);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if( SwingUtilities.isRightMouseButton(e)) {
                JList list = (JList) e.getSource();
                int row = list.locationToIndex(e.getPoint());
                myList.setSelectedIndex(row);
                FilePopMenu menu = new FilePopMenu();
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e){
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
