package prog2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProfessorTest {

    private Professor professor;

    @BeforeEach
    public void setUp() {
        professor = new Professor("prof@ub.edu", "Joan", "Avinguda Diagonal 1");
    }

    @Test
    public void testConstructor() {
        Professor p = new Professor("prof@ub.edu", "Joan", "Avinguda Diagonal 1");
        assertAll(
            () -> assertEquals("prof@ub.edu", p.getEmail()),
            () -> assertEquals("Joan", p.getNom()),
            () -> assertEquals("Avinguda Diagonal 1", p.getAdreca()),
            () -> assertEquals(0, p.getNumPrestecsNormals()),
            () -> assertEquals(0, p.getNumPrestecsLlargs())
        );
    }

    @Test
    public void testTipusClient() {
        assertEquals("Professor", professor.tipusUsuari());
    }

    @Test
    public void testMaxPrestecsNormals() {
        assertEquals(2, professor.getMaxPrestecsNormals());
    }

    @Test
    public void testMaxPrestecsLlargs() {
        assertEquals(2, professor.getMaxPrestecsLlargs());
    }

    @Test
    public void testNumPrestecsInicialsZero() {
        assertEquals(0, professor.getNumPrestecsNormals());
        assertEquals(0, professor.getNumPrestecsLlargs());
    }

    @Test
    public void testSetIGetEmail() {
        professor.setEmail("nou@ub.edu");
        assertEquals("nou@ub.edu", professor.getEmail());
    }

    @Test
    public void testSetIGetNom() {
        professor.setNom("Pere");
        assertEquals("Pere", professor.getNom());
    }

    @Test
    public void testSetIGetAdreca() {
        professor.setAdreca("Carrer Nou 3");
        assertEquals("Carrer Nou 3", professor.getAdreca());
    }

    @Test
    public void testSetNumPrestecsNormals() {
        professor.setNumPrestecsNormals(2);
        assertEquals(2, professor.getNumPrestecsNormals());
    }

    @Test
    public void testSetNumPrestecsLlargs() {
        professor.setNumPrestecsLlargs(2);
        assertEquals(2, professor.getNumPrestecsLlargs());
    }

    @Test
    public void testToStringConteCampsEsperats() {
        String s = professor.toString();
        assertTrue(s.contains("Professor"));
        assertTrue(s.contains("prof@ub.edu"));
        assertTrue(s.contains("Joan"));
    }
}
