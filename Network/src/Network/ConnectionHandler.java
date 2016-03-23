/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

/**
 *
 * @author Max Brauer
 */
public class ConnectionHandler {

    static ConnectionManager connectionManager;

    public static ConnectionHandlerEvent events;

    public static void initAsClient(String ip, int port) {
        close();
        ClientConnection client = new ClientConnection();
        if (!client.connect(ip, port))
            return;
        connectionManager = client;
    }

    public static void initAsServer(int port) {
        close();
        ServerConnection client = new ServerConnection();
        if (!client.connect(port))
            return;
        connectionManager = client;
    }

    public static void close() {
        if (connectionManager == null)
            return;
        connectionManager.close();
        connectionManager = null;
    }

    public static boolean isConnected() {
        return connectionManager != null;
    }
    
    public static ConnectionManager getConnectionManager() {
        return connectionManager;
    }
}
