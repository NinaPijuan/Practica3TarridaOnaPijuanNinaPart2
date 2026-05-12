package prog2.vista;

/**
 * Excepció de la aplicació BiblioUB.
 */
public class BiblioException extends Exception {

    /**
     * Construeix una BiblioException amb el missatge d'error indicat
     * @param message Descripció del motiu de l'excepció
     */
    public BiblioException(String message) {
        super(message);
    }
}