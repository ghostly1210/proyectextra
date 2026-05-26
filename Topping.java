//  Topping  —  hereda de Ingrediente
//  Ingrediente camaron = new Topping()
public class Topping extends Ingrediente {

    private String tipo;
    private double peso;

    public Topping(String tipo, double peso) {
        super(tipo, Math.round(peso * 0.1 * 100.0) / 100.0);  // precio = peso * 0.1
        this.tipo = tipo;
        this.peso = peso;
    }

    public String getTipo() { return tipo; }
    public double getPeso() { return peso; }

    @Override
    public String toString() {
        return "Topping[" + tipo + "] Q" + precio;
    }
}
