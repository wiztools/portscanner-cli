package org.wiztools.util.portscanner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author subwiz
 *
 */
public class Main {

    public static void main(String[] args) {
        if(args.length != 1){
            System.err.println("Please enter hostname as parameter!");
            System.exit(1);
        }

        String hostname = args[0];

        try{
            InetAddress addr = InetAddress.getByName(hostname);

            List<Port> openPorts = new PortScanner(addr).scan();
            
            for(Port p: openPorts){
                System.out.println(p.getPort() + "\t" + p.getType());
            }
        }
        catch(UnknownHostException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }
    }
}
