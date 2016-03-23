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
public abstract class NetworkInput {
    public static NetworkInput networkInput;
    
    public abstract void MessageReceived(PrimaryMessage message);
}

//class NetworkInputImplementation extends NetworkInput {
//    public NetworkInputImplementation() {
//        NetworkInput.networkInput = this;
//    }
//}
