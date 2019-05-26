package controlador;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.empleado;
import modelo.empleadoDAO;
import vista.principal;
import vista.ventanaEmpleado;

public class controlEmpleado implements ActionListener{
    private empleadoDAO empDAO;
    private ventanaEmpleado vEmp;
    
    public controlEmpleado(ventanaEmpleado ve, empleadoDAO emDao){
        this.empDAO=emDao;
        this.vEmp=ve;
        vEmp.btnConsultar.addActionListener(this);
        vEmp.btnActualizar.addActionListener(this);
        vEmp.btnEliminar.addActionListener(this);
        vEmp.btnLimpiar.addActionListener(this);
        vEmp.btnRegresar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vEmp.btnConsultar){
            String id=String.valueOf(vEmp.txtCodigo.getValue());
            String nombre=vEmp.txtNombre.getText();
            if(!id.equals("")||!nombre.equals("")){
                /*
                    Aqui verificamos que los campos que se reuiqrenpara esta
                    funcion esten llenos
                */
                if(id.equals("")){
                    id="0";
                }
                empleado emp;
                try{
                    emp=empDAO.consultarEmpleado(Integer.parseInt(id), nombre);
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error: "+ex);
                    emp=null;
                }

                if(emp!=null){
                    vEmp.txtCodigo.setValue(emp.getIdEmpleado());
                    vEmp.txtNombre.setText(emp.getNombre());
                    vEmp.txtApellidoP.setText(emp.getApellidoP());
                    vEmp.txtApellidoM.setText(emp.getApellidoM());
                    vEmp.listaPuesto.setSelectedItem(emp.getPuesto());
                    vEmp.txtCurp.setText(emp.getCurp());
                    vEmp.txtSueldo.setText(String.valueOf(emp.getSueldo()));
                    vEmp.txtUsuario.setText(emp.getUsuario());
                    vEmp.txtDireccion.setText(emp.getDireccion());
                    vEmp.txtTelefono.setText(emp.getTelefono());
                }
                else{
                    JOptionPane.showMessageDialog(null, "El empleado no ha sido encontrado");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "No deben estar los capos vacios de codigo o nombre para consultar");
            }
        }
        else if(e.getSource()==vEmp.btnActualizar){
            String id=String.valueOf(vEmp.txtCodigo.getValue());
            String nom=vEmp.txtNombre.getText();
            String app=vEmp.txtApellidoP.getText();
            String apm=vEmp.txtApellidoM.getText();
            String direccion=vEmp.txtDireccion.getText();
            String telefono=vEmp.txtTelefono.getText();
            String curp=vEmp.txtCurp.getText();
            String puesto=(String) vEmp.listaPuesto.getSelectedItem();
            String sueldo=vEmp.txtSueldo.getText();
            try{
                if(Double.parseDouble(sueldo)>0){
                    if(!id.equals("")){
                    /*
                        Si alguno de esos campos esta vacio, no lepermite avanzar
                    */
                        try {
                            if(empDAO.modificarEmpleado(Integer.parseInt(id),nom,app,apm,direccion,telefono,curp,puesto,Double.parseDouble(sueldo))){
                                JOptionPane.showMessageDialog(null, "Se ha modificado correctamente el registro");
                                limpiarCampos();
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Ocurrio un problema SQL al modificar");
                        }
                    }//Aqui termina el IF de el ID vacio
                    else{
                        JOptionPane.showMessageDialog(null, "Recuerda llenar todos los campos");
                        JOptionPane.showMessageDialog(null, "Recomendamos consultar antes para facilitar tu trabajo :)");               
                    }//Aqui termina el ELSE
                }//Aqui termina la validacion de el sueldo
                else{
                    JOptionPane.showMessageDialog(null, "Sueldo debe tener un valor valido");
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Sueldo debe tener un valor valido");
            }  
        }
        else if(e.getSource()==vEmp.btnEliminar){ 
            /*
                Aqui estan a punto de eliminar y necesitamos confirmarlo
            */
            int desicion= JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar?");
            if(desicion==JOptionPane.YES_OPTION){
                String id=String.valueOf(vEmp.txtCodigo.getValue());  
                if(!id.equals("")){
                    try{
                        if(empDAO.eliminarEmpleado(Integer.parseInt(id))){
                            JOptionPane.showMessageDialog(null, "Se ha eliminado al empleado correctamente");
                            limpiarCampos();
                        }
                    }
                    catch(HeadlessException | NumberFormatException | SQLException ex){
                        JOptionPane.showMessageDialog(null, "Un error ha ocurrido al Eliminar "+ex.getMessage() );
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "El campo 'Codigo' debe esta lleno");
                }
            }//Aqui termina la desicion si fue un Si
            /*
                Cuando eligen no eliminar solo anuncia que no se borro
            */
            else if(desicion==JOptionPane.NO_OPTION){
                JOptionPane.showMessageDialog(null, "Empleado no borrado");
            }//Aqui termina la desicion en casode un no
        }
        else if(e.getSource()==vEmp.btnLimpiar){
            /*
                Tengo una funcion dentro de la misma clase para limpiar todos los 
                campos y lo uso en varias funciones
            */
            limpiarCampos();            
        }
        else if(e.getSource()==vEmp.btnRegresar){
            principal inicio=new principal();
            inicio.setVisible(true);
            inicio.setLocationRelativeTo(null);
            vEmp.dispose();
            try {
                controlVentanaPrincipal cvp=new controlVentanaPrincipal(inicio,empDAO);
            } catch (SQLException ex) {
                //Logger.getLogger(controlEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//Aqui termina action listener
    
    /*
        Al ser una funcion que uso muchas veces, la dejo de lado para reutilizarlo
    */
    public void limpiarCampos(){
        vEmp.txtCodigo.setValue(0);
        vEmp.txtNombre.setText("");
        vEmp.txtApellidoP.setText("");
        vEmp.txtApellidoM.setText("");  
        vEmp.listaPuesto.setSelectedItem("");
        vEmp.txtSueldo.setText("");
        vEmp.txtUsuario.setText("");
        vEmp.txtCurp.setText("");
        vEmp.txtDireccion.setText("");
        vEmp.txtTelefono.setText("");
    }
    
}//Aqui termina la clase
