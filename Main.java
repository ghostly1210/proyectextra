import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

//  Main.java - punto de entrada del juego
//  MENU:
//  1. Comenzar
//  2. Agregar Ingrediente
//  3. Deshacer
//  4. Saltar Orden
//  5. Enviar Pizza
//  6. Ver mi Pizza
public class Main {

    // Cola de ordenes
    private static Queue<Orden>    colaOrdenes  = new LinkedList<>();
    private static Orden           ordenActual  = null;

    // PizzaBuilder: la pizza ES un stack
    private static PizzaBuilder    builder      = new PizzaBuilder();
    private static OrdenController ordenCtrl    = new OrdenController();

    private static Scanner scanner   = new Scanner(System.in);
    private static int     puntuacion = 0;
    private static boolean jugando    = false;
    public static void main(String[] args) {
        pantallaBienvenida();

        boolean corriendo = true;
        while (corriendo) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1": opcionComenzar();         break;
                case "2": opcionAgregarIngr();      break;
                case "3": opcionDeshacer();         break;
                case "4": opcionSaltarOrden();      break;
                case "5": opcionEnviarPizza();      break;
                case "6": opcionVerMiPizza();       break;
                case "0": corriendo = false;        break;
                default:  System.out.println("  Opcion invalida, intenta de nuevo.");
            }
        }

        System.out.println("\n  Gracias por jugar Pizzatron 3000!");
        System.out.println("  Puntuacion final: " + puntuacion);
        scanner.close();
    }

    // ========================================
    //  PANTALLA DE BIENVENIDA
    // ========================================
    private static void pantallaBienvenida() {
        System.out.println("       PIZZATRON  3000                ");
        System.out.println("       Club Penguin Edition           ");
        System.out.println("                                      ");
        System.out.println("  Hola!                             ");
        System.out.println("  Bienvenido a hacer Pizzas.        ");
        System.out.println("\n  Presiona 1 para Comenzar el juego.");
    }

    // ========================================
    //  MENU PRINCIPAL
    // ========================================
    private static void mostrarMenu() {
        System.out.println("\n+-------------------------------------+");
        if (jugando) {
            System.out.printf("|  PIZZATRON 3000   Puntos: %d | Cola: %d%n",
                              puntuacion, colaOrdenes.size());
        } else {
            System.out.println("|  PIZZATRON 3000                     |");
        }
        System.out.println("+-------------------------------------+");
        System.out.println("|  1. Comenzar                        |");
        System.out.println("|  2. Agregar Ingrediente             |");
        System.out.println("|  3. Deshacer                        |");
        System.out.println("|  4. Saltar Orden                    |");
        System.out.println("|  5. Enviar Pizza                    |");
        System.out.println("|  6. Ver mi Pizza                    |");
        System.out.println("|  0. Salir                           |");
        System.out.println("+-------------------------------------+");
        System.out.print("  Opcion: ");
    }

    //  OPCION 1 - COMENZAR
    //  Cola Ordenes + Orden actual + PizzaBuilder
    private static void opcionComenzar() {
        if (jugando) {
            System.out.println("  El juego ya esta en curso!");
            return;
        }

        System.out.println("\n  Comienza el juego!");
        System.out.println("  Cargando cola de ordenes...");

        // Cargar 3 ordenes en la cola
        colaOrdenes.clear();
        builder.reset();
        puntuacion = 0;

        for (int i = 0; i < 3; i++) colaOrdenes.add(ordenCtrl.generarOrden());

        jugando = true;
        siguienteOrden();
    }

    //  OPCION 2 - AGREGAR INGREDIENTE
    //  Cual ing: 1.masa 2.salsa 3.pescado 4.camo 5.alga
    private static void opcionAgregarIngr() {
        if (!jugando) { System.out.println("  Primero presiona 1 para Comenzar."); return; }

        System.out.println("\n  -- AGREGAR INGREDIENTE --");
        mostrarOrdenActual();
        ordenCtrl.mostrarCatalogo();

        System.out.println("\n  Que tipo quieres agregar?");
        System.out.println("    1. Base (masa)");
        System.out.println("    2. Salsa");
        System.out.println("    3. Topping");
        System.out.print("  Tipo: ");
        String tipo = scanner.nextLine().trim();

        switch (tipo) {
            case "1": elegirBase();    break;
            case "2": elegirSalsa();   break;
            case "3": elegirTopping(); break;
            default:  System.out.println("  Tipo invalido.");
        }
    }

    private static void elegirBase() {
        Base[] bases = ordenCtrl.getBases();
        System.out.println("  Cual base?");
        for (int i = 0; i < bases.length; i++)
            System.out.printf("    %d. %s%n", i+1, bases[i].getNombre());
        System.out.print("  Numero: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx >= 0 && idx < bases.length) builder.agregarIngrediente(bases[idx]);
            else System.out.println("  Numero invalido.");
        } catch (NumberFormatException e) { System.out.println("  Numero invalido."); }
    }

    private static void elegirSalsa() {
        Salsa[] salsas = ordenCtrl.getSalsas();
        System.out.println("  Cual salsa?");
        for (int i = 0; i < salsas.length; i++)
            System.out.printf("    %d. %s%n", i+1, salsas[i].getNombre());
        System.out.print("  Numero: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx >= 0 && idx < salsas.length) builder.agregarIngrediente(salsas[idx]);
            else System.out.println("  Numero invalido.");
        } catch (NumberFormatException e) { System.out.println("  Numero invalido."); }
    }

    private static void elegirTopping() {
        Topping[] tops = ordenCtrl.getToppings();
        System.out.println("  Cual topping?");
        for (int i = 0; i < tops.length; i++)
            System.out.printf("    %d. %s%n", i+1, tops[i].getNombre());
        System.out.print("  Numero: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx >= 0 && idx < tops.length) builder.agregarIngrediente(tops[idx]);
            else System.out.println("  Numero invalido.");
        } catch (NumberFormatException e) { System.out.println("  Numero invalido."); }
    }
    //  OPCION 3 - DESHACER
    //  PizzaBuilder pop a la pila (stack)
    private static void opcionDeshacer() {
        if (!jugando) { System.out.println("  Primero presiona 1 para Comenzar."); return; }
        System.out.println("\n  -- DESHACER (pop del stack) --");
        builder.deshacer();
    }
    //  OPCION 4 - SALTAR ORDEN
    //  Borra el contenido del PizzaBuilder, saca la orden y llama siguiente
    private static void opcionSaltarOrden() {
        if (!jugando) { System.out.println("  Primero presiona 1 para Comenzar."); return; }
        if (ordenActual == null) { System.out.println("  No hay orden activa."); return; }
        System.out.println("\n  -- SALTAR ORDEN --");
        System.out.println("  Orden saltada (sin puntos).");
        builder.reset();   // borra el contenido del PizzaBuilder
        siguienteOrden();  // saca la orden y llama siguiente
    }

    //  OPCION 5 - ENVIAR PIZZA
    //  new Pizza() -> setBase -> setSalsa -> setIngr (desde el builder)
    //  llama siguiente orden
    private static void opcionEnviarPizza() {
        if (!jugando) { System.out.println("  Primero presiona 1 para Comenzar."); return; }
        if (ordenActual == null) { System.out.println("  No hay orden activa."); return; }

        System.out.println("\n  -- ENVIAR PIZZA --");
        try {
            // construirPizza hace: new Pizza() -> setBase() -> setSalsa() -> addTopping()
            Pizza pizzaArmada = builder.construirPizza();

            System.out.println("\n  Tu pizza:");
            System.out.println(pizzaArmada);
            System.out.println("\n  La orden pedia:");
            System.out.println(ordenActual.getPizzaDeseada());

            boolean ok = ordenCtrl.validarYPuntuar(pizzaArmada, ordenActual);
            if (ok) puntuacion += 100;

            builder.reset();
            siguienteOrden();  // llama siguiente

        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    //  OPCION 6 - VER MI PIZZA
    //  Verifica que tenga Masa, Toppings y Salsa, esta adentro del stack de PizzaBuilder
    private static void opcionVerMiPizza() {
        if (!jugando) { System.out.println("  Primero presiona 1 para Comenzar."); return; }
        System.out.println("\n  -- VER MI PIZZA --");
        System.out.println("  (Verifica Masa, Toppings y Salsa del stack de PizzaBuilder)");
        builder.verMiPizza();
    }

    //  SIGUIENTE ORDEN - saca de la cola (poll FIFO)
    private static void siguienteOrden() {
        if (colaOrdenes.isEmpty()) {
            System.out.println("\n  Se acabaron las ordenes!");
            System.out.println("  Puntuacion final: " + puntuacion);
            jugando = false;
            ordenActual = null;
            return;
        }
        ordenActual = colaOrdenes.poll();  // saca el primero de la cola (FIFO)
        System.out.println("\n  +-- NUEVA ORDEN ----------------------+");
        System.out.println(ordenActual);
        System.out.printf("  +------------------- Q%.2f a cobrar --+%n",
                          ordenActual.getPizzaDeseada().calcularPrecio());
    }

    // Muestra la orden activa en pantalla
    private static void mostrarOrdenActual() {
        if (ordenActual == null) return;
        System.out.println("\n  Orden activa:");
        System.out.println(ordenActual);
    }
}
