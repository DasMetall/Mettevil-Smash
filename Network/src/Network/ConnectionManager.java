/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Max Brauer
 */
public class ConnectionManager {

    private ArrayList<Connection> connections;
    private ConnectionEvents connectionEvents;
    private Thread pingChecker;
    private boolean enablePingChecker = true;

    public ConnectionManager() {
        this.connections = new ArrayList<>();
        this.connectionEvents = new ConnectionEvents() {
            @Override
            public void disconnection(Connection sender) {
                removeConnection(sender);
                ConnectionHandler.events.connectionClosed(sender.getUser().
                        getId());
            }

            @Override
            public void packageReceived(Connection sender, byte[] data) {
                PrimaryMessage mes = new PrimaryMessage();
                mes.load(data);
                mes.setSenderId(sender.getUser().getId());
                sheduleMessage(mes);
            }
        };
        pingChecker = new Thread(() -> pingChecker_run(),
                                 "Connection Manager - Ping Checker Thread");
        pingChecker.start();
    }

    private void pingChecker_run() {
        while (enablePingChecker) {
            long start = System.currentTimeMillis();
            for (int i = 0; i<getConnectionsCount(); ++i) {
                Connection con = null;
                synchronized (this) {
                    if (i<connections.size())
                        con = connections.get(i);
                }
                if (con==null) break;
                PrimaryMessage ping = new PrimaryMessage();
                ping.setDataAsByteArray(Helper.longToByteArray(System.currentTimeMillis()));
                ping.setType(PrimaryMessageType.Ping);
                con.sendData(ping.save());
            }
            long sleep = (1000-System.currentTimeMillis()+start)%1000;
            if (sleep<=0) sleep = 1000;
            try {
                Thread.sleep(sleep);
            }
            catch (InterruptedException ex) {
            }
        }
    }
    
    public void close() {
        enablePingChecker = false;
        for (int i = 0; ;++i) {
            Connection con = null;
            synchronized (this) {
                if (i<connections.size())
                    con = connections.get(i);
                else break;
            }
            con.close();
        }
        connections.clear();
    }

    protected ConnectionEvents getConnectionEvents() {
        return connectionEvents;
    }

    public synchronized void addConnection(Connection c) {
        connections.add(c);
    }

    public synchronized Connection getConnection(long id) {
        for (int i = 0; i < connections.size(); ++i)
            if (connections.get(i).getUser().getId() == id)
                return connections.get(i);
        return null;
    }

    public synchronized boolean removeConnection(Connection c) {
        return connections.remove(c);
    }

    public synchronized boolean removeConnection(long id) {
        for (int i = 0; i < connections.size(); ++i)
            if (connections.get(i).getUser().getId() == id) {
                connections.remove(i);
                return true;
            }
        return false;
    }

    public synchronized boolean closeAndRemoveConnection(long id) {
        for (int i = 0; i < connections.size(); ++i)
            if (connections.get(i).getUser().getId() == id) {
                connections.get(i).close();
                connections.remove(i);
                return true;
            }
        return false;
    }
    
    public synchronized int getConnectionsCount() {
        return this.connections.size();
    }

    public void sendMessage(PrimaryMessage mes) {
        Connection con = getConnection(mes.getSenderId());
        if (con == null)
            return;
        con.sendData(mes.save());
    }

    private void sheduleMessage(PrimaryMessage mes) {
        switch (mes.getType()) {
            case None:
                break;
            case Ping:
                mes.setType(PrimaryMessageType.PingAnswer);
                sendMessage(mes);
                break;
            case PingAnswer:
                Connection con = getConnection(mes.getSenderId());
                if (con != null) {
                    con.getUser().setLastPing(System.currentTimeMillis()
                            - Helper.byteArrayToLong(mes.getDataAsByteArray()));
                    con.getUser().notifyLastMessageTime();
                }
                break;
            default:
                NetworkInput.networkInput.MessageReceived(mes);
                break;
        }
    }
}
