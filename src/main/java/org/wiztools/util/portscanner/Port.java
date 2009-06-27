package org.wiztools.util.portscanner;

/**
 *
 * @author subwiz
 */
public class Port implements Comparable<Port> {
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

    public int compareTo(Port o) {
        if(this.port == o.port){
            if(this.type == o.type)
                return 0;
            else if(this.type == Type.TCP && o.type == Type.UDP)
                return 1;
            else
                return -1;
        }
        else if(this.port < o.port){
            return -1;
        }
        else{
            return 1;
        }
    }
    
}
