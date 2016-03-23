/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.test;

import Network.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Max Brauer
 */
public class TestServer {
    public static void main(String[] args) {
        NetworkInput.networkInput = new NetworkInputImplementation();
        
        ConnectionHandler.events = new ConnectionHandlerEventImplementation();
        
        ConnectionHandler.initAsServer(50102);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (!in.readLine().equals("exit"));
        }
        catch (IOException ex) {
            Logger.getLogger(TestServer.class.getName()).log(Level.SEVERE, null,
                                                             ex);
        }
        try {
            in.close();
        }
        catch (IOException ex) {
            Logger.getLogger(TestServer.class.getName()).log(Level.SEVERE, null,
                                                             ex);
        }
        
        ConnectionHandler.close();
    }
}
