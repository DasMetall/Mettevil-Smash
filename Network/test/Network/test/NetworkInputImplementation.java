/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.test;

import Network.PrimaryMessage;

/**
 *
 * @author Max Brauer
 */
public class NetworkInputImplementation extends Network.NetworkInput {

    @Override
    public void MessageReceived(PrimaryMessage message) {
        String text = "Message received: ID=" + message.getSenderId()
                + " Ping=" + Network.ConnectionHandler.getConnectionManager().
                getConnection(message.getSenderId()).getUser().getLastPing()
                + " Type=" + message.getType();
        switch (message.getType()) {
            case RawText:
                text += " Text=\"" + (new String(message.getDataAsByteArray())) + "\"";
                break;
        }
        System.out.println(text);
    }

}
