package controlador;

import vista.ChatGui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatControlador implements ActionListener,Runnable {
    private ChatGui chatGui;
    private String nombreUsuario;
    private String ipDestino;
    private int puertoDestino;
    private int puertoOrigen;
    private Thread hiloInput;
    public ChatControlador(String nombreUsuario, String ipDestino, int puertoDestino, int puertoOrigen) {
        this.nombreUsuario = nombreUsuario;
        this.ipDestino = ipDestino;
        this.puertoDestino = puertoDestino;
        this.puertoOrigen = puertoOrigen;
        chatGui = new ChatGui();
        chatGui.enviarButton.addActionListener(this);
        chatGui.nombreUsuarioLabel.setText(nombreUsuario);
        chatGui.textArea1.setEditable(false);

        chatGui.setVisible(true);

        hiloInput = new Thread(this);
        hiloInput.start();




    }
    @Override
    public void actionPerformed(ActionEvent e) { //Enviar, Salida
        if (e.getSource() == chatGui.enviarButton){
            try {
                Socket miSocket = new Socket(ipDestino,puertoDestino);
                DataOutputStream dataOutputStream = new DataOutputStream(miSocket.getOutputStream());
                String mensajeSalida  = nombreUsuario + ": "+chatGui.textField1.getText();
                dataOutputStream.writeUTF(mensajeSalida);
                chatGui.textArea1.append(mensajeSalida+"\n");
                miSocket.close();
                dataOutputStream.close();

            } catch (IOException e2) {
                System.err.println(e2.getMessage());
                JOptionPane.showMessageDialog(null,"No se pudo conectar", "Error de conexión",JOptionPane.ERROR_MESSAGE);
            }
            chatGui.textField1.setText(null);
        }
    }
    @Override
    public void run() { //Leer Entrada
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(puertoOrigen);
                Socket miSocket = serverSocket.accept();

                DataInputStream dataInputStream = new DataInputStream(miSocket.getInputStream());
                String mensajeTexto = dataInputStream.readUTF();
                chatGui.textArea1.append(mensajeTexto + "\n");
                miSocket.close();
                serverSocket.close();
                dataInputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
