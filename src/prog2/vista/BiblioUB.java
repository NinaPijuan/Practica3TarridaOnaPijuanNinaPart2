/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog2.vista;

import java.util.List;
import java.util.Scanner;
import prog2.adaptador.Adaptador;

/**
 * Controlador principal de la interfície d'usuari de BibilioUB.
 */
public class BiblioUB {

    // ENUMERACIONS DE MENÚ
    
    // Declarem les constants del menu principal
    static private enum OpcionsMenuPrincipal {
        MENU_PRINCIPAL_EXEMPLARS,
        MENU_PRINCIPAL_USUARIS,
        MENU_PRINCIPAL_PRESTECS,
        MENU_PRINCIPAL_SAVE,
        MENU_PRINCIPAL_LOAD,
        MENU_PRINCIPAL_EXIT};
    
    // Declarem descripcions personalitzades per a les opcions del menú principal
    static private String[] descMenuPrincipal={"Gestió Exemplars",
                                               "Gestió Usuaris",
                                               "Gestió Prestecs",
                                               "Guardar Dades",
                                               "Recuperar Dades",
                                               "Sortir"};

    static private enum OpcionsMenuGestioExemplars {
        MENU_GESTIO_EXEMPLARS_ADD,
        MENU_GESTIO_EXEMPLARS_VIEW,
        MENU_GESTIO_EXEMPLARS_EXIT
    };
    
    // Declarem descripcions personalitzades per a les opcions del menú principal
    static private String[] descMenuGestioExemplars ={"Afegir Exemplar",
                                                      "Visualitzar Exemplars",
                                                      "Sortir"};

    static private enum OpcionsMenuGestioClients {
        MENU_GESTIO_USUARIS_ADD,
        MENU_GESTIO_USUARIS_VIEW,
        MENU_GESTIO_USUARIS_EXIT
    };
    
    // Declarem descripcions personalitzades per a les opcions del menú principal
    static private String[] descMenuGestioUsuaris ={"Afegir Usuari",
                                                    "Visualitzar Usuaris",
                                                    "Sortir"};

    static private enum OpcionsMenuGestioPrestecs {
        MENU_GESTIO_PRESTECS_ADD,
        MENU_GESTIO_PRESTECS_REMOVE,
        MENU_GESTIO_PRESTECS_VIEW,
        MENU_GESTIO_PRESTECS_VIEW_URG,
        MENU_GESTIO_PRESTECS_EXIT
    };
    
    // Declarem descripcions personalitzades per a les opcions del menú principal
    static private String[] descMenuGestioPrestecs ={"Afegir Prestec",
                                                     "Retornar Prestec",
                                                     "Visualitzar Prestecs",
                                                     "Visualitzar Prestecs no Retornats",
                                                     "Sortir"};

    // CONSTANTS DE VALIDACIÓ

    // Longitud màxima permesa per als camps de text
    private static final int MAX_LONGITUD_CAMP = 100;

    // Regex per validar IDs d'exemplar: només lletres, digits i guions
    private static final String REGEX_ID = "^[a-zA-Z0-9\\-]{1," + MAX_LONGITUD_CAMP + "}$";

    // Regex per validar emails
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9\\+\\_\\.\\-]+@[a-zA-Z0-9\\.\\-]+\\.[a-zA-Z]{2,}$";

    // ATRIBUTS

    /** Adaptador de l'aplicació */
    private Adaptador adaptador;

    // CONSTRUCTOR

    /** Constructor*/
    public BiblioUB() {
        adaptador = new Adaptador();
    }

    // MENÚ PRINCIPAL

    /**
     * Inicia el bucle principal de l'aplicació, mostrant el menú principal i
     * executant l'opció triada per l'usuari fins que es demana sortir.
     */
    public void gestioBiblioUB() {
        // Creem un objecte per llegir des del teclat
        Scanner sc = new Scanner(System.in);
        
        // Creem l'objecte per al menú. Li passem com a primer paràmetre el nom del menú
        Menu<OpcionsMenuPrincipal> menu = new Menu<>("Menu principal", OpcionsMenuPrincipal.values());

        // Assignem la descripció de les opcions
        menu.setDescripcions(descMenuPrincipal);
        
        OpcionsMenuPrincipal opcio;
        do {
            // Mostrem les opcions del menú i demanem una opció
            menu.mostrarMenu();
            opcio = menu.getOpcio(sc);

            // Fem les accions necessàries per a la opció triada
            switch(opcio) {
                case MENU_PRINCIPAL_EXEMPLARS:
                    // Mostra el menú per a la gestió d'exemplars
                    menuGestioExemplars(sc);
                    break;

                case MENU_PRINCIPAL_USUARIS:
                    // Mostra el menú per a la gestió d'usuaris
                    menuGestioUsuaris(sc);
                    break;

                case MENU_PRINCIPAL_PRESTECS:
                    // Mostra el menú per a la gestió de prestecs
                    menuGestioPrestecs(sc);
                    break;

                case MENU_PRINCIPAL_SAVE:
                    // Guardar dades
                    String dstFile = getFilePath(sc,false); // Obtenir el fitxer de sortida
                    if(dstFile != null) {
                        // Guardar les dades al fitxer triat
                        try {
                             this.adaptador.guardaDades(dstFile);
                             System.out.println("Dades guardades");
                        } catch (BiblioException ex) {
                            System.out.println("Error guardant les dades: " + ex.getMessage());
                        }
                    }                   
                    break;
                case MENU_PRINCIPAL_LOAD:
                    // Carregar dades                   
                    String srcFile = getFilePath(sc,false); // Obtenir el fitxer d'entrada
                    if(srcFile != null) {
                        // Carregar les dades del fitxer triat
                        try {
                             this.adaptador.carregaDades(srcFile);
                             System.out.println("Dades carregades");
                        } catch(BiblioException ex) {
                            System.out.println("Error carregant les dades:" + ex.getMessage());
                        }
                    }     
                    break;
                case MENU_PRINCIPAL_EXIT:
                    // Sortir      1
                    System.err.println("Sortint de l'aplicació...");
                    break;
            }
        } while(opcio != OpcionsMenuPrincipal.MENU_PRINCIPAL_EXIT);
    }

    // SUBMENÚS

    /**
     * Mostra i gestiona el submení de gestió d'exemplars fins que l'usuari
     * decideix sortir.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     */
    private void menuGestioExemplars(Scanner sc) {
        // Creem l'objecte per al menú. Li passem com a primer paràmetre el nom del menú
        Menu<OpcionsMenuGestioExemplars> menu=new Menu<OpcionsMenuGestioExemplars>("Menu Exemplars",OpcionsMenuGestioExemplars.values());

        // Assignem la descripció de les opcions
        menu.setDescripcions(descMenuGestioExemplars);

        // Obtenim una opció des del menú i fem les accions pertinents
        OpcionsMenuGestioExemplars opcio = null;
        do {
            // Mostrem les opcions del menú
            menu.mostrarMenu();

            // Demanem una opcio
            opcio=menu.getOpcio(sc);

            // Fem les accions necessàries
            switch(opcio) {
                case MENU_GESTIO_EXEMPLARS_ADD:
                    afegirExemplar(sc);
                    break;
                case MENU_GESTIO_EXEMPLARS_VIEW:
                    System.out.println("== EXEMPLARS ==");
                    visualitzar(adaptador.recuperarExemplars());
                    break;
                case MENU_GESTIO_EXEMPLARS_EXIT:
                    System.out.println("Sortint del menú d'exemplars...");
                    break;
            }

        } while(opcio!=OpcionsMenuGestioExemplars.MENU_GESTIO_EXEMPLARS_EXIT);
    }

    /**
     * Mostra i gestiona el submenú de gestió d'usuaris fins que l'usuari
     * decideixi sortir.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     */
    private void menuGestioUsuaris(Scanner sc) {
        // Creem l'objecte per al menú. Li passem com a primer paràmetre el nom del menú
        Menu<OpcionsMenuGestioClients> menu=new Menu<OpcionsMenuGestioClients>("Menu Usuaris",OpcionsMenuGestioClients.values());

        // Assignem la descripció de les opcions
        menu.setDescripcions(descMenuGestioUsuaris);

        // Obtenim una opció des del menú i fem les accions pertinents
        OpcionsMenuGestioClients opcio = null;
        do {
            // Mostrem les opcions del menú
            menu.mostrarMenu();

            // Demanem una opcio
            opcio=menu.getOpcio(sc);

            // Fem les accions necessàries
            switch(opcio) {
                case MENU_GESTIO_USUARIS_ADD:
                    afegirUsuari(sc);
                    break;
                case MENU_GESTIO_USUARIS_VIEW:
                    System.out.println("== USUARIS ==");
                    visualitzar(adaptador.recuperarUsuaris());
                    break;
                case MENU_GESTIO_USUARIS_EXIT:
                    System.out.println("Sortint del menú d'usuaris...");
                    break;
            }
        } while(opcio!=OpcionsMenuGestioClients.MENU_GESTIO_USUARIS_EXIT);
    }

    /**
     * Mostra i gestiona el submenú de gestió de préstecs fins que l'usuari
     * decideix sortir.
     *
     * @param sc Scanner per llegir l'entrada de l'usuari
     */
    private void menuGestioPrestecs(Scanner sc) {
        // Creem l'objecte per al menú. Li passem com a primer parÃ metre el nom del menú
        Menu<OpcionsMenuGestioPrestecs> menu=new Menu<OpcionsMenuGestioPrestecs>("Menu Prestecs",OpcionsMenuGestioPrestecs.values());

        // Assignem la descripció de les opcions
        menu.setDescripcions(descMenuGestioPrestecs);

        // Obtenim una opció des del menú i fem les accions pertinents
        OpcionsMenuGestioPrestecs opcio = null;
        do {
            // Mostrem les opcions del menú
            menu.mostrarMenu();

            // Demanem una opcio
            opcio=menu.getOpcio(sc);

            // Fem les accions necessàries
            switch(opcio) {
                case MENU_GESTIO_PRESTECS_ADD:
                    afegirPrestec(sc);
                    break;
                case MENU_GESTIO_PRESTECS_REMOVE:
                    cancelarPrestec(sc);
                    break;
                case MENU_GESTIO_PRESTECS_VIEW:
                    System.out.println("== TOTS ELS PRÉSTECS ==");
                    visualitzar(adaptador.recuperarPrestecs());
                    break;
                case MENU_GESTIO_PRESTECS_VIEW_URG:
                    System.out.println("== PRÉSTECS NO RETORNATS ==");
                    visualitzar(adaptador.recuperarPrestecsNoRetornats());
                    break;
                case MENU_GESTIO_PRESTECS_EXIT:
                    System.out.println("Sortint del menú de préstecs...");
                    break;
            }
        } while(opcio!=OpcionsMenuGestioPrestecs.MENU_GESTIO_PRESTECS_EXIT);
    }

    // OPERACIONS

    /**
     * Demana i valida totes les dades per afegir un exemplar nou.
     *
     * Validacions:
     *  - ID: no buit, només alfanumèric i guions, màx 100 car.
     *  - Títol: no buit, màx 100 car.
     *  - Autor: no buit, màx 100 car.
     *  - Admet préstec llarg: ha de ser exactament "si" o "no".
     *
     * @param sc Scanner actiu
     */
    private void afegirExemplar(Scanner sc){
        System.out.println("=== AFEGIR EXEMPLAR ===");

        // Id de l'exemplar
        String id;
        do {
            System.out.print("Introdueix ID de l'exemplar: ");

            // .trim() treu els espais de davant i darrere del String
            id = sc.nextLine().trim();

            // Comprovar que no està buit i compleix el regex
            if (id.isEmpty()) {
                System.err.println("Error: l'ID no pot estar buit.");
            } else if (!id.matches(REGEX_ID)) {
                System.err.println("Error: l'ID només pot contenir lletres, dígits i guions (-).");
                id = "";
            }
        } while (id.isEmpty());

        // Títol
        String titol;
        do {
            System.out.print("Introdueix títol: ");
            titol = sc.nextLine().trim();
            if (titol.isEmpty()) {
                System.err.println("Error: el títol no pot estar buit.");
            } else if (titol.length() > MAX_LONGITUD_CAMP) {
                System.err.println("Error: el títol no pot superar " + MAX_LONGITUD_CAMP + " caràcters.");
                titol = "";
            }
        } while (titol.isEmpty());

        // Autor
        String autor;
        do {
            System.out.print("Introdueix autor: ");
            autor = sc.nextLine().trim();
            if (autor.isEmpty()) {
                System.err.println("Error: l'autor no pot estar buit.");
            } else if (autor.length() > MAX_LONGITUD_CAMP) {
                System.err.println("Error: l'autor no pot superar " + MAX_LONGITUD_CAMP + " caràcters.");
                autor = "";
            }
        } while (autor.isEmpty());

        // Tipus
        boolean admetPrestecLlarg = llegirSiNo(sc, "Admet préstecs llargs?");

        // Afegir l'exemplar a través de l'adaptador
        try {
            adaptador.afegirExemplar(id, titol, autor, admetPrestecLlarg);
            System.out.println("Exemplar afegit correctament.");
        } catch (BiblioException ex) {
            System.err.println("Error afegint l'exemplar: " + ex.getMessage());
        }
    }

    /**
     * Demana i valida totes les dades per afegir un usuari nou.
     *
     * Validacions:
     *  - Email: format vàlid (regex), no buit. Es torna a demanar fins que sigui correcte.
     *  - Nom: no buit, màx 100 car.
     *  - Adreça: no buida, màx 100 car.
     *  - Tipus: ha de ser exactament "si" (estudiant) o "no" (professor).
     *
     * @param sc Scanner actiu
     */
    private void afegirUsuari(Scanner sc){
        System.out.println("=== AFEGIR USUARI ===");

        // email
        String email;
        do {
            System.out.print("Introdueix email: ");
            email = sc.nextLine().trim();
            if (email.isEmpty()) {
                System.err.println("Error: l'email no pot estar buit.");
            } else if (!email.matches(REGEX_EMAIL)) {
                System.err.println("Error: format d'email invàlid. Exemple: nom@ub.edu");
                email = "";
            }
        } while (email.isEmpty());

        // Nom
        String nom;
        do {
            System.out.print("Introdueix nom complet: ");
            nom = sc.nextLine().trim();
            if (nom.isEmpty()) {
                System.err.println("Error: el nom no pot estar buit.");
            } else if (nom.length() > MAX_LONGITUD_CAMP) {
                System.err.println("Error: el nom no pot superar " + MAX_LONGITUD_CAMP + " caràcters.");
                nom = "";
            }
        } while (nom.isEmpty());

        // Adreça
        String adreca;
        do {
            System.out.print("Introdueix adreça: ");
            adreca = sc.nextLine().trim();
            if (adreca.isEmpty()) {
                System.err.println("Error: l'adreça no pot estar buida.");
            } else if (adreca.length() > MAX_LONGITUD_CAMP) {
                System.err.println("Error: l'adreça no pot superar " + MAX_LONGITUD_CAMP + " caràcters.");
                adreca = "";
            }
        } while (adreca.isEmpty());

        // Tipus
        boolean esEstudiant = llegirSiNo(sc, "És estudiant? (si = Estudiant, no = Professor)");

        // Afegir l'usuari a través d'adaptador
        try {
            adaptador.afegirUsuari(email, nom, adreca, esEstudiant);
            System.out.println("Usuari afegit correctament ("
                    + (esEstudiant ? "Estudiant" : "Professor") + ").");
        } catch (BiblioException ex) {
            System.err.println("Error afegint l'usuari: " + ex.getMessage());
        }
    }



    /**
     * Demana i valida totes les dades per crear un préstec nou.
     *
     * Validacions:
     *  - Mostra primer la llista d'exemplars i després la d'usuaris perquè l'usuari
     *    pugui veure els índexs disponibles.
     *  - exemplarPos: enter >= 0 i < nombre d'exemplars existents.
     *  - usuariPos  : enter >= 0 i < nombre d'usuaris existents.
     *  - Tipus préstec: ha de ser exactament "si" (llarg) o "no" (normal).
     *
     * @param sc Scanner actiu
     */
    private void afegirPrestec(Scanner sc){
        System.out.println("=== AFEGIR PRÉSTEC ===");

        // Obtenir número d'exemplars i d'usuaris
        int numExemplars = adaptador.getNumExemplars();
        int numUsuaris   = adaptador.getNumUsuaris();

        // Comprovar que cap de les dues llistes està buida
        // Si ho està, mostra l'error per pantalla
        if (numExemplars == 0) {
            System.err.println("No hi ha cap exemplar registrat. Afegiu exemplars primer.");
            return;
        }
        if (numUsuaris == 0) {
            System.err.println("No hi ha cap usuari registrat. Afegiu usuaris primer.");
            return;
        }

        // Mostrar llista d'exemplars disponibles
        System.out.println("-- Exemplars disponibles --");
        visualitzar(adaptador.recuperarExemplars(), "Disponible=true");

        // Posició de l'exemplar
        int exemplarPos = llegirEnterEnRang(sc, "Introdueix l'índex de l'exemplar: ",
                0, numExemplars - 1);

        // Mostrar llista d'usuaris
        System.out.println("-- Usuaris registrats --");
        visualitzar(adaptador.recuperarUsuaris());

        // Posició de l'usuari
        int usuariPos = llegirEnterEnRang(sc, "Introdueix l'índex de l'usuari: ",
                0, numUsuaris - 1);

        // Tipus de préstec
        boolean esLlarg = llegirSiNo(sc, "És un préstec llarg? ");

        // Afegir préstec
        try {
            adaptador.afegirPrestec(exemplarPos, usuariPos, esLlarg);
            System.out.println("Préstec creat correctament (" + (esLlarg ? "Llarg" : "Normal") + ").");
        } catch (BiblioException ex) {
            System.err.println("Error creant el préstec: " + ex.getMessage());
        }
    }

    /**
     * Demana i valida les dades per retornar un préstec existent.
     *
     * Validacions:
     *  - Mostra primer la llista de préstecs no retornats amb els seus índexs.
     *  - Si no n'hi ha cap, informa i torna al menú.
     *  - posicio: enter >= 0 i < nombre total de préstecs.
     *
     * @param sc Scanner actiu
     */
    private void cancelarPrestec(Scanner sc){
        System.out.println("=== RETORNAR PRÉSTEC ===");

        int numPrestecs = adaptador.getNumPrestecs();

        if (numPrestecs == 0) {
            System.out.println("No hi ha cap préstec registrat.");
            return;
        }

        // Mostrem els préstecs que encara no han estat retornats amb els índexs
        System.out.println("-- Préstecs registrats --");
        visualitzar(adaptador.recuperarPrestecs(), "Retornat=false");

        // Posició del préstec
        int posicio = llegirEnterEnRang(sc, "Introdueix l'índex del préstec a retornar: ", 0, numPrestecs - 1);

        // Retornar préstec
        try {
            adaptador.retornarPrestec(posicio);
            System.out.println("Préstec retornat correctament");
        } catch (BiblioException ex) {
            System.err.println("Error retornant el préstec: " + ex.getMessage());
        }
    }

    // ALTRES

     /**
     * Mostra una llista d'objectes
     * @param title Títol a posar com a capçalera
     * @param lines Llista d'objectes per mostrar
     */
    private void showList(String title, List<String> lines) {
        System.out.println("============================================");
        System.out.println(title);
        System.out.println("============================================");
        int i = 0;
        for(String l : lines) {
            System.out.println("\t[" + (i++) + "] " + l);
        }
        System.out.println("============================================");
    }


    /**
     * Demana el camí d'un fitxer
     * @param sc Objecte per a la lectura de dades de teclat
     * @param mustExist Exigeix que el fitxer existeixi (True) o no (False)
     * @return Ruta al fitxer entrada per l'usuari o null si s'ha cancelat
     */
    private String getFilePath(Scanner sc, boolean mustExist) {
        String filePath = null;

        // Mostrar el missatge demanant la entrada
        System.out.println("Entra ruta completa fitxer (o ENTER per ometre):");

        // Llegim la ruta del fitxer
        filePath = sc.nextLine();

        // Si la ruta està buida retornem un null
        if(filePath.isEmpty()) {
                return null;
        }

        return filePath;
    }

    // MÈTODES AUXILIARS

    /**
     * Llegeix un enter del teclat dins del rang [min, max].
     * Gestiona entrades no numèriques i valors fora de rang mostrant
     * un missatge d'error i tornant a demanar.
     *
     * @param sc       Scanner actiu
     * @param missatge Missatge que es mostra abans de llegir
     * @param min      Valor mínim acceptat (inclòs)
     * @param max      Valor màxim acceptat (inclòs)
     * @return Enter vàlid dins del rang
     */
    private int llegirEnterEnRang(Scanner sc, String missatge, int min, int max) {
        int valor;

        // Bucle que només acaba quan s'ha introduit un valor dintre del rang
        while (true) {
            System.out.print(missatge);

            // Llegim el String que ha introduit l'usuari
            String linia = sc.nextLine().trim();

            // Comprovació que no sigui buida
            if (linia.isEmpty()) {
                System.err.println("Error: cal introduir un número.");

            } else {

                // Integer.parseInt(String) pot produir error si el String no és un valor numèric
                try {
                    valor = Integer.parseInt(linia);
                    if (valor < min || valor > max) {
                        System.err.println("Error: el valor ha d'estar entre " + min + " i " + max + ".");
                    } else {
                        return valor;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error: '" + linia + "' no és un número enter vàlid.");
                }
            }
        }
    }

    /**
     * Llegeix una resposta de tipus si/no del teclat.
     * Accepta únicament "si" o "no" (en minúscules). Torna a demanar si
     * l'entrada és diferent.
     *
     * @param sc       Scanner actiu
     * @param missatge Missatge que es mostra abans de llegir
     * @return true si l'usuari ha escrit "si", false si ha escrit "no"
     */
    private boolean llegirSiNo(Scanner sc, String missatge) {
        String resposta;
        do {
            System.out.print(missatge + " (si/no): ");
            resposta = sc.nextLine().trim().toLowerCase();
            if (!resposta.equals("si") && !resposta.equals("no")) {
                System.err.println("Error: introdueix 'si' o 'no'.");
            }
        } while (!resposta.equals("si") && !resposta.equals("no"));
        return resposta.equals("si");
    }

    /**
     * Mostra una llista numerada d'elements.
     * Si la llista és buida, ho informa a l'usuari.
     *
     * @param llista Llista de cadenes a mostrar
     */
    private void visualitzar(List<String> llista) {
        // Llista buida
        if (llista.isEmpty()) {
            System.out.println("  (cap registrat)");
            return;
        }

        // Mostrar elements de la llista amb índex
        for (int i = 0; i < llista.size(); i++) {
                System.out.println(" [" + i + "] " + llista.get(i));
        }
    }

    /**
     * Mostra únicament els elements de la llista que contenen la subcadena requisit.
     * S'utilitza, per exemple, per filtrar exemplars disponibles.
     *
     * @param llista    Llista de cadenes a filtrar i mostrar
     * @param requisit  Subcadena que ha de contenir cada element per ser mostrat
     */
    private void visualitzar(List<String> llista, String requisit) {
        // Llista buida
        if (llista.isEmpty()) {
            System.out.println("  (cap registrat)");
            return;
        }

        // Mostrar elements de la llista que compleixen el requisit amb l'índex
        for (int i = 0; i < llista.size(); i++) {
            if (llista.get(i).contains(requisit))
                System.out.println(" [" + i + "] " + llista.get(i));
        }
    }
}