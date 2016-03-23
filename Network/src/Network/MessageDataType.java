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
public enum MessageDataType {
    BinaryData(0),
    Object(1),
    ILoadSaveAble(2),
    Message(3);

    private int id;
    
    public int toInt() {
        return id;
    }

    private MessageDataType(int id) {
        this.id = id;
    }

    public static MessageDataType fromInt(int value) {
        for (MessageDataType type : MessageDataType.values())
            if (type.id == value)
                return type;
        return null;
    }
}
