package org.afeka.fi.backend.clients;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

public class SshClient {
    final int PORT=22;

    String host;
    String username;
    String password;
    String privateKeyPath;
    Session session;

    public void SshClient(String host,String userName,String password){
        this.host=host;
        this.password=password;
        this.username=userName;
    }

    public void SshClient(String host,String userName,String password, String privateKeyPath){
        this.host=host;
        this.password=password;
        this.username=userName;
        this.privateKeyPath=privateKeyPath;
    }

    public void connect() throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(username, host, PORT);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
    }

    public void disconnect(){
        session.disconnect();
    }

    public String sendCommand(String command) throws JSchException, IOException {
        StringBuilder outputBuffer = new StringBuilder();
        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(command);
        InputStream commandOutput = channel.getInputStream();
        channel.connect();
        int readByte = commandOutput.read();
        while(readByte != 0xffffffff) {
            outputBuffer.append((char)readByte);
            readByte = commandOutput.read();
        }
        channel.disconnect();
        return outputBuffer.toString();
    }
}
