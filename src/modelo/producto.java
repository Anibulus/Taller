package modelo;
public class producto {
    private int idProducto;
    private String nombre;
    private int stock;
    private String descripcion;
    private double costo;
    private String imagen;
    private String estatus;

    public producto() {
        this.idProducto = 0;
        this.nombre = "";
        this.stock = 0;
        this.descripcion = "";
        this.costo=0;
        this.imagen = "";
        this.estatus = "";
    }
    
    public producto(int idProducto, String nombre, int stock, String descripcion, double costo, String imagen, String estatus) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.stock = stock;
        this.descripcion = descripcion;
        this.costo=costo;
        this.imagen = imagen;
        this.estatus = estatus;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    
}
