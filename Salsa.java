//  Salsa  —  hereda de Ingrediente
//  Ingrediente salsa = new Salsa()
public class Salsa extends Ingrediente {

    private String tipo;
    private boolean esPicante;

    public Salsa(String tipo, boolean esPicante) {
        super(tipo, 1.50);   // precio fijo Q1.50
        this.tipo      = tipo;
        this.esPicante = esPicante;
    }

    public String  getTipo()      { return tipo;      }
    public boolean isEsPicante()  { return esPicante; }

    @Override
    public String toString() {
        return "Salsa[" + tipo + (esPicante ? " PICANTE" : "") + "] Q" + precio;
    }
}
