package test;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class JschTest {

    public  static void main(String args[]){
        JSch jSch = new JSch();
        try {

            Session session = jSch.getSession("pi", "192.168.1.75");
            session.setPassword("Easy2Guess!");
            //TODO : configure known hosts
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            //TODO : configure known hosts
            session.connect();

            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();;
            String pwd = sftp.pwd();
            System.out.println("PWD = "+pwd);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
