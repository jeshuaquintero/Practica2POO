import java.util.Random;

/**
 * Write a description of class Dado here.
 *
 * @author (Cecy Curlango Rosas)
 * @version (1.0 agosto 2025)
 */
public class Dado
{
    private int caras;
    private int valor;

    /**
     * Constructor for objects of class Dado
     */
    public Dado()
    {
        // initialise instance variables
        caras = 6;
        valor = 1;
    }

    public Dado(int lados) {
        caras = lados;
        valor = 1;
    }
    public int getValor() {
        return valor;
    }
    
    public void setValor(int nuevoValor) {
        valor = nuevoValor;
    }
    
    public int getCaras() {
        return caras;
    }
    public void setCaras(int caras) {
        this.caras = caras;
    }
    public int lanzar() {
        Random aleatorio;
        aleatorio = new Random();
        
        valor = aleatorio.nextInt(caras) +1;
        return valor;
    }
    
}