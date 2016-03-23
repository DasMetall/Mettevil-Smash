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
public interface ConnectionHandlerEvent {
    void connectionAdded(long userId);
    
    void connectionClosed(long userId);
    
    void connectionFailed();
}
