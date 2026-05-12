package prog2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstudiantTest {

    private Estudiant estudiant;

    @BeforeEach
    public void setUp() {
        estudiant = new Estudiant("est@ub.edu", "Anna", "Via Laietana 1");
    }

    @Test
    public void testConstructor() {
        Estudiant e = new Estudiant("est@ub.edu", "Anna", "Carrer Major 1");
        assertAll(
            () -> assertEquals("est@ub.edu", e.getEmail()),
            () -> assertEquals("Anna", e.getNom()),
            () -> assertEquals("Carrer Major 1", e.getAdreca()),
            () -> assertEquals(0, e.getNumPrestecsNormals()),
            () -> assertEquals(0, e.getNumPrestecsLlargs())
        );
    }

    @Test
    public void testTipusClient() {
        assertEquals("Estudiant", estudiant.tipusUsuari());
    }

    @Test
    public void testMaxPrestecsNormals() {
        assertEquals(2, estudiant.getMaxPrestecsNormals());
    }

    @Test
    public void testMaxPrestecsLlargs() {
        assertEquals(1, estudiant.getMaxPrestecsLlargs());
    }

    @Test
    public void testNumPrestecsInicialsZero() {
        assertEquals(0, estudiant.getNumPrestecsNormals());
        assertEquals(0, estudiant.getNumPrestecsLlargs());
    }

    @Test
    public void testSetIGetEmail() {
        estudiant.setEmail("nou@ub.edu");
        assertEquals("nou@ub.edu", estudiant.getEmail());
    }

    @Test
    public void testSetIGetNom() {
        estudiant.setNom("Marta");
        assertEquals("Marta", estudiant.getNom());
    }

    @Test
    public void testSetIGetAdreca() {
        estudiant.setAdreca("Nova Adreca 5");
        assertEquals("Nova Adreca 5", estudiant.getAdreca());
    }

    @Test
    public void testSetNumPrestecsNormals() {
        estudiant.setNumPrestecsNormals(1);
        assertEquals(1, estudiant.getNumPrestecsNormals());
    }

    @Test
    public void testSetNumPrestecsLlargs() {
        estudiant.setNumPrestecsLlargs(1);
        assertEquals(1, estudiant.getNumPrestecsLlargs());
    }

    @Test
    public void testToStringConteCampsEsperats() {
        String s = estudiant.toString();
        assertTrue(s.contains("Estudiant"));
        assertTrue(s.contains("est@ub.edu"));
        assertTrue(s.contains("Anna"));
        assertTrue(s.contains("Via Laietana 1"));
    }
}
