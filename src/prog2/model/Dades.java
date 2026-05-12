package prog2.model;

import prog2.vista.BiblioException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Classe de model central que gestiona totes les dades de la biblioteca.
 */
public class Dades implements InDades, Serializable {
    private LlistaUsuaris llistaUsuaris;
    private LlistaExemplars llistaExemplars;
    private LlistaPrestecs llistaPrestecs;

    /**
     * Constructor.
     */
    public Dades() {
        this.llistaUsuaris = new LlistaUsuaris();
        this.llistaExemplars = new LlistaExemplars();
        this.llistaPrestecs = new LlistaPrestecs();
    }

    /**
     * Crea un exemplar i l'afegeix a la llista d'exemplars
     *
     * @param id                Identificador únic de l'exemplar
     * @param titol             Títol del document
     * @param autor             Autor del document
     * @param admetPrestecLlarg true si l'exemplar permet préstecs llargs
     *                          false en cas contrari
     * @throws BiblioException Si ja existeix un exemplar amb el mateix id
     */
    @Override
    public void afegirExemplar(String id, String titol, String autor, boolean admetPrestecLlarg) throws BiblioException {
        // Creem un exemplar nou i el mètode afegir de LlistaExemplars gestiona la resta
        Exemplar exemplar = new Exemplar(id, titol, autor, admetPrestecLlarg);
        llistaExemplars.afegir(exemplar);
    }

    /**
     * Retorna tots els exmeplars registrats a la biblioteca.
     *
     * @return ArrayList amb tots els objectes Exemplar registrats
     */
    @Override
    public ArrayList<Exemplar> recuperaExemplars() {
        return llistaExemplars.getArrayList();
    }

    /**
     * Crea un usuari (estudiant o professor) i l'afegeix a la llista d'usuaris
     *
     * @param email       Correu electrònic de l'usuari (identificador únic)
     * @param nom         Nom de l'usuari
     * @param adreca      Adreça de l'usuari
     * @param esEstudiant true per crear un Estudiant
     *                    false per crear un Professor
     * @throws BiblioException Si ja existeix un usuari amb el mateix email
     */
    @Override
    public void afegirUsuari(String email, String nom, String adreca, boolean esEstudiant) throws BiblioException {

        // Creem un usuari nou (de tipus concret) i el mètode afegir de la LlistaUsuaris fa la resta
        if(esEstudiant){
            Estudiant estudiant = new Estudiant(email, nom, adreca);
            llistaUsuaris.afegir(estudiant);
        }
        else{
            Professor professor = new Professor(email, nom, adreca);
            llistaUsuaris.afegir(professor);
        }

    }

    /**
     * Retorna tots els usuaris registrats a la biblioteca.
     *
     * @return ArrayList amb tots els objectes Usuari registrats
     */
    @Override
    public ArrayList<Usuari> recuperaUsuaris() {
        return llistaUsuaris.getArrayList();
    }

    /**
     * Crea un nou préstec i l'afegeix a la llista de préstecs.
     * Ha de fer diferents comprovacions que poden llançar excepcions.
     * Quan s'afegeix el préstec, s'han de tenir en compte les posicions d'exemplar
     * i usuari dins dels seus ArrayLists
     *
     * @param exemplarPos Índex de l'exemplar dins de la seva llista
     * @param usuariPos   Índex de l'usuari dins de la seva llista
     * @param esLlarg     true per crear un PrestecLlarg
     *                    false per crear un PrestecNormal
     * @throws BiblioException Si no es compleix alguna de les condicions de validació
     */
    @Override
    public void afegirPrestec(int exemplarPos, int usuariPos, boolean esLlarg) throws BiblioException {
        // Llista té un mètode (getAt()) que retorna l'objecte en la posició donada

        // Obtenim l'exemplar i comprovem que està disponible
        Exemplar exemplar = llistaExemplars.getAt(exemplarPos);
        if(!exemplar.isDisponible()){ throw new BiblioException("Exemplar no disponible"); }

        // Si és préstec llarg, mirem si l'exemplar ho permet
        if(esLlarg) {
            if (!exemplar.getAdmetPrestecLlarg()) {
                throw new BiblioException("L'exemplar no admet un préstec llarg");
            }
        }

        // Obtenim l'usuari i guardem el email
        Usuari usuari = llistaUsuaris.getAt(usuariPos);
        String emailUsuari = usuari.getEmail();

        // Busquem l'usuari dins de llistaPrestecs, i si el trobem mirem si el préstec està endarrerit
        Iterator<Prestec> it = llistaPrestecs.getArrayList().iterator();
        while( it.hasNext() ){
                Prestec pres = it.next();
                if( pres.getUsuari().getEmail().equals(emailUsuari) ) {
                    if (pres.prestecEndarrerit()) {
                        throw new BiblioException("L'usuari té préstecs endarrerits");
                    }
                }
        }

        // Comprovem que l'usuari no superi el límit de préstecs i creem el préstec del tipus concret
        if(esLlarg){
            if (usuari.getNumPrestecsLlargs() == usuari.getMaxPrestecsLlargs()){ throw new
                    BiblioException("L'usuari supera el número màxim de préstecs llargs"); }
            PrestecLlarg prestec = new PrestecLlarg(exemplar, usuari, new Date());
            llistaPrestecs.afegir(prestec);
            usuari.setNumPrestecsLlargs(usuari.getNumPrestecsLlargs() + 1);
        }
        else{
            if (usuari.getNumPrestecsNormals() == usuari.getMaxPrestecsNormals()) { throw new
                    BiblioException("L'usuari supera el número màxim de préstecs normals"); }
            PrestecNormal prestec = new PrestecNormal(exemplar, usuari, new Date());
            llistaPrestecs.afegir(prestec);
            usuari.setNumPrestecsNormals(usuari.getNumPrestecsNormals() + 1);
        }

        // Marquem l'exemplar com a no disponible
        exemplar.setDisponible(false);
    }

    /**
     * Marca un préstec existent com a retornat.
     * El préstec s'identifica amb la seva posició dins de l'ArrayList.
     *
     * @param position índex del préstec dins de la llista de préstecs
     * @throws BiblioException Si el préstec indicat ja havia estat retornat
     */
    @Override
    public void retornarPrestec(int position) throws BiblioException {
        // Llista té un mètode (getAt()) que retorna l'objecte en la posició donada
        Prestec prestec = llistaPrestecs.getAt(position);

        // Llancem excepció si l'exemplar ja ha estat retornat
        if (prestec.getRetornat()){ throw new BiblioException("L'exemplar ja ha estat retornat"); }

        // Si no, el retornem amb el mètode de préstec
        prestec.retorna();
    }

    /**
     * Retorna tots els préstecs registrats (actius i retornats).
     *
     * @return Arraylist amb tots els objectes Prestec
     */
    @Override
    public ArrayList<Prestec> recuperaPrestecs() {
        return llistaPrestecs.getArrayList();
    }

    /**
     * Retorna únicament els préstecs que encara no han estat retornats.
     *
     * @return ArrayList amb els Prestec no retornats
     */
    @Override
    public ArrayList<Prestec> recuperaPrestecsNoRetornats() {
        ArrayList<Prestec> noRetornats = new ArrayList<>();

        Iterator<Prestec> it = llistaPrestecs.getArrayList().iterator();
        while(it.hasNext()){
            Prestec pres = it.next();
            if( !pres.getRetornat() ) {
                noRetornats.add(pres);
            }
        }
        return noRetornats;
    }
}
