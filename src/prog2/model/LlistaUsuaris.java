package prog2.model;

import prog2.vista.BiblioException;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Llista especialitzada per a objectes de tipus Usuari.
 */
public class LlistaUsuaris extends Llista<Usuari> implements Serializable {

    /**
     * Constructor.
     */
    public LlistaUsuaris() {
        super();
    }

    /**
     * Afegeix un usuari a la llista si el seu correu electrònic no existeix ja.
     *
     * @param usuari Usuari a afegir
     * @throws BiblioException Si ja existeix un usuari amb el mateix email
     */
    @Override
    public void afegir(Usuari usuari) throws BiblioException{
        String email = usuari.getEmail();

        Iterator<Usuari> it = llista.iterator();
        while(it.hasNext()){
            Usuari usuariAux = it.next();
            if( usuariAux.getEmail().equals(email) ){ throw new BiblioException("Aquest usuari ja existeix"); }
        }

        llista.add(usuari);
    }

    /**
     * Comprova si la llista conté un usuari amb el correu electrònic indicat.
     *
     * @param email Correu electrònic a cercar
     * @return true si existeix un usuari amb aquell email
     *         false en cas contrari
     */
    public boolean contains(String email) {
        Iterator<Usuari> it = llista.iterator();
        while (it.hasNext()) {
            Usuari usuariAux = it.next();
            if (usuariAux.getEmail().equals(email)) return true;
        }
        return false;
    }

    /**
     * Elimina de la llista l'usuari amb el mateix correu que el passat per paràmetre.
     *
     * @param usuari usuari de rèferencia per a la cerca (s'usa el seu email)
     * @throws BiblioException Si no es troba cap usuari amb aquell email
     */
    public void esborrar(Usuari usuari) throws BiblioException {
        Iterator<Usuari> it = llista.iterator();
        while (it.hasNext()) {
            Usuari usuariAux = it.next();
            if (usuariAux.getEmail().equals(usuari.getEmail())) {
                it.remove();
                return;
            }
        }
        throw new BiblioException("No existeix");
    }
}
