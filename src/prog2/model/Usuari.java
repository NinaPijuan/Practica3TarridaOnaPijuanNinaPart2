package prog2.model;

import java.io.Serializable;

/**
 * Classe abstracta que representa un usuari de la biblioteca
 */
public abstract class Usuari implements InUsuari, Serializable {
    private String email;
    private String nom;
    private String adreca;
    private int numPrestecsNormals;
    private int numPrestecstLlargs;
    private int maxPrestecsNormals;
    private int maxPrestecsLlargs;

    /**
     * Constructor
     *
     * @param email              Correu electrònic de l'usuari
     * @param nom                Nom de l'usuari
     * @param adreca             Adreça de l'usuari
     * @param maxPrestecsNormals Nombre màxim de préstecs normals permesos
     * @param maxPrestecsLlargs  Nombre màxim de préstecs llargs permesos
     */
    public Usuari(String email, String nom, String adreca, int maxPrestecsNormals, int maxPrestecsLlargs) {
        this.email = email;
        this.nom = nom;
        this.adreca = adreca;
        this.maxPrestecsNormals = maxPrestecsNormals;
        this.maxPrestecsLlargs = maxPrestecsLlargs;
        this.numPrestecstLlargs = 0; // 0 per def
        this.numPrestecsNormals = 0; // 0 per def
    }

    /**
     * Setter del email
     * @param email
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter del email
     * @return email
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Setter del nom
     * @param nom
     */
    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter del nom
     * @return nom
     */
    @Override
    public String getNom() {
        return nom;
    }

    /**
     * Setter de l'adreça
     * @param adreca
     */
    @Override
    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    /**
     * Getter de l'adreça
     * @return adreça
     */
    @Override
    public String getAdreca() {
        return adreca;
    }

    /**
     * Retorna una cadena de text que identifica el tipus d'usuari.
     * Mètode abstracte que definim en les subclasses.
     *
     * @return Cadena que descriu el tipus d'usuari ("Estudiant" o "Professor")
     */
    @Override
    public abstract String tipusUsuari();

    /**
     * Setter del número de préstecs normals
     * @param numPrestecsNormals
     */
    @Override
    public void setNumPrestecsNormals(int numPrestecsNormals) {
        this.numPrestecsNormals = numPrestecsNormals;
    }

    /**
     * Getter del número de préstecs normals
     * @return numPrestecsNormals
     */
    @Override
    public int getNumPrestecsNormals() {
        return numPrestecsNormals;
    }

    /**
     * Setter del número de préstecs llargs
     * @param numPrestecstLlargs
     */
    @Override
    public void setNumPrestecsLlargs(int numPrestecstLlargs) {
        this.numPrestecstLlargs = numPrestecstLlargs;
    }

    /**
     * Getter del número de préstecs llargs
     * @return numPrestecsLlargs
     */
    @Override
    public int getNumPrestecsLlargs() { return numPrestecstLlargs; }

    /**
     * Getter del número màxim de préstecs normals
     * @return maxPrestecsNormals
     */
    @Override
    public int getMaxPrestecsNormals() { return maxPrestecsNormals; }

    /**
     * Getter del número màxim de préstecs llargs
     * @return maxPrestecsLlargs
     */
    @Override
    public int getMaxPrestecsLlargs() {
        return maxPrestecsLlargs;
    }

    /**
     * Retorna una representació textual de l'usuari amb tots els seus camps.
     *
     * @return String amb el tipus d'usuari, email, nom, adreça i comptadors de préstecs actius
     */
    @Override
    public String toString(){
        return "Tipus=" + tipusUsuari() + ", Email=" + email + ", Nom=" + nom + ", Adreça="
                + adreca + ", Num. préstecs normals=" + numPrestecsNormals + ", Num. préstecs llargs="
                + numPrestecstLlargs;
    }

}
