package prog2.model;

import prog2.vista.BiblioException;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Llista especialitzada per a objectes de tipus Exemplar.
 */
public class LlistaExemplars extends Llista<Exemplar> implements Serializable {

    /**
     * Constructor.
     */
    public LlistaExemplars() {
        super();
    }

    /**
     * Afegeix un exemplar a la llista si el seu identificador no existeix ja.
     *
     * @param exemplar Exemplar a afegir
     * @throws BiblioException Si ja existeix un exemplar amb el mateix id
     */
    @Override
    public void afegir(Exemplar exemplar) throws BiblioException {
        String idNou = exemplar.getId();

        Iterator<Exemplar> it = llista.iterator();
        while (it.hasNext()) {
            Exemplar exe = it.next();
            if (exe.getId().equals(idNou)) {
                throw new BiblioException("Aquest exemplar ja existeix");
            }
        }
        llista.add(exemplar);
    }

    /**
     * Comprova si la llista conté un exemplar amb l'identificador indicat.
     *
     * @param id Identificador a cercar
     * @return true si existeix un exemplar amb aquell id
     *         false en cas contrari
     */
    public boolean contains(String id) {
        Iterator<Exemplar> it = llista.iterator();
        while (it.hasNext()) {
            Exemplar exemplarAux = it.next();
            if (exemplarAux.getId().equals(id)) return true;
        }
        return false;
    }

    /**
     * Elimina de la llista l'exemplar amb el mateix identificador que el passat per paràmatre.
     *
     * @param exemplar Exemplar de referència per a la cerca (s'usa el seu id)
     * @throws BiblioException Si no estroba cap exemplar amb aquell id
     */
    public void esborrar(Exemplar exemplar) throws BiblioException {
        Iterator<Exemplar> it = llista.iterator();
        while (it.hasNext()) {
            Exemplar exemplarAux = it.next();
            if (exemplarAux.getId().equals(exemplar.getId())) {
                it.remove();
                return;
            }
        }
        throw new BiblioException("No existeix");
    }
}
