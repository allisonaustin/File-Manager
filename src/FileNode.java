import java.io.*;

public class FileNode {
    File file;
    String fileName;
    public FileNode( String fileName ){
        file = new File(fileName);
    }

    public FileNode( String name, File f ){
        fileName = name;
        file = f;
    }

    /**
     * @return file
     */
    public File getFile(){
        return file;
    }

    /**
     * @return boolean t/f if the file is a directory
     */
    public boolean isDirectory(){
        return file.isDirectory();
    }

    /**
     * Overridden method for returning the name of the file.
     * If the file name is empty, it returns the path of the file.
     * @return
     */
    @Override
    public String toString(){
        //file is a drive, getName() returns null
        if(file.getName().equals("")){
            return file.getPath();
        }
        return file.getName();
    }
}
