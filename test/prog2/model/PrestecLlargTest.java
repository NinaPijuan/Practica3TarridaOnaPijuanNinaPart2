package prog2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class PrestecLlargTest {

    private Exemplar exemplar;
    private Estudiant estudiant;

    @BeforeEach
    public void setUp() {
        exemplar = new Exemplar("001", "El Quijote", "Cervantes", true);
        estudiant = new Estudiant("est@ub.edu", "Anna", "Via Laietana 1");
    }

    @Test
    public void testConstructor() {
        Exemplar exemplar = new Exemplar("001", "El Quijote", "Cervantes", true);
        Estudiant estudiant = new Estudiant("est@ub.edu", "Anna", "Carrer Major 1");
        Date dataCreacio = new Date();
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, dataCreacio);
        assertAll(
            () -> assertEquals(exemplar, p.getExemplar()),
            () -> assertEquals(estudiant, p.getUsuari()),
            () -> assertEquals(dataCreacio, p.getDataCreacio()),
            () -> assertEquals(dataCreacio.getTime() + 140_000L, p.getDataLimitRetorn().getTime()),
            () -> assertFalse(p.getRetornat())
        );
    }

  @Test
    public void testTipusPrestec() {
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, new Date());
        assertEquals("Llarg", p.tipusPrestec());
    }

    @Test
    public void testDurada140Segons() {
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, new Date());
        assertEquals(140_000L, p.duradaPrestec());
    }

    @Test
    public void testDataLimitRetornCorrecta() {
        Date creacio = new Date();
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, creacio);
        assertEquals(creacio.getTime() + 140_000L, p.getDataLimitRetorn().getTime());
    }

    @Test
    public void testNoRetornatInicialment() {
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, new Date());
        assertFalse(p.getRetornat());
    }

    @Test
    public void testGetExemplar() {
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, new Date());
        assertEquals(exemplar, p.getExemplar());
    }

    @Test
    public void testGetUsuari() {
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, new Date());
        assertEquals(estudiant, p.getUsuari());
    }

    @Test
    public void testRetornaMarcaRetornat() {
        exemplar.setDisponible(false);
        estudiant.setNumPrestecsLlargs(1);
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, new Date());
        p.retorna();
        assertTrue(p.getRetornat());
    }

    @Test
    public void testRetornaAlliberaExemplar() {
        exemplar.setDisponible(false);
        estudiant.setNumPrestecsLlargs(1);
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, new Date());
        p.retorna();
        assertTrue(exemplar.isDisponible());
    }

    @Test
    public void testRetornaDecrementaComptadorUsuari() {
        exemplar.setDisponible(false);
        estudiant.setNumPrestecsLlargs(1);
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, new Date());
        p.retorna();
        assertEquals(0, estudiant.getNumPrestecsLlargs());
    }

    @Test
    public void testNoEndarreritSiAcabaDeCrear() {
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, new Date());
        assertFalse(p.prestecEndarrerit());
    }

    @Test
    public void testEndarreritSiDataLimitPassada() {
        Date dataAntica = new Date(System.currentTimeMillis() - 300_000L);
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, dataAntica);
        assertTrue(p.prestecEndarrerit());
    }

    @Test
    public void testNoEndarreritSiRetornat() {
        Date dataAntica = new Date(System.currentTimeMillis() - 300_000L);
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, dataAntica);
        p.retorna();
        assertFalse(p.prestecEndarrerit());
    }

    @Test
    public void testToStringConteCampsEsperats() {
        PrestecLlarg p = new PrestecLlarg(exemplar, estudiant, new Date());
        String s = p.toString();
        assertTrue(s.contains("Llarg"));
        assertTrue(s.contains("El Quijote"));
        assertTrue(s.contains("Anna"));
    }
}
