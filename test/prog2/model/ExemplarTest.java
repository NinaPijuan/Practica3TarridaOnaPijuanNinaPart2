package prog2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExemplarTest {

    private Exemplar exemplar;

    @BeforeEach
    public void setUp() {
        exemplar = new Exemplar("001", "El Quijote", "Cervantes", true);
    }

    @Test
    public void testConstructor() {
        Exemplar e = new Exemplar("001", "El Quijote", "Cervantes", true);
        assertAll(
            () -> assertEquals("001", e.getId()),
            () -> assertEquals("El Quijote", e.getTitol()),
            () -> assertEquals("Cervantes", e.getAutor()),
            () -> assertTrue(e.getAdmetPrestecLlarg()),
            () -> assertTrue(e.isDisponible())
        );
    }

    @Test
    public void testSetIGetId() {
        exemplar.setId("999");
        assertEquals("999", exemplar.getId());
    }

    @Test
    public void testSetIGetTitol() {
        exemplar.setTitol("Nou Titol");
        assertEquals("Nou Titol", exemplar.getTitol());
    }

    @Test
    public void testSetIGetAutor() {
        exemplar.setAutor("Nou Autor");
        assertEquals("Nou Autor", exemplar.getAutor());
    }

    @Test
    public void testSetDisponibleFals() {
        exemplar.setDisponible(false);
        assertFalse(exemplar.isDisponible());
    }

    @Test
    public void testSetDisponibleTornaATrue() {
        exemplar.setDisponible(false);
        exemplar.setDisponible(true);
        assertTrue(exemplar.isDisponible());
    }

    @Test
    public void testAdmetPrestecLlargFals() {
        Exemplar e = new Exemplar("002", "Titol2", "Autor2", false);
        assertFalse(e.getAdmetPrestecLlarg());
    }

    @Test
    public void testSetAdmetPrestecLlarg() {
        exemplar.setAdmetPrestecLlarg(false);
        assertFalse(exemplar.getAdmetPrestecLlarg());
    }

    @Test
    public void testToStringContéCampsEsperats() {
        String s = exemplar.toString();
        assertTrue(s.contains("001"));
        assertTrue(s.contains("El Quijote"));
        assertTrue(s.contains("Cervantes"));
    }
}
