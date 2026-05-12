package prog2.model;

import java.io.Serializable;

/**
 * Representa un usuari de tipus estudiant de la biblioteca.
 */
public class Estudiant extends Usuari implements Serializable {
    /**
     * Constructor.
     *
     * @param email  Correu electrònic de l'estudiant (identificador únic)
     * @param nom    Nom de l'estudiant
     * @param adreca Adreça de l'estudiant
     */
    public Estudiant(String email, String nom, String adreca) {
        super(email, nom, adreca, 2, 1);
    }

    /**
     * Getter del número màxim de préstecs normals.
     * Per a un estudiant, el màxim és 2.
     */
    @Override
    public int getMaxPrestecsNormals() {
        return super.getMaxPrestecsNormals();
    }

    /**
     * Getter del número màxim de préstecs llargs.
     * Per a un estudiant, el màxim és 1.
     */
    @Override
    public int getMaxPrestecsLlargs() {
        return super.getMaxPrestecsLlargs();
    }

    /**
     * Retorna el tipus d'usuari com a String.
     *
     * @return La cadena "Estudiant"
     */
    @Override
    public String tipusUsuari(){ return "Estudiant"; }
}
