import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FilePanel extends JPanel {
    JList myList = new JList();
    JScrollPane scrollPane = new JScrollPane();
    DefaultListModel listModel = new DefaultListModel();

    public FilePanel(){
        this.setDropTarget(new MyDropTarget());
        myList.setDragEnabled(true);
        myList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, selected, cellHasFocus);
                if( value instanceof String){
                    String file = (String) value;
                    setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
                    setText(file);
                }
                return this;
            }
        });
        myList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //execute files here
            }
        });
        myList.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e){
                if(e.isPopupTrigger()) {
                    doPop(e);
                }
            }
            private void doPop(MouseEvent e) {
                FilePopMenu menu = new FilePopMenu();
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        setLayout(new BorderLayout());

        myList.setModel(listModel);
        File top = App.getDrives()[0];
        displayFiles(top);
        scrollPane.setViewportView(myList);
        add(scrollPane, BorderLayout.CENTER);
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
                } catch (UnsupportedFlavorException unsupportedFlavorException) {
                    unsupportedFlavorException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                String[] next = temp.split("\\n");
                for (int i = 0; i < next.length; i++) {
                    listModel.addElement(next[i]);
                }
            } else{
                try {
                    result = (ArrayList) e.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                } catch (UnsupportedFlavorException unsupportedFlavorException) {
                    unsupportedFlavorException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                for(Object o : result){
                        System.out.println(o.toString());
                        listModel.addElement(o.toString());
                    }
                }
            }
        }

    public void displayFiles(File dir) {
        File[] files;

        files = dir.listFiles();
        if(files == null){
            return;
        }
        listModel.clear();
        myList.removeAll();
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()){
                listModel.addElement(files[i].getName());
            }
        }
        for(int j=0; j < files.length; j++){
            if(!files[j].isDirectory()){
                listModel.addElement(files[j].getName());
            }
        }
        myList.setModel(listModel);
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
            add(rename);
            add(copy);
            add(paste);
            this.addSeparator();
            add(delete);
            addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent me) {
                    if (me.isPopupTrigger())
                        show(me.getComponent(), me.getX(), me.getY());
                }
            });
        }
    }



    public boolean getShowDetails( ){
        return false;
    }

    public static void setShowDetails(boolean b){

    }

}
