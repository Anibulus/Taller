package modelo;
public class material {
    private int idMaterial;
    private int idProveedor;
    private String nombre;
    private double costo;
    private double stock;
    
    public material() {
        this.idMaterial = 0;
        this.idProveedor = 0;
        this.nombre = "";
        this.costo = 0;
        this.stock = 0;
    }

    public material(int idMaterial, int idProveedor, String nombre, double costo, double stock) {
        this.idMaterial = idMaterial;
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.costo = costo;
        this.stock = stock;
    }
    
    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }
    
    
    
}
