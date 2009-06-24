package org.wiztools.util.portscanner;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author subwiz
 *
 */
public class Main {

    private static final int MAX_PORT_SIZE = 1024 * 64; // 65536

    public static void main(String[] args) {
        if(args.length != 1){
            System.err.println("Please enter hostname as parameter!");
            System.exit(1);
        }

        String hostname = args[0];

        List<Port> openPorts = new ArrayList<Port>();

        String hostnameResolveError = null;
        for(int i=1; i<MAX_PORT_SIZE; i++){
            try{
                Socket s = new Socket(hostname, i);
                s.close();
                openPorts.add(new Port(i, Type.TCP));
            }
            catch(UnknownHostException ex){
                hostnameResolveError = ex.getMessage();
                break;
            }
            catch(IOException ex){

            }
        }
        if(hostnameResolveError != null){
            System.err.println(hostnameResolveError);
            System.exit(2);
        }
        else{
            for(Port p: openPorts){
                System.out.println(p.getPort());
            }
        }
    }
}
