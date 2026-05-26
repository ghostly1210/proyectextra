//  Orden  —  pedido de un cliente
public class Orden {

    private static int contadorId = 1;

    private int    id;
    private Pizza  pizzaDeseada;
    private String estado;        // "PENDIENTE" | "COMPLETADA" | "FALLIDA"
    private String nombreCliente;

    public Orden(Pizza pizzaDeseada, String nombreCliente) {
        this.id            = contadorId++;
        this.pizzaDeseada  = pizzaDeseada;
        this.estado        = "PENDIENTE";
        this.nombreCliente = nombreCliente;
    }

    public int    getId()            { return id;            }
    public Pizza  getPizzaDeseada()  { return pizzaDeseada;  }
    public String getEstado()        { return estado;        }
    public String getNombreCliente() { return nombreCliente; }

    public void setEstado(String e)  { this.estado = e; }

    @Override
    public String toString() {
        return "  Orden #" + id + " — Cliente: " + nombreCliente + "\n" + pizzaDeseada.toString();
    }
}
