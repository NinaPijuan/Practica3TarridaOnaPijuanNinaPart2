package prog2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog2.vista.BiblioException;
import static org.junit.jupiter.api.Assertions.*;

public class LlistaTest {

    // -------------------------------------------------------
    // Tests LlistaExemplars
    // -------------------------------------------------------

    private LlistaExemplars llistaExemplars;

    @BeforeEach
    public void setUp() {
        llistaExemplars = new LlistaExemplars();
    }

    @Test
    public void testLlistaExemplarsInicialmentBuida() {
        assertTrue(llistaExemplars.isEmpty());
        assertEquals(0, llistaExemplars.getSize());
    }

    @Test
    public void testLlistaExemplarsAfegirUnElement() throws BiblioException {
        Exemplar e = new Exemplar("001", "Titol", "Autor", true);
        llistaExemplars.afegir(e);
        assertEquals(1, llistaExemplars.getSize());
        assertFalse(llistaExemplars.isEmpty());
    }

    @Test
    public void testLlistaExemplarsAfegirVarisElements() throws BiblioException {
        llistaExemplars.afegir(new Exemplar("001", "T1", "A1", true));
        llistaExemplars.afegir(new Exemplar("002", "T2", "A2", false));
        assertEquals(2, llistaExemplars.getSize());
    }

    @Test
    public void testLlistaExemplarsAfegirDuplicatLlançaExcepcio() throws BiblioException {
        Exemplar e1 = new Exemplar("001", "Titol", "Autor", true);
        Exemplar e2 = new Exemplar("001", "Altre Titol", "Altre Autor", false);
        llistaExemplars.afegir(e1);
        assertThrows(BiblioException.class, () -> llistaExemplars.afegir(e2));
    }

    @Test
    public void testLlistaExemplarsGetAt() throws BiblioException {
        Exemplar e = new Exemplar("001", "Titol", "Autor", true);
        llistaExemplars.afegir(e);
        assertEquals(e, llistaExemplars.getAt(0));
    }

    @Test
    public void testLlistaExemplarsEsborrar() throws BiblioException {
        Exemplar e = new Exemplar("001", "Titol", "Autor", true);
        llistaExemplars.afegir(e);
        llistaExemplars.esborrar(e);
        assertEquals(0, llistaExemplars.getSize());
    }

    @Test
    public void testLlistaExemplarsClear() throws BiblioException {
        llistaExemplars.afegir(new Exemplar("001", "T1", "A1", true));
        llistaExemplars.afegir(new Exemplar("002", "T2", "A2", false));
        llistaExemplars.clear();
        assertTrue(llistaExemplars.isEmpty());
    }

    @Test
    public void testLlistaExemplarsGetArrayListRetornaCopia() throws BiblioException {
        Exemplar e = new Exemplar("001", "Titol", "Autor", true);
        llistaExemplars.afegir(e);
        assertEquals(1, llistaExemplars.getArrayList().size());
    }

    @Test
    public void testLlistaExemplarsContainsIdExistent() throws BiblioException {
        llistaExemplars.afegir(new Exemplar("001", "Titol", "Autor", true));
        assertTrue(llistaExemplars.contains("001"));
    }

    @Test
    public void testLlistaExemplarsContainsIdInexistent() {
        assertFalse(llistaExemplars.contains("999"));
    }

    // -------------------------------------------------------
    // Tests LlistaUsuaris
    // -------------------------------------------------------

    @Test
    public void testLlistaUsuarisAfegirEstudiant() throws BiblioException {
        LlistaUsuaris lu = new LlistaUsuaris();
        lu.afegir(new Estudiant("a@ub.edu", "Anna", "Carrer 1"));
        assertEquals(1, lu.getSize());
    }

    @Test
    public void testLlistaUsuarisAfegirDuplicatEmailLlançaExcepcio() throws BiblioException {
        LlistaUsuaris lu = new LlistaUsuaris();
        lu.afegir(new Estudiant("a@ub.edu", "Anna", "Carrer 1"));
        assertThrows(BiblioException.class,
            () -> lu.afegir(new Professor("a@ub.edu", "Joan", "Carrer 2")));
    }

    @Test
    public void testLlistaUsuarisEmailsDiferentsNoLlançaExcepcio() throws BiblioException {
        LlistaUsuaris lu = new LlistaUsuaris();
        lu.afegir(new Estudiant("a@ub.edu", "Anna", "Carrer 1"));
        lu.afegir(new Professor("b@ub.edu", "Joan", "Carrer 2"));
        assertEquals(2, lu.getSize());
    }

    @Test
    public void testLlistaUsuarisContainsEmailExistent() throws BiblioException {
        LlistaUsuaris lu = new LlistaUsuaris();
        lu.afegir(new Estudiant("a@ub.edu", "Anna", "Carrer 1"));
        assertTrue(lu.contains("a@ub.edu"));
    }

    @Test
    public void testLlistaUsuarisContainsEmailInexistent() {
        LlistaUsuaris lu = new LlistaUsuaris();
        assertFalse(lu.contains("noexisteix@ub.edu"));
    }

    // -------------------------------------------------------
    // Tests LlistaPrestecs
    // -------------------------------------------------------

    @Test
    public void testLlistaPrestecsAfegirPrestec() throws BiblioException {
        LlistaPrestecs lp = new LlistaPrestecs();
        Exemplar e = new Exemplar("001", "Titol", "Autor", false);
        Estudiant u = new Estudiant("a@ub.edu", "Anna", "Carrer 1");
        PrestecNormal p = new PrestecNormal(e, u, new java.util.Date());
        lp.afegir(p);
        assertEquals(1, lp.getSize());
    }
}
