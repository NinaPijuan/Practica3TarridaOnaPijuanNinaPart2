package prog2.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Representa un préstec de llarga durada a la biblioteca.
 */
public class PrestecLlarg extends Prestec implements Serializable {

    /**
     * Constructor.
     *
     * @param exemplar    Exemplar que es presta
     * @param usuari      Usuari que realitza el préstec
     * @param dataCreacio Data en què es crea el préstec
     */
    public PrestecLlarg(Exemplar exemplar, Usuari usuari, Date dataCreacio) {
        super(exemplar, usuari, 140, dataCreacio);
    }

    /**
     * Retorna el tipus d'aquest préstec.
     *
     * @return La cadena "Llarg"
     */
    @Override
    public String tipusPrestec() {
        return "Llarg";
    }

    /**
     * Retorna la durada del préstec.
     * Per a un préstec llarg, la durada és de 140 segons.
     */
    @Override
    public long duradaPrestec() {
        return super.duradaPrestec();
    }
}
