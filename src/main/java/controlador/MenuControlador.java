package controlador;

import vista.MenuGui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuControlador implements ActionListener {
    private MenuGui menuGui;

    public MenuControlador() {
        menuGui = new MenuGui();
        menuGui.iniciarButton.addActionListener(this);
        menuGui.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String eventoString = e.getActionCommand();
        System.out.println("Evento boton: "+eventoString);
        if(eventoString == "Iniciar"){

            iniciarEvento();
        }
    }

    public void iniciarEvento(){

        String nombre = menuGui.nombreTXT.getText();
        String ipDestino = menuGui.ipDestinoTXT.getText();
        int puertoDestino;
        int puertoOrigen;
        if(!validIP(ipDestino)){
            JOptionPane.showMessageDialog(null,"Error, la dirección no es valida","Error dirección ip",JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            puertoDestino = Integer.parseInt(menuGui.puertoDestinoTXT.getText());
            puertoOrigen = Integer.parseInt(menuGui.puertoOrigenTXT.getText());


        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Error, uno de los puertos no es valida","Error puertos",JOptionPane.ERROR_MESSAGE);
            return;
        }
        ChatControlador chatControlador = new ChatControlador(nombre,ipDestino,puertoDestino,puertoOrigen);
        menuGui.dispose();

    }
    public static boolean validIP (String ip) {
        //https://stackoverflow.com/a/5240291
        if(ip.equals("localhost")){
            return true;
        }
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


}
