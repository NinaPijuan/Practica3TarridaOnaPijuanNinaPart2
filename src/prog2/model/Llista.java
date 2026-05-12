/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog2.model;

import java.io.Serializable;
import java.util.ArrayList;
import prog2.vista.BiblioException;

/**
 * Classe genèrica que representa una llista de qualsevol tipus T.
 *
 * @param <T> Tipus dels elements que contindrà la llista
 */
public class Llista<T> implements Serializable {
   protected ArrayList<T> llista;

    /**
     * Constructor per defecte.
     */
   public Llista() {
       llista = new ArrayList<>();
    }

    /**
     * Getter del nombre d'elements de la llista
     * @return size
     */
    public int getSize() { return llista.size(); }

    /**
     * Afegeix un element al final de la llista.
     *
     * @param t Element a afegir
     * @throws BiblioException Si la subclasse detecta alguna condició
     *                         que impedeixi l'addició
     */
    public void afegir(T t) throws BiblioException { llista.add(t); }

    /**
     * Retorna l'element situat a la posició indicada.
     *
     * @param position Índex de l'element que es vol obtenir
     * @return Element en la posició position
     */
    public T getAt(int position) { return llista.get(position); }

    /**
     * Elimina tots els elements de la llista
     */
    public void clear() { llista.clear(); }

    /**
     * Comprova si la llista està buida.
     *
     * @return true si la llista està buida
     *         false en cas contrari
     */
    public boolean isEmpty() { return (llista.size() == 0); }

    /**
     * Retorna una còpia del contingut de la llista com a ArrayList.
     *
     * @return Nou ArrayList amb tots els elements de la llista
     */
    public ArrayList<T> getArrayList() {
        return new ArrayList<>(llista);
    }
}
