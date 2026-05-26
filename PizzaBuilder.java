import java.util.Stack;

//  PizzaBuilder - la pizza es un Stack de Ingredientes
//  Cada ingrediente que agregas hace push
//  Deshacer hace pop (quita el ultimo)
//  PizzaBuilder -> Pizza (construye el objeto)
public class PizzaBuilder {

    // La pizza se guarda como Stack de Ingrediente
    // Asi el "deshacer" es simplemente un pop()
    private Stack<Ingrediente> stack;

    public PizzaBuilder() {
        this.stack = new Stack<>();
    }

    // Agregar ingrediente (push)
    public void agregarIngrediente(Ingrediente ing) {
        if (ing == null) {
            System.out.println("  [!] Ingrediente invalido.");
            return;
        }

        if (ing instanceof Base && contieneTipo(Base.class)) {
            System.out.println("  [!] La pizza ya tiene una base. Usa Deshacer si quieres cambiarla.");
            return;
        }

        if (ing instanceof Salsa && contieneTipo(Salsa.class)) {
            System.out.println("  [!] La pizza ya tiene una salsa. Usa Deshacer si quieres cambiarla.");
            return;
        }

        if (ing instanceof Topping && contieneTopping((Topping) ing)) {
            System.out.println("  [!] Ese topping ya fue agregado.");
            return;
        }

        stack.push(ing);
        System.out.println("  [+] Agregado: " + ing.getNombre() + " (Q" + ing.getPrecio() + ")");
    }

    private boolean contieneTipo(Class<? extends Ingrediente> tipo) {
        for (Ingrediente actual : stack) {
            if (tipo.isInstance(actual)) return true;
        }
        return false;
    }

    private boolean contieneTopping(Topping topping) {
        for (Ingrediente actual : stack) {
            if (actual instanceof Topping && actual.equals(topping)) return true;
        }
        return false;
    }

    // Deshacer: quita el ultimo que pusiste (pop)
    public Ingrediente deshacer() {
        if (stack.isEmpty()) {
            System.out.println("  [!] No hay nada que deshacer.");
            return null;
        }
        Ingrediente quitado = stack.pop();
        System.out.println("  [-] Deshecho: " + quitado.getNombre());
        return quitado;
    }

    // Ver mi pizza: muestra el stack completo
    public void verMiPizza() {
        if (stack.isEmpty()) {
            System.out.println("  Tu pizza esta vacia.");
            return;
        }
        System.out.println("  Tu pizza actual:");
        for (Ingrediente ing : stack) {
            System.out.println("    - " + ing);
        }
        // precio parcial
        double total = stack.stream().mapToDouble(Ingrediente::getPrecio).sum();
        System.out.printf("  Precio parcial: Q%.2f%n", total);
    }

    // Construir la Pizza final desde el stack
    // Pizza pizza = new Pizza() -> setBase -> setSalsa -> addTopping
    public Pizza construirPizza() throws Exception {
        Pizza pizza = new Pizza();

        for (Ingrediente ing : stack) {
            if      (ing instanceof Base)    pizza.setBase((Base) ing);
            else if (ing instanceof Salsa)   pizza.setSalsa((Salsa) ing);
            else if (ing instanceof Topping) pizza.addTopping((Topping) ing);
        }

        if (pizza.getBase()  == null) throw new Exception("Falta la BASE.");
        if (pizza.getSalsa() == null) throw new Exception("Falta la SALSA.");

        return pizza;
    }

    // Limpiar para la siguiente pizza
    public void reset() {
        stack.clear();
    }

    public boolean estaVacio() { return stack.isEmpty(); }
}
