package prog2.model;

import java.io.Serializable;

/**
 * Representa un usuari de tipus professor a la biblioteca
 */
public class Professor extends Usuari implements Serializable {

    /**
     * Constructor
     *
     * @param email  Correu electrònic del professor (identificador únic)
     * @param nom    Nom del professor
     * @param adreca Adreça del professor
     */
    public Professor(String email, String nom, String adreca) {
        super(email, nom, adreca, 2, 2);
    }

    /**
     * Getter del número màxim de préstecs normals.
     * Per a un professor, el màxim és 2.
     */
    @Override
    public int getMaxPrestecsNormals() {
        return super.getMaxPrestecsNormals();
    }

    /**
     * Getter del número màxim de préstecs llargs.
     * Per a un professor, el màxim són 2.
     */
    @Override
    public int getMaxPrestecsLlargs() {
        return super.getMaxPrestecsLlargs();
    }

    /**
     * Retorna el tipus d'usuari com a String
     *
     * @return La cadena "Professor"
     */
    @Override
    public String tipusUsuari(){ return "Professor"; }

}
