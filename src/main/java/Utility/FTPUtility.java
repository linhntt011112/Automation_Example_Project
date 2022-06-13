package Utility;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

import static org.junit.Assert.assertTrue;

public class FTPUtility {
    // Creating FTP Client instance
    FTPClient ftp = null;

    /**
     * Constructor to connect to the FTP server
     * @param host
     * @param port
     * @param username
     * @param password
     * @throws Exception
     */
    public void FTPFunctions(String host, int port, String username, String password) throws Exception {
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host, port);
        System.out.println("FTP URL is: " + ftp.getDefaultPort());
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(username, password);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }

    /**
     * Method to upload the file on the FTP server
     * @param localFileFullName
     * @param fileName
     * @param hostDir
     */
    public void uploadFTPFile(String localFileFullName, String fileName, String hostDir) {
        try {
                InputStream input = new FileInputStream(new File(localFileFullName));

                this.ftp.storeFile(hostDir + fileName, input);
            } catch (Exception e){}
    }

    /**
     * Download the FTP file from the FTP server
     * @param source
     * @param destination
     */
    public void downloadFTPFile(String source, String destination) {
        try (FileOutputStream fos = new FileOutputStream(destination)) {
            this.ftp.retrieveFile(source, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * List the files in a specified directory on the FTP
     * @param directory
     * @param fileName
     * @return
     * @throws IOException
     */
    public boolean listFTPFiles(String directory, String fileName) throws IOException {
        // lists files and directories in the current working directory
        boolean verificationFileName = false;
        FTPFile[] files = ftp.listFiles(directory);
        for (FTPFile file : files) {
            String details = file.getName();
            System.out.println(details);
            if (details.equals(fileName)) {
                System.out.println("Correct Filename");
                verificationFileName = details.equals(fileName);
                assertTrue("Verification Failed: The filename is not updated at the CDN end.", details.equals(fileName));
            }
        }
        return verificationFileName;
    }

    /**
     * disconnect the connection to FTP
     */
    public void disconnect() {
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException e) {
                // do nothing as file is already saved to server
            }
        }
    }
}
