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

    public File getFile(){
        return file;
    }

    public boolean isDirectory(){
        return file.isDirectory();
    }

    @Override
    public String toString(){
        //file is a drive, getName() returns null
        if(file.getName().equals("")){
            return file.getPath();
        }
        return file.getName();
    }
}
