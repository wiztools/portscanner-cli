package org.wiztools.util.portscanner;

/**
 *
 * @author subwiz
 */
public class Port {
    private int port;
    private Type type;

    public Port(int port, Type type){
        this.port = port;
        this.type = type;
    }

    public int getPort() {
        return port;
    }

    public Type getType() {
        return type;
    }
    
}
