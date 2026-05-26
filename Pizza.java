import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//  Pizza - usa las clases Base, Salsa y Topping
//  Pizza pizza = new Pizza()
public class Pizza {

    private Base          base;
    private Salsa         salsa;
    private List<Topping> toppings;

    // Pizza vacia para ir armando
    public Pizza() {
        this.toppings = new ArrayList<>();
    }

    // Pizza ya armada (para la orden objetivo)
    public Pizza(Base base, Salsa salsa, List<Topping> toppings) {
        this.base     = base;
        this.salsa    = salsa;
        this.toppings = new ArrayList<>(toppings);
    }

    // Setters
    public void setBase(Base b)    { this.base  = b; }
    public void setSalsa(Salsa s)  { this.salsa = s; }
    public void addTopping(Topping t) { toppings.add(t); }

    // Getters
    public Base          getBase()     { return base;     }
    public Salsa         getSalsa()    { return salsa;    }
    public List<Topping> getToppings() { return toppings; }

    // Precio total: suma todos los ingredientes
    public double calcularPrecio() {
        double total = 0;
        if (base  != null) total += base.getPrecio();
        if (salsa != null) total += salsa.getPrecio();
        for (Topping t : toppings) total += t.getPrecio();
        return Math.round(total * 100.0) / 100.0;
    }

    // Comparar con la pizza objetivo de la orden.
    // Devuelve null si esta perfecta, o el nombre de lo que falla.
    public String compararCon(Pizza objetivo) {
        if (objetivo == null) return "La orden no tiene pizza objetivo";

        if (base == null || !base.equals(objetivo.getBase()))
            return "Base: " + (objetivo.getBase() != null ? objetivo.getBase().getNombre() : "ninguna");
        if (salsa == null || !salsa.equals(objetivo.getSalsa()))
            return "Salsa: " + (objetivo.getSalsa() != null ? objetivo.getSalsa().getNombre() : "ninguna");

        Map<Topping, Integer> toppingsMios = contarToppings(toppings);
        Map<Topping, Integer> toppingsObjetivo = contarToppings(objetivo.getToppings());

        for (Map.Entry<Topping, Integer> esperado : toppingsObjetivo.entrySet()) {
            int cantidadMia = toppingsMios.getOrDefault(esperado.getKey(), 0);
            if (cantidadMia < esperado.getValue()) {
                return "Topping faltante: " + esperado.getKey().getNombre();
            }
        }

        for (Map.Entry<Topping, Integer> mio : toppingsMios.entrySet()) {
            int cantidadEsperada = toppingsObjetivo.getOrDefault(mio.getKey(), 0);
            if (mio.getValue() > cantidadEsperada) {
                return "Topping de mas: " + mio.getKey().getNombre();
            }
        }

        return null; // perfecta
    }

    private Map<Topping, Integer> contarToppings(List<Topping> lista) {
        Map<Topping, Integer> conteo = new HashMap<>();
        for (Topping topping : lista) {
            conteo.merge(topping, 1, Integer::sum);
        }
        return conteo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  Base    : ").append(base  != null ? base.getNombre()  : "(sin base)").append("\n");
        sb.append("  Salsa   : ").append(salsa != null ? salsa.getNombre() : "(sin salsa)").append("\n");
        sb.append("  Toppings: ");
        if (toppings.isEmpty()) sb.append("(ninguno)");
        else toppings.forEach(t -> sb.append(t.getNombre()).append(" | "));
        sb.append("\n");
        sb.append(String.format("  Precio  : Q%.2f", calcularPrecio()));
        return sb.toString();
    }
}
