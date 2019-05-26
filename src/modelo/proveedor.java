package modelo;
public class proveedor {
    private int idProvedoor;
    private String nombre;
    private String app;
    private String apm;
    private String telefono;
    private String nombreEmpresa;

    public proveedor() {
        this.idProvedoor = 0;
        this.nombre = "";
        this.app = "";
        this.apm = "";
        this.telefono = "";
        this.nombreEmpresa = "";
    }
    
    

    public proveedor(int idProvedoor, String nombre, String app, String apm, String telefono, String nombreEmpresa) {
        this.idProvedoor = idProvedoor;
        this.nombre = nombre;
        this.app = app;
        this.apm = apm;
        this.telefono = telefono;
        this.nombreEmpresa = nombreEmpresa;
    }

    public int getIdProvedoor() {
        return idProvedoor;
    }

    public void setIdProvedoor(int idProvedoor) {
        this.idProvedoor = idProvedoor;
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

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }
    
    
}
