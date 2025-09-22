
/**
 * class LCRDadoView.
 * 
 * @author (Jeshua Quintero) 
 * @version (1.0)
 */
public class LCRDadoView {
    private int xPos, yPos;
    private int size;
    private boolean visible;

    private Square cuadro;   // marco del dado
    private Circle punto;    // círculo central para el "*"

    // Guarda la cara actualmente mostrada
    private char caraActual;

    /**
     * Constructor
     */
    public LCRDadoView(int x, int y, int tamano) {
        xPos = x;
        yPos = y;
        size = tamano;
        visible = false;
        caraActual = ' ';

        // Marco del dado
        cuadro = new Square();
        cuadro.changeSize(size);
        cuadro.setPosition(xPos, yPos);
        cuadro.changeColor("blue");

        // Punto central (tamaño proporcional y posición centrada)
        punto = new Circle();
        punto.changeSize(size / 4);
        punto.setPosition(xPos + size/2 - size/8, yPos + size/2 - size/8);
        punto.changeColor("yellow");
    }

    /**
     * Hace visible el dado vacío (sin cara).
     */
    public void mostrar() {
        visible = true;
        cuadro.makeVisible();
    }

    /**
     * Oculta el dado.
     */
    public void ocultar() {
        punto.makeInvisible();
        Canvas.getCanvas().erase(this);
        visible = false;
        caraActual = ' ';
    }

    /**
     * Cambia la posición del dado.
     */
    public void setPosicion(int x, int y) {
        xPos = x;
        yPos = y;

        // Actualiza posición de los objetos que sí son objetos reales
        cuadro.setPosition(xPos, yPos);
        punto.setPosition(xPos + size/2 - size/8, yPos + size/2 - size/8);

        // Si está visible, redibuja la cara actual
        if (visible) {
            dibujarCaraActual();
        }
    }

    // ---------------- Métodos públicos de cada cara ----------------

    public void mostrarPunto() {
        if (!visible) mostrar();
        caraActual = '*';
        dibujarCaraActual();
    }

    public void mostrarL() {
        if (!visible) mostrar();
        caraActual = 'L';
        dibujarCaraActual();
    }

    public void mostrarC() {
        if (!visible) mostrar();
        caraActual = 'C';
        dibujarCaraActual();
    }

    public void mostrarR() {
        if (!visible) mostrar();
        caraActual = 'R';
        dibujarCaraActual();
    }

    // ---------------- Método auxiliar para centralizar el dibujo ----------------

    private void dibujarCaraActual() {
        Canvas canvas = Canvas.getCanvas();
        canvas.erase(this);

        // Asegurar que el punto esté en la posición correcta
        punto.setPosition(xPos + size/2 - size/8, yPos + size/2 - size/8);

        switch (caraActual) {
            case '*':
                // Mostrar el punto central
                punto.makeVisible();
                break;

            case 'L':
            case 'C':
            case 'R':
                // Ocultar el punto si estaba visible
                punto.makeInvisible();

                int fontSize = size / 2;

                // Calcular centro del cuadrado
                int xCentro = xPos + size / 2;
                int yCentro = yPos + size / 2;

                // Ajuste para centrar la letra
                int xTexto = xCentro - fontSize / 3;
                int yTexto = yCentro + fontSize / 3;

                canvas.drawString(this, "yellow", String.valueOf(caraActual),
                                  xTexto, yTexto, fontSize);
                break;

            default:
                // ninguna cara
                punto.makeInvisible();
                break;
        }
    }
}
