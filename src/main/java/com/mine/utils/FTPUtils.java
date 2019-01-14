package com.mine.utils;

import lombok.Data;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author 杜晓鹏
 * @create 2019-01-10 09:44
 */
@Data
public class FTPUtils {
    /**
     * ip地址
     */
    private static final String FTPIP = PropertiesUtils.getValue("ftp.server.ip");
    /**
     * 用户名
     */
    private static final String FTPUSER = PropertiesUtils.getValue("ftp.server.user");
    /**
     * 密码
     */
    private static final String FTPPASSWORD = PropertiesUtils.getValue("ftp.server.password");

    private String ftpIp;
    private String ftpUser;
    private String ftpPass;
    private Integer ftpPort; //端口号

    public  FTPUtils(String ftpIp, String ftpUser, String ftpPass, Integer ftpPort) {
        this.ftpIp = ftpIp;
        this.ftpUser = ftpUser;
        this.ftpPass = ftpPass;
        this.ftpPort = ftpPort;
    }

    /**
     * 图片上传到ftp
     */
    public static boolean uploadFile(List<File> files) {
        FTPUtils ftpUtils = new FTPUtils(FTPIP, FTPUSER, FTPPASSWORD, 21);
        System.out.println("开始连接ftp服务器...");
        //默认是  ftpfile目录下   此时 remotePath为img   那么目录就是 /ftpfile/img下面
        ftpUtils.uploadFile("img", files);

        return false;
    }

    public boolean uploadFile(String remotePath, List<File> files) {
        FileInputStream fileInputStream = null;
        //连接ftp服务器   连接时 用到ftpClient
        if (connectFTPServer(ftpIp, ftpUser, ftpPass)) {
            try {
                //设置上传的目录
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(4096);//设置每次读取文件流时缓存数组的大小
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();//打开被动传输模式

                for (File file : files){
                    fileInputStream = new FileInputStream(file);
                    ftpClient.storeFile(file.getName(),fileInputStream);
                }
                System.out.println("======图片上传成功======");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("图片上传失败...");
            }finally {
                try {
                    fileInputStream.close();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return false;
    }

    FTPClient ftpClient = null;

    public boolean connectFTPServer(String ip, String user, String password) {

        ftpClient = new FTPClient();

        try {
            ftpClient.connect(ip);
            return ftpClient.login(user, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
