/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lidar_data_tr;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Javier
 */
public class TxServer implements Runnable {
    private volatile boolean running = true;
    Client client;

    TxServer(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
//            try {
//                synchronized (Sensor_Select_GUI.txThread) {
//                    //this.wait();
//                    Sensor_Select_GUI.txThread.wait();
//                }
//            } catch (InterruptedException ex) {
//                Logger.getLogger(TxServer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                this.client.SEND();
//                this.client.resetBufferLength();
//                //this.client.print_msgs = true;
//                //this.client.print();
//            } catch (IOException ex) {
//                Logger.getLogger(TxServer.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }
}
