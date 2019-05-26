package modelo;
public class cliente {
    private int idCliente;
    private int idUsuario;
    private String nombre;
    private String app;
    private String apm;
    private String telefono;
    private String direccion;
    private String correo;
    private String usuario;
    private String contrasena;
    
    public cliente(){
        this.idCliente=0;
        this.idUsuario=0;
        this.nombre="";
        this.app="";
        this.apm="";
        this.telefono="";
        this.direccion="";
        this.correo="";
        this.usuario="";
        this.contrasena="";
    }
    public cliente(int id, int idUsu,String nom, String app, String apm, String tel, String direccion, String correo, String usu, String contra){
        this.idCliente=id;
        this.idUsuario=idUsu;
        this.nombre=nom;
        this.app=app;
        this.telefono=tel;
        this.usuario=usu;
        this.contrasena=contra;
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getApm() {
        return apm;
    }

    public void setApm(String apm) {
        this.apm = apm;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
}
