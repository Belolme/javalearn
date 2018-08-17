package javax.main.io.local;

import java.io.File;
/**
 * Created by Billin on 2016/5/18.
 * File Examples
 */
public class FileExample {


    public static void  main(String[] args) {
        File path = new  File(".");

        String[] fileStrings = path.list((dir, name) -> name.length() > 5);
        for (String s : fileStrings)
            System.out.print (s);

        System.out.println();

        File[] files = path.listFiles((dir, name) -> name.length()>5);
        for (File f : files)
            System.out.print (f.getName());

    }

        private static void usage(){
            System.err.println(
                    "Usage:MakeDirectoriespath1...\n"+
                            "Createseach path\n"+
                            "Usage:MakeDirectories-dpath1...\n"+
                            "Deletes eachpath\n"+
                            "Usage:MakeDirectories-r path1path2\n"+
                            "Renamesfrompath1topath2");
            System.exit(1);
        }
        private static void fileData(File f) {
            System.out.println(
                    "Absolutepath: "+ f.getAbsolutePath()+
                            "\nCanread: "+ f.canRead() +
                            "\nCanwrite: "+ f.canWrite()+
                            "\ngetName: "+ f.getName()+
                            "\ngetParent: " +f.getParent()+
                            "\ngetPath: "+ f.getPath()+
                            "\nlength: "+ f.length()+
                            "\nlastModified: " +f.lastModified());
            if(f.isFile())
                System.out.println("It'safile");
            else if(f.isDirectory())
            System.out.println("It'sadirectory");
        }
}
