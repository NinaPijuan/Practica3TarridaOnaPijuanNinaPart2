package prog2.adaptador;

import prog2.model.Dades;
import prog2.model.Exemplar;
import prog2.model.Prestec;
import prog2.model.Usuari;
import prog2.vista.BiblioException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Adaptador entre la vista i el model de l'aplicació BiblioUB
 */
public class Adaptador implements Serializable {
    /** Objecte del model que conté totes les dades de la biblioteca. */
    private Dades dades;

    /** Constructor */
    public Adaptador() {
        this.dades = new Dades();
    }

    // GETTERS DE LLISTES DE STRINGS

    /**
     * Retorna tots els exemplars registrats com a llista de Strings.
     *
     * @return List de cadenes, una per exemplar
     */
    public List<String> recuperarExemplars() {
        ArrayList<Exemplar> exemplars = dades.recuperaExemplars();
        ArrayList<String> llista = new ArrayList<>();
        Iterator<Exemplar> it = exemplars.iterator();
        while(it.hasNext()) {
            llista.add(it.next().toString());
        }
        return llista;
    }

    /**
     * Retorna tots els usuaris registrats com a llista de Strings.
     *
     * @return List de cadenes, una per usuari
     */
    public List<String> recuperarUsuaris() {
        ArrayList<Usuari> usuaris = dades.recuperaUsuaris();
        ArrayList<String> llista = new ArrayList<>();
        Iterator<Usuari> it = usuaris.iterator();
        while(it.hasNext()) {
            llista.add(it.next().toString());
        }
        return llista;
    }

    /**
     * Retorna tots els préstecs (actius i retornats) com a llista de Strings.
     *
     * @return List de cadenes, una per préstec
     */
    public List<String> recuperarPrestecs() {
        ArrayList<Prestec> prestecs = dades.recuperaPrestecs();
        ArrayList<String> llista = new ArrayList<>();
        Iterator<Prestec> it = prestecs.iterator();
        while(it.hasNext()) {
            llista.add(it.next().toString());
        }
        return llista;
    }

    /**
     * Retorna els préstecs que encara no han estat retornats com a llista de Strings.
     *
     * @return List de cadenes amb els préstecs no retornats
     */
    public List<String> recuperarPrestecsNoRetornats() {
        ArrayList<Prestec> prestecsNR = dades.recuperaPrestecsNoRetornats();
        ArrayList<String> llista = new ArrayList<>();
        Iterator<Prestec> it = prestecsNR.iterator();
        while(it.hasNext()) {
            llista.add(it.next().toString());
        }
        return llista;
    }


    // EXEMPLARS

    /**
     * Crea i afegeix un exemplar a la llista d'exemplars.
     *
     * @param id                 Identificador únic de l'exemplar
     * @param titol              Títol del document
     * @param autor              Autor del document
     * @param admetPrestecLlarg  true si l'exemplar permet préstecs llargs
     *                           false en cas contrari (només préstecs normals)
     * @throws BiblioException Si ja existeix un exemplar amb el mateix id
     */
    public void afegirExemplar(String id, String titol, String autor, boolean admetPrestecLlarg) throws BiblioException {
        dades.afegirExemplar(id, titol, autor, admetPrestecLlarg);
    }

    /**
     * Retorna el nombre total d'exemplars registrats.
     *
     * @return Nombre d'exemplars
     */
    public int getNumExemplars() {
        return dades.recuperaExemplars().size();
    }

    // USUARIS

    /**
     * Crea i afegeix un usuari a la llista d'usuaris.
     *
     * @param email         Correu electrònic de l'usuari (identificador únic)
     * @param nom           Nom de l'usuari
     * @param adreca        Adreça de l'usuari
     * @param esEstudiant   true per crear un estudiant
     *                      false per crear un professor
     * @throws BiblioException Si ja existeix un usuari amb el mateix email
     */
    public void afegirUsuari(String email, String nom, String adreca, boolean esEstudiant) throws BiblioException {
        dades.afegirUsuari(email, nom, adreca, esEstudiant);
    }


    /**
     * Retorna el nombre total d'usuaris registrats.
     *
     * @return Nombre d'usuaris
     */
    public int getNumUsuaris() {
        return dades.recuperaUsuaris().size();
    }

    // PRÉSTECS

    /**
     * Crea un préstec per a un exemplar i un usuari donats pels seus índexs.
     *
     * @param exemplarPos índex de l'exemplar dins de la seva llista
     * @param usuariPos   índex de l'usuari dins de la seva llista
     * @param esLlarg     true per crear un préstec llarg
     *                    false per crear un préstec normal
     * @throws BiblioException Si no es complex alguna de les condicions de validació
     */
    public void afegirPrestec(int exemplarPos, int usuariPos, boolean esLlarg) throws BiblioException {
        dades.afegirPrestec(exemplarPos, usuariPos, esLlarg);
    }

    /**
     * Marca el préstec  de la posició com a retornat.
     *
     * @param position índex del préstec dins de la llista de préstecs
     * @throws BiblioException Si el préstec ja havia estat retornat
     */
    public void retornarPrestec(int position) throws BiblioException {
        dades.retornarPrestec(position);
    }

    /**
     * Retorna el nombre total de préstecs registrats (actius i retornats).
     *
     * @return Nombre total de préstecs
     */
    public int getNumPrestecs() {
        return dades.recuperaPrestecs().size();
    }

    // PERSISTÈNCIA

    /**
     * Serialitza l'objecte Adaptador (i totes les dades que conté)
     * al fitxer indicat. Si el fitxer ja existeix, serà sobreescrit.
     *
     * @param camiDesti Ruta del fitxer de destinació
     * @throws BiblioException Si es produeix qualsevol error d'entrada/sortida
     */
    public void guardaDades(String camiDesti) throws BiblioException {
        // Creem el fitxer
        File fitxer = new File(camiDesti);

        try {
            // Creem el fos i oos
            FileOutputStream fos = new FileOutputStream(fitxer);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Escrivim l'Adaptador (this)
            oos.writeObject(this);

            // Tanquem fos i oos
            fos.close();
            oos.close();

        } catch (IOException e) {
            throw new BiblioException("No s'ha pogut guardar al fitxer: " + e.getMessage());
        }
    }

    /**
     * Deserialitza les dades d'un fitxer generat prèviament per guardaDades(String)
     * i les carrega en l'objecte actual.
     *
     * @param camiOrigen Ruta del fitxer d'origen
     * @throws BiblioException Si el fitxer no existeix, no es pot llegir o
     *                         conté dades incompatibles
     */
    public void carregaDades(String camiOrigen) throws BiblioException {
        // Inicialitzem les variables per poder veure-les dins del try
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(camiOrigen);
            ois = new ObjectInputStream(fis);
            Adaptador adaptadorAux = (Adaptador) ois.readObject();
            this.dades = adaptadorAux.dades;

        } catch (FileNotFoundException e) {
            throw new BiblioException("El fitxer no existeix");
        } catch (ClassNotFoundException e) {
            throw new BiblioException("No s'han pogut llegir les dades del fitxer");
        } catch (IOException e) {
            throw new BiblioException("Error llegint el fitxer: " + e.getMessage());
        }

        // Provem de tancar els canals
        finally {
            try {
                fis.close();
                ois.close();
            } catch (NullPointerException e) {
                throw new BiblioException("El fitxer no existeix");
            } catch (IOException e) {
                throw new BiblioException("No s'ha pogut tancar el fitxer");
            }
        }
    }
}
