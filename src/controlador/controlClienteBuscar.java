package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.clienteDAO;
import modelo.empleadoDAO;
import vista.principal;
import vista.ventanaClienteBuscar;

public class controlClienteBuscar implements ActionListener{
    clienteDAO cDAO;
    ventanaClienteBuscar vcb;
    public controlClienteBuscar(clienteDAO cDAO, ventanaClienteBuscar vc){
        this.cDAO=cDAO;
        this.vcb=vc;
        this.vcb.btnRegresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==vcb.btnRegresar){
            principal menu=new principal();
            empleadoDAO empDAO=new empleadoDAO();          
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
            try {
                controlVentanaPrincipal cvp=new controlVentanaPrincipal(menu, empDAO);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un errro al regresarte");
            }
            vcb.dispose();
        }
    }//Aqui termina el escuchador 
}//Aqui termina la clase
