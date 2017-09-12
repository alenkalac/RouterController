package com.akproductions.routercontroller2;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Alen Kalac on 08/09/2017.
 */

public class Telnet {

    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;

    public Telnet(String host, String username, String password ) {
        try {
            this.socket = new Socket(host, 23);
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            pw = new PrintWriter(this.socket.getOutputStream(), true);
            System.out.println(readUntil("Login:"));
            write(username);
            System.out.println(readUntil("Password:"));
            write(password);
            //readAll();

        } catch (IOException e) {
            Log.d("TELNET EXCEPTION", "BAD STUFF HAPPENED MAN");
        }
    }

    private String readAll() throws IOException {
        StringBuilder sb = new StringBuilder();
        int c = br.read();
        while(c != -1) {
            if(c > 300)  {
                c = br.read();
                continue;
            }
            sb.append((char)c);
            c = br.read();
        }

        return sb.toString();
    }

    public String sendCommand(String command) {
        try {
            write(command);
            return readUntil(">");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) br.read();
            while (true) {
                if(ch > 300) {
                    ch = (char) br.read();
                    continue;
                }
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        //System.out.println("Found " + pattern);
                        return "Found " +pattern + " - " + sb.toString();
                    }
                }
                ch = (char) br.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void write(String value) {
        try {
            pw.println(value);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {}
    }

}
