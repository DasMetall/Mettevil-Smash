/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author Max Brauer
 */
public class Connection extends Thread {

    private static final int MaxErrorTimes = 1;
    private static final int MaxQueueCounts = 5;

    private NetworkUser user;
    private boolean isAlive;
    private InputStream input;
    private OutputStream output;
    private ArrayList<byte[]> sendBuffer;
    private byte[] buffer4 = new byte[4];
    private int errorTimes = 0;
    private int delayTime = 10;

    private ConnectionEvents events;

    public Connection(NetworkUser user, ConnectionEvents events) {
        this.user = user;
        this.isAlive = true;
        this.sendBuffer = new ArrayList<>();
        this.events = events;
    }

    public synchronized void sendData(byte[] data) {
        sendBuffer.add(data);
    }

    public synchronized int getSendBufferLength() {
        return sendBuffer.size();
    }

    public synchronized byte[] getSendData() {
        if (sendBuffer.size() == 0)
            return null;
        byte[] data = sendBuffer.get(0);
        sendBuffer.remove(0);
        return data;
    }

    public void close() {
        isAlive = false;
    }

    @Override
    public void run() {
        //Create input and output streams
        try {
            this.input = user.getSocket().getInputStream();
        }
        catch (IOException ex) {
            broadcastDisconnection();
            try {
                user.getSocket().close();
            }
            catch (IOException ex1) {
            }
            return;
        }
        try {
            this.output = user.getSocket().getOutputStream();
        }
        catch (IOException ex) {
            try {
                this.input.close();
            }
            catch (IOException ex1) {
            }
            broadcastDisconnection();
            try {
                user.getSocket().close();
            }
            catch (IOException ex1) {
            }
            return;
        }
        //do message loop
        while (isIsAlive()) {
            //Read received Data
            try {
                for (int i = 0; i < MaxQueueCounts && input.available() > 0; ++i) {
                    //read data length
                    int readed = input.read(buffer4);
                    if (readed == -1) {
                        broadcastDisconnection();
                        break;
                    }
                    int length = Helper.byteArrayToInt(buffer4);
                    //read data
                    byte[] buffer = new byte[length];
                    readed = input.read(buffer);
                    if (readed == -1) {
                        broadcastDisconnection();
                        break;
                    }
                    //send data to system
                    sendReceivedPackage(buffer);
                }
            }
            catch (IOException ex) {
                if (!setError())
                    continue;
                else
                    break;
            }
            //Send Data
            try {
                for (int i = 0; i < MaxQueueCounts && getSendBufferLength() > 0; ++i) {

                    byte[] data = getSendData();
                    output.write(Helper.intToByteArray(data.length));
                    output.write(data);
                }
            }
            catch (IOException ex) {
                if (setError())
                    break;
                else continue;
            }
            //reset error level
            errorTimes = 0;
            //wait short time
            try {
                Thread.sleep(delayTime);
            }
            catch (InterruptedException ex) {
                break;
            }
        }
        //clean up
        try {
            output.close();
        }
        catch (IOException ex) {
        }
        try {
            input.close();
        }
        catch (IOException ex) {
        }
        try {
            getUser().getSocket().close();
        }
        catch (IOException ex) {
        }
    }

    private boolean setError() {
        errorTimes++;
        if (errorTimes >= MaxErrorTimes) {
            broadcastDisconnection();
            return true;
        }
        else
            return false;
    }

    private void broadcastDisconnection() {
        events.disconnection(this);
    }

    private void sendReceivedPackage(byte[] data) {
        events.packageReceived(this, data);
    }

    /**
     * @return the user
     */
    public NetworkUser getUser() {
        return user;
    }

    /**
     * @return the isAlive
     */
    public boolean isIsAlive() {
        return isAlive;
    }

    /**
     * @return the delayTime
     */
    public int getDelayTime() {
        return delayTime;
    }

    /**
     * @param delayTime the delayTime to set
     */
    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime <= 0 ? 1 : delayTime;
    }
}
