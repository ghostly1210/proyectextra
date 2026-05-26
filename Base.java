public class Base extends Ingrediente {

    private String tipo;

    public Base(String tipo) {
        super(tipo, 3.00);   // precio fijo Q3.00
        this.tipo = tipo;
    }

    public String getTipo() { return tipo; }

    @Override
    public String toString() { return "Base[" + tipo + "] Q" + precio; }
}
