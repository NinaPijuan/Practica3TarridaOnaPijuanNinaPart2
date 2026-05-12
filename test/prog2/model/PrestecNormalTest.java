package prog2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class PrestecNormalTest {

    private Exemplar exemplar;
    private Estudiant estudiant;

    @BeforeEach
    public void setUp() {
        exemplar = new Exemplar("001", "El Quijote", "Cervantes", false);
        estudiant = new Estudiant("est@ub.edu", "Anna", "Via Laietana 1");
    }

    @Test
    public void testConstructor() {
        Exemplar exemplar = new Exemplar("001", "El Quijote", "Cervantes", false);
        Estudiant estudiant = new Estudiant("est@ub.edu", "Anna", "Carrer Major 1");
        Date dataCreacio = new Date();
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, dataCreacio);
        assertAll(
            () -> assertEquals(exemplar, p.getExemplar()),
            () -> assertEquals(estudiant, p.getUsuari()),
            () -> assertEquals(dataCreacio, p.getDataCreacio()),
            () -> assertEquals(dataCreacio.getTime() + 70_000L, p.getDataLimitRetorn().getTime()),
            () -> assertFalse(p.getRetornat())
        );
    }

    @Test
    public void testTipusPrestec() {
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, new Date());
        assertEquals("Normal", p.tipusPrestec());
    }

    @Test
    public void testDurada70Segons() {
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, new Date());
        assertEquals(70_000L, p.duradaPrestec());
    }

    @Test
    public void testDataLimitRetornCorrecta() {
        Date creacio = new Date();
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, creacio);
        assertEquals(creacio.getTime() + 70_000L, p.getDataLimitRetorn().getTime());
    }

    @Test
    public void testNoRetornatInicialment() {
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, new Date());
        assertFalse(p.getRetornat());
    }

    @Test
    public void testGetExemplar() {
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, new Date());
        assertEquals(exemplar, p.getExemplar());
    }

    @Test
    public void testGetUsuari() {
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, new Date());
        assertEquals(estudiant, p.getUsuari());
    }

    @Test
    public void testRetornaMarcaRetornat() {
        exemplar.setDisponible(false);
        estudiant.setNumPrestecsNormals(1);
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, new Date());
        p.retorna();
        assertTrue(p.getRetornat());
    }

    @Test
    public void testRetornaAlliberaExemplar() {
        exemplar.setDisponible(false);
        estudiant.setNumPrestecsNormals(1);
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, new Date());
        p.retorna();
        assertTrue(exemplar.isDisponible());
    }

    @Test
    public void testRetornaDecrementaComptadorUsuari() {
        exemplar.setDisponible(false);
        estudiant.setNumPrestecsNormals(1);
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, new Date());
        p.retorna();
        assertEquals(0, estudiant.getNumPrestecsNormals());
    }

    @Test
    public void testNoEndarreritSiAcabaDeCrear() {
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, new Date());
        assertFalse(p.prestecEndarrerit());
    }

    @Test
    public void testEndarreritSiDataLimitPassada() {
        Date dataAntica = new Date(System.currentTimeMillis() - 200_000L);
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, dataAntica);
        assertTrue(p.prestecEndarrerit());
    }

    @Test
    public void testToStringConteCampsEsperats() {
        PrestecNormal p = new PrestecNormal(exemplar, estudiant, new Date());
        String s = p.toString();
        assertTrue(s.contains("Normal"));
        assertTrue(s.contains("El Quijote"));
        assertTrue(s.contains("Anna"));
    }
}
