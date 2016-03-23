/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.test;

import Network.ConnectionHandlerEvent;

/**
 *
 * @author Max Brauer
 */
public class ConnectionHandlerEventImplementation implements ConnectionHandlerEvent{

    @Override
    public void connectionAdded(long userId) {
        System.out.println("Connection added: Id="+userId);
    }

    @Override
    public void connectionClosed(long userId) {
        System.out.println("Connetion closed: Id="+userId);
    }

    @Override
    public void connectionFailed() {
        System.out.println("Connection failed");
    }
    
}
