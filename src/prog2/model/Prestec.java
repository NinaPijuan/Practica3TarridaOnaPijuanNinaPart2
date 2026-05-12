package prog2.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe abstracta que representa un préstec d'un exemplar a un usuari.
 */
public abstract class Prestec implements InPrestec, Serializable {
    private Exemplar exemplar;
    private Usuari usuari;
    private long durada; // Mil·lisegons
    private Date dataCreacio;
    private Date dataLimitRetorn;
    private boolean retornat;

    /**
     * Constructor.
     *
     * @param exemplar    Exemplar que es presta
     * @param usuari      Usuari que realitza el préstec
     * @param durada      Durada màxima del préstec en segons
     * @param dataCreacio Data en què s'ha creat el préstec
     */
    public Prestec(Exemplar exemplar, Usuari usuari, long durada, Date dataCreacio) {
        this.exemplar = exemplar;
        this.usuari = usuari;
        this.durada = durada * 1000; // Es guarda en mil·lisegons
        this.dataCreacio = dataCreacio;
        this.dataLimitRetorn = new Date();
        this.dataLimitRetorn.setTime(dataCreacio.getTime() + durada * 1000);
        retornat = false; // Per definició a false
    }

    /**
     * Setter de l'exemplar
     * @param exemplar
     */
    @Override
    public void setExemplar(Exemplar exemplar) { this.exemplar = exemplar; }

    /**
     * Getter de l'exemplar
     * @return exemplar
     */
    @Override
    public Exemplar getExemplar() { return exemplar; }

    /**
     * Setter de l'usuari
     * @param usuari
     */
    @Override
    public void setUsuari(Usuari usuari) { this.usuari = usuari; }

    /**
     * Getter de l'usuari
     * @return usuari
     */
    @Override
    public Usuari getUsuari() { return usuari; }

    /**
     * Setter de la data de creació
     * @param data
     */
    @Override
    public void setDataCreacio(Date data) { this.dataCreacio = data; }

    /**
     * Getter de la data de creació
     * @return dataCreacio
     */
    @Override
    public Date getDataCreacio() { return dataCreacio; }

    /**
     * Setter de la data de límit retorn
     * @param data
     */
    @Override
    public void setDataLimitRetorn(Date data) { this.dataLimitRetorn = data; }

    /**
     * Getter de la data de límit retorn
     * @return dataLimitRetorn
     */
    @Override
    public Date getDataLimitRetorn() { return dataLimitRetorn; }

    /**
     * Retorna un String que identifica el tipus de préstec.
     * Mètode abstracte que definim en cada subclasse.
     *
     * @return Cadena amb el tipus de préstec ("Normal" o "Llarg")
     */
    @Override
    public abstract String tipusPrestec();

    /**
     * Setter del boolean retornat
     * @param retornat
     */
    @Override
    public void setRetornat(boolean retornat) { this.retornat = retornat; }

    /**
     * Getter del boolean retornat
     * @return retornat
     */
    @Override
    public boolean getRetornat() { return retornat; }

    /**
     * Marca el préstec com a retornat i actualitza l'estat dels objectes associats.
     */
    @Override
    public void retorna() {
        // L'excepció sobre si ha estat retornat es llença a Dades.java

        exemplar.setDisponible(true);
        retornat = true;

        if (this instanceof PrestecLlarg) usuari.setNumPrestecsLlargs(usuari.getNumPrestecsLlargs() - 1);
        else usuari.setNumPrestecsNormals(usuari.getNumPrestecsNormals() - 1);
    }

    /**
     * Retorna la durada del préstec en mil·lisegons
     *
     * @return Durada del préstec
     */
    @Override
    public long duradaPrestec(){ return durada; }

    /**
     * Comprova si el préstec ha superat la seva data límit de retorn sense haver
     * estat retornat.
     *
     * @return true si el préstec no ha estat retornat i la data límit de
     *         retorn ja ha passat respecte a la data actual
     *         false en qualsevol altre cas
     */
    @Override
    public boolean prestecEndarrerit() {
        boolean endarrerit = false;

        if(!retornat){
            // Crear date amb data actual
            Date avui = new Date();

            // Mirem si la data de retorn ja ha passat
            if (dataLimitRetorn.before(avui)){ endarrerit = true; }
        }

        return endarrerit;
    }

    /**
     * Retorna una representació textual del préstec amb tots els seus camps.
     *
     * @return Cadena amb el tipus, exemplar, usuari, dates de creació i límit de
     *         retorn, i si ha estat retornat
     */
    @Override
    public String toString(){
        return "Tipus=" + tipusPrestec() + ", Exemplar=" + exemplar.getTitol() + ", Usuari=" + usuari.getNom()+ ", Data de creació="
                + dataCreacio + ", Data límit de retorn=" + dataLimitRetorn + ", Retornat="
                + retornat;
    }
}
