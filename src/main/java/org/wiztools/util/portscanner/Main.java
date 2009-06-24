package org.wiztools.util.portscanner;

import java.io.IOException;
import java.net.InetAddress;
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

    private static final int DEFAULT_THREAD_COUNT = 4;

    private static List<Port> openPorts = new ArrayList<Port>();

    private static synchronized void addOpenPort(Port p){
        openPorts.add(p);
    }

    public static void main(String[] args) {
        if(args.length != 1){
            System.err.println("Please enter hostname as parameter!");
            System.exit(1);
        }

        String hostname = args[0];

        try{
            InetAddress addr = InetAddress.getByName(hostname);

            // Threading logic:
            int chunkSize = MAX_PORT_SIZE / DEFAULT_THREAD_COUNT;

            Thread[] tArr = new Thread[DEFAULT_THREAD_COUNT];
            for(int i=0; i<DEFAULT_THREAD_COUNT; i++){
                int startPort = (i * chunkSize) + 1;
                int endPort;
                if((DEFAULT_THREAD_COUNT - 1)==i){ // Last iteration
                    endPort = MAX_PORT_SIZE - 1;
                }
                else{
                    endPort = (startPort-1) + chunkSize;
                }
                System.out.println("Thread " + i + " scanning from " + startPort + " to " + endPort);
                tArr[i] = new Thread(new PortScanThread(addr, startPort, endPort));
                tArr[i].start();
            }

            for(Thread t: tArr){
                try{
                    t.join();
                }
                catch(InterruptedException ex){
                    
                }
            }
            
            for(Port p: openPorts){
                System.out.println(p.getPort() + "\t" + p.getType());
            }
        }
        catch(UnknownHostException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }
    }

    static class PortScanThread implements Runnable{
        private InetAddress addr;
        private int startPort;
        private int endPort;

        PortScanThread(InetAddress addr, int startPort, int endPort){
            this.addr = addr;
            this.startPort = startPort;
            this.endPort = endPort;
        }

        @Override
        public void run() {
            for(int i=startPort; i<endPort; i++){
                try{
                    Socket s = new Socket(addr, i);
                    s.close();
                    addOpenPort(new Port(i, Type.TCP));
                }
                catch(IOException ex){
                    // Do nothing!
                }
            }
        }
        
    }
}
