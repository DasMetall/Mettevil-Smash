/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author Max Brauer
 */
public class ServerConnection extends ConnectionManager {

    private Thread listener;
    private boolean enableListener = true;
    private ServerSocket socket;
    private static long nextId = 0;

    public boolean connect(int port) {
        try {
            socket = new ServerSocket(port);
            socket.setSoTimeout(1000);
        }
        catch (Exception e) {
            ConnectionHandler.events.connectionFailed();
            return false;
        }
        
        listener = new Thread(() -> listener_run(),
                              "Server Connection Manager - Listener Thread");
        listener.start();
        return true;
    }

    private void listener_run() {
        while (enableListener) {
            Socket client = null;
            try  { client = socket.accept(); }
            catch (SocketTimeoutException e) { continue; }
            catch (IOException e) {
                ConnectionHandler.events.connectionFailed();
                close();
                return;
            }
            NetworkUser user = new NetworkUser(nextId++, client);
            Connection c = new Connection(user, super.getConnectionEvents());
            c.start();
            super.addConnection(c);
        }
    }

    @Override
    public void close() {
        enableListener = false;
        super.close();
        try {
            socket.close();
        }
        catch (IOException ex) {
        }
    }
}
