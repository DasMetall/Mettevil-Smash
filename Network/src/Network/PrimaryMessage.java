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
public class PrimaryMessage extends Message {
    public PrimaryMessageType getType() {
        return PrimaryMessageType.fromInt(super.getReason());
    }
    
    public void setType(PrimaryMessageType type) {
        super.setReason(type.toInt());
    }
}
