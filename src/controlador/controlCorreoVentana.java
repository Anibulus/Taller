package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.controlCorreo;
import modelo.correo;
import vista.ventanaCorreo;

public class controlCorreoVentana implements ActionListener {
    private ventanaCorreo ventana;
    correo c;
    public controlCorreoVentana(ventanaCorreo ventana){
        this.ventana=ventana;
        c=new correo();
        c.setUssuario("anibulusnn@gmail.com");
        c.setContrasena("kagamine");
        this.ventana.btnEnviar.addActionListener(this);
        this.ventana.btnCancelar.addActionListener(this);
    }   
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==ventana.btnEnviar){
            c.setAsunto(String.valueOf(ventana.txtAsunto.getSelectedItem()));          
            c.setMensaje(ventana.txtMensaje.getText());
            c.setDestino(ventana.txtDestino.getText());
            //c.setDestino("abigailyo2.0@gmail.com");            
            controlCorreo  co=new controlCorreo();
            if(co.enviarCorreo(c)){
                JOptionPane.showMessageDialog(null, "Se envio Correo");
                ventana.dispose();
            }
            else{
               JOptionPane.showMessageDialog(null, "No se ha podido enviar el correo");
            }
        }
        else if(ae.getSource()==ventana.btnCancelar){
            ventana.dispose();
        }
    }  
}
