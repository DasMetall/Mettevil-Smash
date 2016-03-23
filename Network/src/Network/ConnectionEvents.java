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
public interface ConnectionEvents {
    void disconnection(Connection sender);
    
    void packageReceived(Connection sender, byte[] data);
}
