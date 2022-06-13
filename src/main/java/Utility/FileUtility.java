package Utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtility {

    /**
     * Create folder
     * @param folderPath
     */
    public static void createFolder(String folderPath) {
        try {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
                System.out.println("Folder is created successfully!");
            } else {
                System.out.println("Folder is NOT created!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create File
     * @param folderPath
     * @param fileName
     * @param extension
     */
    public static void createFile(String folderPath, String fileName, String extension) {
        try {
            File myFile = new File(folderPath + fileName + "." + extension);

            if (myFile.createNewFile()) System.out.println("File is created successfully!");
            else System.out.println("File is NOT created!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create new file and write to it
     * @param folderPath
     * @param fileName
     * @param extension
     * @param content
     */
    public static void createFileWithContent(String folderPath, String fileName, String extension, String content) {
        try {
            String path = folderPath + fileName + "." + extension;

            File file = new File(path);

            // if file doesn't exist, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            // write in file
            bw.write(content);
            // close connection
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Delete files with extension
     * @param directory
     * @param extensionWithDot
     */
    public static void deleteFilesWithExtension(String directory, String extensionWithDot) {
        try {
            File folder = new File(directory);
            File fList[] = folder.listFiles();

            for (File f : fList) {
                if (f.getName().endsWith(extensionWithDot)) {
                    f.delete();
                }
            }
            System.out.println("All files in " + directory + " with extension " + extensionWithDot + " are deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete files with matchingFileName
     * @param directory
     * @param matchingFileName
     */
    public static void deleteFilesContains(String directory, String matchingFileName) {
        try {
            File folder = new File(directory);
            File fList[] = folder.listFiles();

            for (File f : fList) {
                if (f.getName().contains(matchingFileName)) {
                    f.delete();
                }
            }
            System.out.println("All files in " + directory + " containing " + matchingFileName + " are deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete all files in a folder
     * @param folder
     */
    public static void deleteFiles(File folder) {
        try {
            File[] files = folder.listFiles();
            for (File file:files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    boolean del = file.delete();
                    System.out.println(fileName + " : got deleted ? " + del);
                } else if (file.isDirectory()) {
                    deleteFiles(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("xxx Deleting Folder Completed xxx");
        }
    }

    /**
     * Rename file
     * @param oldNameWithPath
     * @param newNameWithPath
     */
    public static void renameFile(String oldNameWithPath, String newNameWithPath) {
        try {
            Files.move(Paths.get(oldNameWithPath), Paths.get(newNameWithPath));
            System.out.println("File renamed as - " + newNameWithPath);
        } catch (Exception e) {
            FileUtility.file_Rename(oldNameWithPath, newNameWithPath);
            e.printStackTrace();
        }
    }

    /**
     * file_Rename (using java.io instead of java.nio which is cause exception in old JRE
     * @param oldFileFullPath
     * @param newFileFullPath
     */
    public static void file_Rename(String oldFileFullPath, String newFileFullPath){
        File oldName = new File(oldFileFullPath); // "D:/program.txt"
        File newName = new File(newFileFullPath); // "D:/java.txt"
        if (oldName.renameTo(newName)) {
            System.out.println("Rename successfully");
        } else {
            System.out.println("Rename failed");
        }
    }

    /**
     * Move file
     * @param sourcePath
     * @param destinationPath
     */
    public static void moveFile(String sourcePath, String destinationPath) {
        try {
            Files.move(Paths.get(sourcePath), Paths.get(destinationPath));
            System.out.println("File moved to - " + destinationPath);
        } catch (Exception e) {
            System.out.println("File NOT moved to - " + destinationPath);
            e.printStackTrace();
        }
    }
}
