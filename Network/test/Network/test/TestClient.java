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
public class TestClient {
    public static void main(String[] args) {
        NetworkInput.networkInput = new NetworkInputImplementation();
        
        ConnectionHandler.events = new ConnectionHandlerEventImplementation();
        
        ConnectionHandler.initAsClient("localhost", 50102);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            String text;
            while (!(text = in.readLine()).equals("exit")) {
                PrimaryMessage mes = new PrimaryMessage();
                mes.setType(PrimaryMessageType.RawText);
                mes.setDataAsByteArray(text.getBytes());
                NetworkOutput.SendMessage(mes);
            }
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
