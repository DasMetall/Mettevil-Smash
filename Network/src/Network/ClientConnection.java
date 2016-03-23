/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.net.Socket;

/**
 *
 * @author Max Brauer
 */
public class ClientConnection extends ConnectionManager {
    public boolean connect(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            NetworkUser user = new NetworkUser(0, socket);
            Connection c = new Connection(user, super.getConnectionEvents());
            c.start();
            super.addConnection(c);
        }
        catch (Exception e) {
            ConnectionHandler.events.connectionFailed();
            return false;
        }
        return true;
    }
}
