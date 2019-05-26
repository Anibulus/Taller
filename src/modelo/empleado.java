package modelo;
public class empleado {
    private int idEmpleado;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String puesto;
    private String direccion;
    private String telefono;
    private String curp;
    private double sueldo;
    private int idusuario;
    private String usuario;
    private String contrasena;
    private String estatus;
    
    public empleado(){
        this.idEmpleado=0;
        this.nombre="";
        this.apellidoP="";
        this.apellidoM="";
        this.direccion="";
        this.telefono="";
        this.curp="";
        this.puesto="";
        this.sueldo=0;
        this.idusuario=0;
        this.usuario="";
        this.contrasena="";
        this.estatus="";
    }

    public empleado(int idEmpleado, int idusuario, String nombre, String apellidoP, String apellidoM, String curp, String puesto, String calle, int numExterior, int numInterior, String telefono, double sueldo, String usuario, String contrasena, String estatus) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.curp=curp;
        this.puesto = puesto;
        this.direccion = calle;
        this.telefono = telefono;
        this.sueldo = sueldo;
        this.idusuario=idusuario;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.estatus = estatus;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String calle) {
        this.direccion = calle;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getEmpleado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
