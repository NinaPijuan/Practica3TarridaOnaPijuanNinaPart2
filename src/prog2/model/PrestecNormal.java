package prog2.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Representa un préstec de durada normal a la biblioteca.
 */
public class PrestecNormal extends Prestec implements Serializable {

    /**
     * Constructor
     *
     * @param exemplar    Exemplar que es presta
     * @param usuari      Usuari que realitza el préstec
     * @param dataCreacio Data en què es crea el préstec
     */
    public PrestecNormal(Exemplar exemplar, Usuari usuari, Date dataCreacio) {
        super(exemplar, usuari, 70, dataCreacio);
    }

    /**
     * Retorna el tipus de préstec.
     *
     * @return La cadena "Normal"
     */
    @Override
    public String tipusPrestec() {
        return "Normal";
    }

    /**
     * Retorna la durada del préstec.
     * Per a un préstec normal, la durada és de 70 segons.
     */
    @Override
    public long duradaPrestec() {
        return super.duradaPrestec();
    }
}
