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
public class NetworkUser {
    private Socket socket;
    private long id;
    private long lastMessageTime;
    private long lastPing;
    
    public NetworkUser(long id, Socket socket) {
        this.id = id;
        this.socket = socket;
        this.lastMessageTime = System.currentTimeMillis();
        this.lastPing = 0;
    }

    /**
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * @param socket the socket to set
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the lastMessageTime
     */
    public long getLastMessageTime() {
        return lastMessageTime;
    }
    
    public void notifyLastMessageTime() {
        lastMessageTime = System.currentTimeMillis();
    }

    /**
     * @return the lastPing
     */
    public long getLastPing() {
        return lastPing;
    }

    /**
     * @param lastPing the lastPing to set
     */
    public void setLastPing(long lastPing) {
        this.lastPing = lastPing;
    }
}
