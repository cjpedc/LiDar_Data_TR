/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lidar_data_tr;

import java.io.*;
import java.net.*;

/**
 *
 * @author Carlos Javier
 */
public class Client {

    Boolean print_msgs = false; // Print packets received and sent
    public int out_port;
    public byte[] buffer, Sbuffer;
    public DatagramSocket dsocket, out_dsocket;
    public DatagramPacket packet, sendPacket;
    public InetAddress IPAddress;

    Client(int port) {
        this.out_port = 2368;
        try {
            // Create a socket to listen on the port
            open_dsocket(port);
            out_dsocket = new DatagramSocket();
            IPAddress = InetAddress.getByName("130.229.177.237"); // sending to
            // Create a buffer to read datagrams into. If a
            // packet is larger than this buffer, the
            // excess will simply be discarded!
            buffer = new byte[1206]; // receiver 
            Sbuffer = buffer; // 

            // Create a packet to receive data into the buffer
            packet = new DatagramPacket(buffer, buffer.length);
            // Create a packet to send data into the buffer
            sendPacket = new DatagramPacket(Sbuffer, Sbuffer.length, IPAddress, out_port);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    void open_dsocket(int r_port) throws SocketException {
        this.dsocket = new DatagramSocket(r_port);
    }

    void close_socket() {
        this.dsocket.close();
        this.out_dsocket.close();
    }

    void RECEIVE() throws IOException {
        // Wait to receive a datagram
        this.dsocket.receive(packet);
    }

    void SEND() throws IOException {
        this.out_dsocket.send(sendPacket); //
    }

    void resetBufferLength() {
        this.packet.setLength(buffer.length);
        this.sendPacket.setLength(Sbuffer.length); //
    }

    void print() {
        String msg = new String(buffer, 0, packet.getLength());
        String send_msg = new String(Sbuffer, 0, sendPacket.getLength()); //
        System.out.println("Receiving from: " + packet.getAddress().getHostName() + " Buffer length "
                + buffer.length + " Port " + packet.getPort() + ": "
                + msg);
        System.out.println("Sending to: " + sendPacket.getAddress().getHostName() + " Buffer length "
                + Sbuffer.length + " Port " + sendPacket.getPort() + ": "
                + send_msg); //
    }
}
