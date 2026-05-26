import java.util.*;

//  OrdenController  —  genera ordenes y valida
public class OrdenController {

    // Catálogo de ingredientes disponibles
    // Ingrediente nuevaBase = new Base()
    private static final Base[] BASES = {
        new Base("Masa normal"),
        new Base("Masa de alga"),
    };
    private static final Salsa[] SALSAS = {
        new Salsa("Salsa normal",  false),
        new Salsa("Salsa picante", true),
    };
    private static final Topping[] TOPPINGS = {
        new Topping("Pescado",   30),
        new Topping("Camaron",   25),
        new Topping("Squid",     20),
        new Topping("Alga",      15),
        new Topping("Hielo",     10),
    };

    private static final String[] CLIENTES = {
        "Pingu", "Tux", "Skipper", "Rico", "Kowalski"
    };

    private Random random = new Random();

    // ── Genera una Orden aleatoria ──
    public Orden generarOrden() {
        Base  base  = BASES [random.nextInt(BASES.length)];
        Salsa salsa = SALSAS[random.nextInt(SALSAS.length)];

        List<Topping> todos = new ArrayList<>(Arrays.asList(TOPPINGS));
        Collections.shuffle(todos, random);
        List<Topping> elegidos = todos.subList(0, 1 + random.nextInt(3));

        Pizza pizzaObj = new Pizza(base, salsa, elegidos);
        return new Orden(pizzaObj, CLIENTES[random.nextInt(CLIENTES.length)]);
    }

    // ── Validar y puntuar — boolean para saber si pasó ──
    public boolean validarYPuntuar(Pizza pizzaArmada, Orden orden) {
        String problema = pizzaArmada.compararCon(orden.getPizzaDeseada());
        if (problema == null) {
            orden.setEstado("COMPLETADA");
            System.out.printf("%n  ✓ PIZZA PERFECTA! +100 puntos — Q%.2f cobrados%n",
                              pizzaArmada.calcularPrecio());
            return true;
        } else {
            orden.setEstado("FALLIDA");
            System.out.println("\n  ✗ Te falto: " + problema);
            return false;
        }
    }

    // ── Mostrar catálogo con precios ──
    public void mostrarCatalogo() {
        System.out.println("\n  -- BASES --");
        for (int i = 0; i < BASES.length; i++)
            System.out.printf("  %d. %-20s Q%.2f%n", i+1, BASES[i].getNombre(), BASES[i].getPrecio());
        System.out.println("  -- SALSAS --");
        for (int i = 0; i < SALSAS.length; i++)
            System.out.printf("  %d. %-20s Q%.2f%n", i+1, SALSAS[i].getNombre(), SALSAS[i].getPrecio());
        System.out.println("  -- TOPPINGS --");
        for (int i = 0; i < TOPPINGS.length; i++)
            System.out.printf("  %d. %-20s Q%.2f%n", i+1, TOPPINGS[i].getNombre(), TOPPINGS[i].getPrecio());
    }

    public Base[]    getBases()    { return BASES;    }
    public Salsa[]   getSalsas()   { return SALSAS;   }
    public Topping[] getToppings() { return TOPPINGS; }
}
