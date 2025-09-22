
/**
 * Write a description of class GanaleAlDado here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GanaleAlDado {
    private DadoLCR dado1, dado2;
    private int tirosRealizados;
    private int fichasObtenidas;

    public GanaleAlDado() {
        dado1 = new DadoLCR();
        dado2 = new DadoLCR();
        tirosRealizados = 0;
        fichasObtenidas = 0;
    }

    /**
     * El juego termina si se hicieron 10 tiros o si se obtuvieron 6 o más fichas.
     */
    public boolean finDelJuego() {
        return (tirosRealizados >= 10 || fichasObtenidas >= 6);
    }

    /**
 * Lanza los dos dados y aplica las reglas,
 * pero solo si el juego no ha terminado.
 */
public void lanzar() {
    // Si ya terminó el juego, no hacer nada
    if (finDelJuego()) {
        return;
    }

    char c1 = dado1.lanzar();
    char c2 = dado2.lanzar();
    tirosRealizados++;

    // Primero sumo/resto fichas según los símbolos
    fichasObtenidas += deltaPorCara(c1);
    fichasObtenidas += deltaPorCara(c2);

    // Si salió al menos un 'C', se duplican las fichas (solo una vez)
    if (c1 == 'C' || c2 == 'C') {
        fichasObtenidas *= 2;
    }

    // No dejar que las fichas bajen de 0
    if (fichasObtenidas < 0) {
        fichasObtenidas = 0;
    }
}

    /**
     * Reglas para cada cara del dado.
     */
    private int deltaPorCara(char cara) {
        switch (cara) {
            case '*': return 1;   // punto
            case 'L': return -1;
            case 'R': return -2;
            case 'C': return 0;   // no suma/resta, solo duplica
            default:  return 0;
        }
    }

    // ==== Métodos de consulta ====

    public int getFichas() {
        return fichasObtenidas;
    }

    public int getTiros() {
        return tirosRealizados;
    }

    public char getCaraDado1() {
        return dado1.getValor();
    }

    public char getCaraDado2() {
        return dado2.getValor();
    }

    /**
     * Reinicia el juego.
     */
    public void reiniciar() {
        tirosRealizados = 0;
        fichasObtenidas = 0;
    }

    
}