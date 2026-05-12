package prog2.model;

import java.io.Serializable;

/**
 * Representa un exemplar físic d'un document a la biblioteca
 */
public class Exemplar implements InExemplar, Serializable {
    private String id;
    private String titol;
    private String autor;
    private boolean admetPrestecLlarg;
    private boolean disponible;

    /**
     * Constructor.
     *
     * @param id                Identificador únic de l'exemplar
     * @param titol             Títol del document
     * @param autor             Autor del document
     * @param admetPrestecLlarg true si l'exemplar permet préstecs llargs
     *                          false en cas contrari
     */
    public Exemplar(String id, String titol, String autor, boolean admetPrestecLlarg) {
        this.id = id;
        this.titol = titol;
        this.autor = autor;
        this.admetPrestecLlarg = admetPrestecLlarg;
        this.disponible = true; // Disponible per definició
    }

    /**
     * Setter de l'id
     * @param id
     */
    @Override
    public void setId(String id) { this.id = id; }

    /**
     * Getter de l'id
     * @return id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Setter del títol
     * @param titol
     */
    @Override
    public void setTitol(String titol) { this.titol = titol; }

    /**
     * Getter del títol
     * @return titol
     */
    @Override
    public String getTitol() {
        return titol;
    }

    /**
     * Setter de l'autor
     * @param autor
     */
    @Override
    public void setAutor(String autor) { this.autor = autor; }

    /**
     * Getter de l'autor
     * @return autor
     */
    @Override
    public String getAutor() {
        return autor;
    }

    /**
     * Setter del boolean admetPrestecLlarg
     * @param admetPrestecLlarg
     */
    @Override
    public void setAdmetPrestecLlarg(boolean admetPrestecLlarg) { this.admetPrestecLlarg = admetPrestecLlarg; }

    /**
     * Getter del boolean admetPrestecLlarg
     * @return admetPrestecLlarg
     */
    @Override
    public boolean getAdmetPrestecLlarg() {
        return admetPrestecLlarg;
    }

    /**
     * Setter del boolean disponible
     * @param disponible
     */
    public void setDisponible(boolean disponible){ this.disponible = disponible; }

    /**
     * Indica si l'exemplar està disponible
     *
     * @return true si l'exemplar està disponible
     *         false en cas contrari
     */
    public boolean isDisponible(){ return disponible; }

    /**
     * Retorna una representació textual de l'exemplar amb tots els seus camps.
     *
     * @return Cadena de text amb l'id, títol, autor, si admet préstecs llargs
     *         i si està disponible
     */
    @Override
    public String toString(){
        return "Id=" + id + ", Títol=" + titol + ", Autor=" + autor + ", Admet Préstec Llarg="
                + admetPrestecLlarg +", Disponible=" + disponible;
    }

}
