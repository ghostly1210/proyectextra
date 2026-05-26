import java.util.Locale;

public abstract class Ingrediente {

    protected String nombre;
    protected double precio;

    public Ingrediente(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio;  }

    @Override
    public String toString() { return nombre; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.nombre.equalsIgnoreCase(((Ingrediente) obj).nombre);
    }

    @Override
    public int hashCode() {
        return nombre.toLowerCase(Locale.ROOT).hashCode();
    }
}
