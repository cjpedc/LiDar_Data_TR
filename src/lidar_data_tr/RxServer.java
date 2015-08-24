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
public class RxServer implements Runnable {

    private volatile boolean running = true;
    Client client;

    RxServer(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (running) {
            try {
                this.client.RECEIVE();
                this.client.SEND();
                this.client.resetBufferLength();
//                synchronized(Sensor_Select_GUI.txThread){
//                    Sensor_Select_GUI.txThread.notify();
//                }
                //TODO process rx data 
            } catch (IOException ex) {
                Logger.getLogger(RxServer.class.getName()).log(Level.SEVERE, null, ex);
                running = false;
            }
        }
    }

    public void terminate() {
        running = false;
    }
}
