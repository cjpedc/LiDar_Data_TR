/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lidar_data_tr;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Carlos Javier
 */
public class Sensor_Select_GUI extends JPanel implements ActionListener {

    static String sensor1 = "Velodyne 1";
    static String sensor2 = "Velodyne 2";
    public JRadioButton s1Button = new JRadioButton(sensor1);
    public JRadioButton s2Button = new JRadioButton(sensor2);
    JLabel picture;
    static boolean clientExists = false;
    static public Client client;
    static public RxServer rxServer;
    //public TxServer txServer;
    static public Thread rxThread;
    //static public Thread txThread;

    //Client PortFwd = null;
    Sensor_Select_GUI() {

        super((new BorderLayout()));
        //Create radio buttons
        //JRadioButton s1Button = new JRadioButton(sensor1);
        s1Button.setMnemonic(KeyEvent.VK_1);
        s1Button.setActionCommand(sensor1);
        s1Button.setSelected(true);

        //JRadioButton s2Button = new JRadioButton(sensor2);
        s2Button.setMnemonic(KeyEvent.VK_2);
        s2Button.setActionCommand(sensor2);

        //Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(s1Button);
        group.add(s2Button);

        //Register a listener for the radio buttons
        s1Button.addActionListener(this);
        s2Button.addActionListener(this);
        
        //Set up the picture label.
        picture = new JLabel(createImageIcon("vlp16.png"));
        
        //The preferred size is hard-coded to be the width of the
        //widest image and the height of the tallest image.
        //A real program would compute this.
        //picture.setPreferredSize(new Dimension(177, 122));

        //Put the radio buttons in a column in a panel
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(s1Button);
        radioPanel.add(s2Button);

        add(radioPanel, BorderLayout.LINE_START);
        add(picture, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }
    
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Sensor_Select_GUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int port = 2372;

        if (s1Button.isSelected()) {
            System.out.print("Sensor 1");
            port = 2372;
        } else if (s2Button.isSelected()) {
            System.out.print("Sensor 2");
            port = 2371;
        }

        if (!clientExists) {
            client = new Client(port);
            createThread(client);
            clientExists = true;
        } else if (clientExists) {
            client.close_socket();
            stopThread();
            client = new Client(port);
            clientExists = true;
            createThread(client);
        }
    }

    static void createThread(Client c) {
        rxServer = new RxServer(c);
        //txServer = new TxServer(client);
        rxThread = new Thread(rxServer, "RxServer Thread");
        rxThread.start();
        clientExists = true;
        //txThread.start();
    }

    static void stopThread() {
        rxServer.terminate();
        try {
            rxThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Sensor_Select_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void createGUI() {
        //Create and set up the window
        JFrame frame = new JFrame("Sensor Selector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane
        JComponent newContentPane = new Sensor_Select_GUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
                client = new Client(2372);
                createThread(client);
            }
        });
    }

}
