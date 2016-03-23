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
public enum PrimaryMessageType {
    None(0),
    Ping(1),
    PingAnswer(2),
    RawText(3), //for debug purpose -> test package
    ;    
    
    private int id;
    
    public int toInt() {
        return id;
    }

    private PrimaryMessageType(int id) {
        this.id = id;
    }

    public static PrimaryMessageType fromInt(int value) {
        for (PrimaryMessageType type : PrimaryMessageType.values())
            if (type.id == value)
                return type;
        return null;
    }
}
