import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FilePanel extends JPanel {
    JList myList = new JList();
    DefaultListModel listModel = new DefaultListModel();

    public FilePanel(){
        this.setDropTarget(new MyDropTarget());
        myList.setDragEnabled(true);
        setLayout(new BorderLayout());

        myList.setModel(listModel);
        add(myList, BorderLayout.CENTER);
    }

    /**
     *
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

    private void displayFiles(FileNode fileNode) {
        File[] files = fileNode.getFile().listFiles();
        if( files != null ) {
            for (File f : files) {
                listModel.addElement(f.getName());
            }
        }
    }
}
