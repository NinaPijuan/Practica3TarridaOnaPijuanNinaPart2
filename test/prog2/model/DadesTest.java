package prog2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog2.vista.BiblioException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class DadesTest {

    private Dades dades;

    @BeforeEach
    public void setUp() {
        dades = new Dades();
    }

    // -------------------------------------------------------
    // Exemplars
    // -------------------------------------------------------

    @Test
    public void testAfegirExemplarOk() throws BiblioException {
        dades.afegirExemplar("001", "Titol", "Autor", true);
        assertEquals(1, dades.recuperaExemplars().size());
    }

    @Test
    public void testAfegirExemplarIdDuplicatLlançaExcepcio() throws BiblioException {
        dades.afegirExemplar("001", "Titol", "Autor", true);
        assertThrows(BiblioException.class,
            () -> dades.afegirExemplar("001", "Altre Titol", "Altre Autor", false));
    }

    @Test
    public void testRecuperaExemplarsLlistaCorrecta() throws BiblioException {
        dades.afegirExemplar("001", "T1", "A1", true);
        dades.afegirExemplar("002", "T2", "A2", false);
        assertEquals(2, dades.recuperaExemplars().size());
    }

    // -------------------------------------------------------
    // Usuaris
    // -------------------------------------------------------

    @Test
    public void testAfegirUsuariEstudiant() throws BiblioException {
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        ArrayList<Usuari> usuaris = dades.recuperaUsuaris();
        assertEquals(1, usuaris.size());
        assertInstanceOf(Estudiant.class, usuaris.get(0));
    }

    @Test
    public void testAfegirUsuariProfessor() throws BiblioException {
        dades.afegirUsuari("prof@ub.edu", "Joan", "Carrer 2", false);
        ArrayList<Usuari> usuaris = dades.recuperaUsuaris();
        assertEquals(1, usuaris.size());
        assertInstanceOf(Professor.class, usuaris.get(0));
    }

    @Test
    public void testAfegirUsuariEmailDuplicatLlançaExcepcio() throws BiblioException {
        dades.afegirUsuari("a@ub.edu", "Anna", "Carrer 1", true);
        assertThrows(BiblioException.class,
            () -> dades.afegirUsuari("a@ub.edu", "Joan", "Carrer 2", false));
    }

    // -------------------------------------------------------
    // Préstecs: afegir
    // -------------------------------------------------------

    @Test
    public void testAfegirPrestecNormalOk() throws BiblioException {
        dades.afegirExemplar("001", "Titol", "Autor", false);
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        dades.afegirPrestec(0, 0, false);
        assertEquals(1, dades.recuperaPrestecs().size());
    }

    @Test
    public void testAfegirPrestecNormalMarcaExemplarNoDisponible() throws BiblioException {
        dades.afegirExemplar("001", "Titol", "Autor", false);
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        dades.afegirPrestec(0, 0, false);
        assertFalse(dades.recuperaExemplars().get(0).isDisponible());
    }

    @Test
    public void testAfegirPrestecNormalIncrementaComptadorUsuari() throws BiblioException {
        dades.afegirExemplar("001", "Titol", "Autor", false);
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        dades.afegirPrestec(0, 0, false);
        assertEquals(1, dades.recuperaUsuaris().get(0).getNumPrestecsNormals());
    }

    @Test
    public void testAfegirPrestecLlargOk() throws BiblioException {
        dades.afegirExemplar("001", "Titol", "Autor", true); // admet llarg
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        dades.afegirPrestec(0, 0, true);
        assertEquals(1, dades.recuperaPrestecs().size());
    }

    // -------------------------------------------------------
    // Préstecs: excepcions
    // -------------------------------------------------------

    @Test
    public void testAfegirPrestecExemplarNoDisponibleLlançaExcepcio() throws BiblioException {
        dades.afegirExemplar("001", "Titol", "Autor", false);
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        dades.afegirPrestec(0, 0, false); // primer préstec OK
        // Segon usuari intenta agafar el mateix exemplar ja no disponible
        dades.afegirUsuari("b@ub.edu", "Berta", "Carrer 2", true);
        assertThrows(BiblioException.class,
            () -> dades.afegirPrestec(0, 1, false));
    }

    @Test
    public void testAfegirPrestecLlargExemplarNoAdmetLlançaExcepcio() throws BiblioException {
        dades.afegirExemplar("001", "Titol", "Autor", false); // NO admet llarg
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        assertThrows(BiblioException.class,
            () -> dades.afegirPrestec(0, 0, true));
    }

    @Test
    public void testAfegirPrestecSuperaLimitNormalsEstudiantLlançaExcepcio() throws BiblioException {
        // Estudiant: màxim 2 préstecs normals
        dades.afegirExemplar("001", "T1", "A1", false);
        dades.afegirExemplar("002", "T2", "A2", false);
        dades.afegirExemplar("003", "T3", "A3", false);
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        dades.afegirPrestec(0, 0, false);
        dades.afegirPrestec(1, 0, false);
        assertThrows(BiblioException.class,
            () -> dades.afegirPrestec(2, 0, false));
    }

    @Test
    public void testAfegirPrestecSuperaLimitLlargsEstudiantLlançaExcepcio() throws BiblioException {
        // Estudiant: màxim 1 préstec llarg
        dades.afegirExemplar("001", "T1", "A1", true);
        dades.afegirExemplar("002", "T2", "A2", true);
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        dades.afegirPrestec(0, 0, true);
        assertThrows(BiblioException.class,
            () -> dades.afegirPrestec(1, 0, true));
    }

    @Test
    public void testAfegirPrestecUsuariAmbPrestecsEndarreritsLlançaExcepcio() throws BiblioException {
        // Creem un préstec amb data de creació molt antiga perquè estigui endarrerit
        dades.afegirExemplar("001", "T1", "A1", false);
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        dades.afegirPrestec(0, 0, false);

        // Manipulem la data límit del préstec perquè estigui endarrerit
        Prestec p = dades.recuperaPrestecs().get(0);
        p.setDataLimitRetorn(new java.util.Date(System.currentTimeMillis() - 10_000L));

        // Ara intentem fer un nou préstec amb un segon exemplar
        dades.afegirExemplar("002", "T2", "A2", false);
        assertThrows(BiblioException.class,
            () -> dades.afegirPrestec(1, 0, false));
    }

    // -------------------------------------------------------
    // Preste: retorn
    // -------------------------------------------------------

    @Test
    public void testRetornarPrestecOk() throws BiblioException {
        dades.afegirExemplar("001", "Titol", "Autor", false);
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        dades.afegirPrestec(0, 0, false);
        dades.retornarPrestec(0);
        assertTrue(dades.recuperaPrestecs().get(0).getRetornat());
    }

    @Test
    public void testRetornarPrestecAlliberaExemplar() throws BiblioException {
        dades.afegirExemplar("001", "Titol", "Autor", false);
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        dades.afegirPrestec(0, 0, false);
        dades.retornarPrestec(0);
        assertTrue(dades.recuperaExemplars().get(0).isDisponible());
    }

    @Test
    public void testRetornarPrestecJaRetornatLlançaExcepcio() throws BiblioException {
        dades.afegirExemplar("001", "Titol", "Autor", false);
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        dades.afegirPrestec(0, 0, false);
        dades.retornarPrestec(0);
        assertThrows(BiblioException.class, () -> dades.retornarPrestec(0));
    }

    // -------------------------------------------------------
    // Prestec: consultes
    // -------------------------------------------------------

    @Test
    public void testRecuperaPrestecsNoRetornats() throws BiblioException {
        dades.afegirExemplar("001", "T1", "A1", false);
        dades.afegirExemplar("002", "T2", "A2", false);
        dades.afegirUsuari("est@ub.edu", "Anna", "Carrer 1", true);
        dades.afegirPrestec(0, 0, false);
        dades.afegirPrestec(1, 0, false);
        dades.retornarPrestec(0); // retornem el primer
        assertEquals(1, dades.recuperaPrestecsNoRetornats().size());
    }
}
