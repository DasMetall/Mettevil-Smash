/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.*;
import java.lang.reflect.ParameterizedType;

/**
 *
 * @author Max Brauer
 */
public class Message implements iLoadSaveAble {

    private int reason;
    private byte[] header;
    private MessageDataType dataType;
    private byte[] data;
    private long senderId;

    public Message() {
        data = new byte[0];
        header = new byte[0];
    }

    @Override
    public void load(byte[] data) {
        reason = Helper.byteArrayToInt(data);
        header = new byte[data[4]];
        Helper.byteCopyTo(data, header, 5, 0, data[4]);
        dataType = MessageDataType.fromInt(data[5 + data[4]]);
        int length = Helper.byteArrayToInt(data, 6 + data[4]);
        this.data = new byte[length];
        Helper.byteCopyTo(data, this.data, 10 + data[4], 0, length);
    }

    @Override
    public byte[] save() {
        byte[] buffer = new byte[10 + data.length + header.length];
        Helper.byteCopyTo(Helper.intToByteArray(reason), buffer, 0, 0, 4);
        buffer[4] = (byte)header.length;
        Helper.byteCopyTo(header, buffer, 0, 5, header.length);
        buffer[5 + header.length] = (byte)dataType.toInt();
        Helper.byteCopyTo(Helper.intToByteArray(data.length), buffer, 0,
                          6 + header.length, 4);
        Helper.byteCopyTo(data, buffer, 0, 10 + header.length, data.length);
        return buffer;
    }

    /**
     * @return the reason
     */
    public int getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(int reason) {
        this.reason = reason;
    }

    /**
     * @return the dataType
     */
    public MessageDataType getDataType() {
        return dataType;
    }

    /**
     * @return the header
     */
    public byte[] getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(byte[] header) {
        if (header.length > Byte.MAX_VALUE)
            throw new ArrayIndexOutOfBoundsException(
                    "the length of header is larger then " + Byte.MAX_VALUE);
        this.header = header;
    }
    
    public long getSenderId() {
        return this.senderId;
    }
    
    public void setSenderId(long id) {
        this.senderId = id;
    }

    public byte[] getDataAsByteArray() {
        if (dataType.toInt() != MessageDataType.BinaryData.toInt())
            throw new IllegalArgumentException("Cannot parse the data to byte[]");
        return data;
    }

    public void setDataAsByteArray(byte[] data) {
        if (data == null)
            this.data = new byte[0];
        else
            this.data = data;
        this.dataType = MessageDataType.BinaryData;
    }

    public <T> T getDataAsObject() {
        if (dataType.toInt() != MessageDataType.Object.toInt())
            throw new IllegalArgumentException("Cannot parse the data to object");
        ByteArrayInputStream bis = new ByteArrayInputStream(this.data);
        ObjectInput in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            return null;
        }
        finally {
            try {
                bis.close();
            }
            catch (IOException ex) {
            }
            try {
                if (in != null)
                    in.close();
            }
            catch (IOException ex) {
            }
        }
        if (o == null)
            return null;
        return (T)o;
    }

    public <T> void setDataAsObject(T data) {
        this.dataType = MessageDataType.Object;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(data);
            this.data = bos.toByteArray();
        }
        catch (Exception e) {
            this.data = new byte[0];
        }
        finally {
            try {
                if (out != null)
                    out.close();
            }
            catch (IOException ex) {
            }
            try {
                bos.close();
            }
            catch (IOException ex) {
            }
        }
    }

    public <T extends iLoadSaveAble> T getDataAsLoadSaveAble() {
        if (dataType.toInt() != MessageDataType.ILoadSaveAble.toInt())
            throw new IllegalArgumentException(
                    "Cannot parse the data to iLoadSaveAble");
        T data = new FastActivator<T>().instance;
        if (data != null)
            data.load(this.data);
        return data;
    }

    public <T extends iLoadSaveAble> void setDataAsLoadSaveAble(T data) {
        this.data = data.save();
        this.dataType = MessageDataType.ILoadSaveAble;
    }

    public <T extends Message> T getDataAsMessage() {
        if (dataType.toInt() != MessageDataType.Message.toInt())
            throw new IllegalArgumentException(
                    "Cannot parse the data to Message");
        T data = new FastActivator<T>().instance;
        if (data != null) {
            data.load(this.data);
            data.setSenderId(this.senderId);
        }
        return data;
    }

    public <T extends Message> void setDataAsMessage(T message) {
        this.data = message.save();
        this.dataType = MessageDataType.Message;
    }
}

class FastActivator<T> {

    public T instance;

    public FastActivator() {
        try {
            instance = (T)((Class)((ParameterizedType)this.getClass().
                    getGenericSuperclass()).getActualTypeArguments()[0]).
                    newInstance();
        }
        catch (InstantiationException | IllegalAccessException ex) {
            instance = null;
        }
    }
}
