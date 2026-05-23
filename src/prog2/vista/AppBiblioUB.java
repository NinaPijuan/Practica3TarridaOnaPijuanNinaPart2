package prog2.vista;

import prog2.adaptador.Adaptador;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;

/**
 * Finestra principal de l'aplicació BiblioUB.
 *
 * Aquesta classe és el punt d'entrada de l'aplicació. Des d'aquí l'usuari pot:
 * - Obrir la gestió d'usuaris, exemplars i préstecs
 * - Guardar l'estat actual de la biblioteca en un fitxer.
 * - Carregar un estat prèviament guardat des d'un fitxer.
 */
public class AppBiblioUB extends JFrame {
    private JPanel panel;
    private JButton btnGestioUsuaris;
    private JButton btnGestioExemplars;
    private JButton btnGestioPrestecs;
    private JButton btnGuardar;
    private JButton btnCarregar;
    private JPanel panelGestio;
    private JPanel panelPersistencia;
    private JLabel etTitol;

    /**
     * Adaptador que fa de pont entre la vista i el model.
     * Es passa per referència a totes les subfinestres perquè comparteixin
     * el mateix estat de la biblioteca.
     */
    private Adaptador adaptador;

    // ── Paleta de colors ────────────────────────────────────────────────────
    /* Tots els colors es declaren static final perquè les subfinestres els
       puguin reutilitzar sense crear objectes d'AppBiblioUB.
     */

    /** Color de fons principal de la finestra. */
    static final Color C_FONS = new Color(0x1C, 0x2B, 0x1E);

    /** Color de fons dels panells secundaris i diàlegs. */
    static final Color C_PANEL = new Color(0x24, 0x38, 0x27);

    /** Color daurat, utilitzat pels contorns dels diàlegs, ressaltat de botons "Acceptar"
     * i color del cursor dels camps de text. */
    static final Color C_OR = new Color(0xC8, 0xA8, 0x50);

    /** Color del text principal. */
    static final Color C_TEXT = new Color(0xF0, 0xE8, 0xD0);

    /** Color del text secundari, utilitzat per a etiquetes i botons secundaris. */
    static final Color C_TEXT2 = new Color(0xB8, 0xAA, 0x88);

    /** Color de les vores, utilitzat en els camps de text, scroll panels i botons. */
    static final Color C_VORA = new Color(0xA0, 0x85, 0x50);

    /** Color de fons dels botons primaris (gestió). */
    static final Color C_BTN = new Color(0x3A, 0x4E, 0x28);

    /** Color de fons dels botons secundaris (persistència). */
    static final Color C_BTN2 = new Color(0x2A, 0x38, 0x1C);

    /** Color de fons dels camps d'entrada de text i llistes. */
    static final Color C_INPUT = new Color(0x18, 0x26, 0x1A);

    /** Color de selecció a les llistes. */
    static final Color C_SEL = new Color(0x4A, 0x6B, 0x30);

    // ── Fonts ───────────────────────────────────────────────────────────────

    /** Font botons primaris: negreta mida 16.
     * Utilitzada als botons de gestió i a les capçaleres dels Dialogs. */
    static final Font F_TITOL = new Font("", Font.BOLD, 16);

    /** Font del cos general: normal mida 14.
     * Utilitzada als camps de text, botons secundaris i altres. */
    static final Font F_COS = new Font("", Font.PLAIN, 14);

    /** Font per a etiquetes: cursiva mida 13. */
    static final Font F_ITALIC = new Font("", Font.ITALIC, 14);

    /** Font per als elements de les llistes: normal mida 14 */
    static final Font F_LLISTA = new Font("", Font.PLAIN, 14);

    /**
     * main de l'aplicació (per on s'entra)
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppBiblioUB app = new AppBiblioUB();
            app.setVisible(true);
        });
    }

    /**
     * Construeix i configura la finestra principal de BiblioUB.
     */
    public AppBiblioUB() {
        // Creem l'adaptador: punt d'accés al model
        adaptador = new Adaptador();

        setTitle("BiblioUB — Sistema de Gestió de Biblioteca");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(1000, 700);

        // Posar la finestra al centre de la pantalla
        setLocationRelativeTo(null);

        // Aplicar estètica
        aplicarTema();

        btnGestioUsuaris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Creem diàleg, la fem modal (bloqueja la finestra pare) i la fem visible.
                // L'ordre és important
                FrmGestioUsuaris frmGestioUsuaris = new FrmGestioUsuaris(AppBiblioUB.this, adaptador);
                frmGestioUsuaris.setModal(true);
                frmGestioUsuaris.setVisible(true);
            }
        });

        btnGestioExemplars.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmGestioExemplars frmGestioExemplars = new FrmGestioExemplars(AppBiblioUB.this, adaptador);
                frmGestioExemplars.setModal(true);
                frmGestioExemplars.setVisible(true);
            }
        });

        btnGestioPrestecs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmGestioPrestecs frmGestioPrestecs = new FrmGestioPrestecs(AppBiblioUB.this, adaptador);
                frmGestioPrestecs.setModal(true);
                frmGestioPrestecs.setVisible(true);
            }
        });


        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Mostrem un selector de fitxers en mode "guardar"
                JFileChooser seleccio = new JFileChooser();
                int resultat = seleccio.showSaveDialog(AppBiblioUB.this);

                if (resultat == JFileChooser.APPROVE_OPTION) {
                    File fitxer = seleccio.getSelectedFile();
                    try {
                        adaptador.guardaDades(fitxer.toString());
                        JOptionPane.showMessageDialog(AppBiblioUB.this, "Dades guardades",
                                "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (BiblioException ex) {
                        JOptionPane.showMessageDialog(AppBiblioUB.this, ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnCarregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Mostrem un selector de fitxers en mode "obrir"
                JFileChooser seleccio = new JFileChooser();
                int resultat = seleccio.showOpenDialog(AppBiblioUB.this);

                if (resultat == JFileChooser.APPROVE_OPTION) {
                    File fitxer = seleccio.getSelectedFile();
                    try {
                        adaptador.carregaDades(fitxer.toString());
                        JOptionPane.showMessageDialog(AppBiblioUB.this, "Dades carregades",
                                "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (BiblioException ex) {
                        JOptionPane.showMessageDialog(AppBiblioUB.this, ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }



    // VISUALS

    /* Tots els mètodes d'estètica (excepte aplicarTema) són "static" per
       permetre que les subfinestres els cridin sense necessitar una referència
       a AppBiblioUB.
     */

    /** Aplica els colors i fonts del tema als components generats pel .form */
    private void aplicarTema() {
        // Fons principal amb marges per centrar el contingut
        panel.setBackground(C_FONS);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // Panells secundaris: mateix color del fons (si no es posa surten blancs)
        // S'ha de posar el setBackground(C_FONS) a TOTS els JPanel del .form
        panelGestio.setBackground(C_FONS);
        panelPersistencia.setBackground(C_FONS);

        // Botons de gestió
        estilitzarBotoGestio(btnGestioExemplars, "Gestió d'Exemplars");
        estilitzarBotoGestio(btnGestioUsuaris,   "Gestió d'Usuaris");
        estilitzarBotoGestio(btnGestioPrestecs,  "Gestió de Préstecs");

        // Botons de fitxer (secundaris)
        estilitzarBotoSecundari(btnGuardar,  "Guardar dades");
        estilitzarBotoSecundari(btnCarregar, "Carregar dades");

        // Títol
        estilitzarTitol(etTitol);
    }

    /**
     * Aplica l'estil de botó de gestió a un JButton.
     * @param btn el botó al qual s'aplica l'estil
     * @param text el text que mostrarà el botó
     */
    static void estilitzarBotoGestio(JButton btn, String text) {
        estilitzarBotoPrimari(btn, text);
        // Mida més gran
        btn.setPreferredSize(new Dimension(-1, 70));
        // btn.setForeground(C_T);
    }

    /**
     * Aplica l'estil de botó primari a un JButton.
     * @param btn el botó al qual s'aplica l'estil
     * @param text el text que mostrarà el botó
     */
    static void estilitzarBotoPrimari(JButton btn, String text) {
        btn.setText(text);
        btn.setFont(F_TITOL);
        btn.setBackground(C_BTN);
        btn.setForeground(C_TEXT);

        // Desactivem el focus pintat
        // (és a dir, eliminar el requadre que apareix al voltant del text
        // d'un botó quan aquest està seleccionat o s'hi ha fet clic)
        // Per defecte és blau
        btn.setFocusPainted(false);

        // Vora daurada + marge intern
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(C_VORA, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        // Efecte hover (és a dir, el botó canvia de color quan el cursor
        // s'hi posa a sobre d'ell)
        // Passa a ser una mica més clar i el text es posa en daurat
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                // Background: fons
                btn.setBackground(new Color(0x4A, 0x68, 0x32));
                // Foreground: text
                btn.setForeground(C_OR);
            }
            @Override public void mouseExited(MouseEvent e) {
                // Restaurem els colors normals quan el ratolí surt del botó
                btn.setBackground(C_BTN);
                btn.setForeground(C_TEXT);
            }
        });
    }

    /**
     * Aplica l'estil de botó secundari a un JButton.
     * @param btn el botó al qual s'aplica l'estil
     * @param text el text que mostrarà el botó
     */
    static void estilitzarBotoSecundari(JButton btn, String text) {
        btn.setText(text);
        btn.setFont(F_COS);
        btn.setBackground(C_BTN2);
        btn.setForeground(C_TEXT2);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(-1, 50));

        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x60, 0x50, 0x30), 1),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        // També tenen hover però és més suau
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0x38, 0x48, 0x22));
                btn.setForeground(C_TEXT);
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(C_BTN2);
                btn.setForeground(C_TEXT2);
            }
        });
    }

    /** Aplica estil a un camp de text JTextField.
     * @param camp el camp de text al qual s'aplica l'estil
     */
    static void estilitzarCamp(JTextField camp) {
        camp.setBackground(C_INPUT);
        camp.setForeground(C_TEXT);

        // El cursor de text (en anglès: caret) és la línia vertical parpellejant que surt
        // en un camp de text quan vas a escriure. El posem en daurat perquè destaqui més sobre el fons fosc.
        camp.setCaretColor(C_OR);
        camp.setFont(F_COS);
        camp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x60, 0x50, 0x30), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    /** Aplica estil a una etiqueta JLabel.
     * @param lbl l'etiqueta a la qual s'aplica l'estil
     */
    static void estilitzarEtiqueta(JLabel lbl) {
        lbl.setForeground(C_TEXT2);
        lbl.setFont(F_ITALIC);
    }

    /** Aplica estil a una JLabel de títol */
    static void estilitzarTitol(JLabel lbl) {
        lbl.setForeground(C_OR);
        lbl.setFont(new Font("", Font.BOLD, 25));
    }

    /** Aplica estil a un JCheckBox.
     * @param chk la casella a la qual s'aplica l'estil
     */
    static void estilitzarCheckBox(JCheckBox chk) {
        chk.setBackground(C_PANEL);
        chk.setForeground(C_TEXT);
        chk.setFont(F_COS);
        chk.setFocusPainted(false);
    }

    /** Aplica estil a una llista JList de cadenes de text (Strings).
     * @param lst la llista a la qual s'aplica l'estil
     */
    static void estilitzarLlista(JList<String> lst) {
        lst.setBackground(C_INPUT);
        lst.setForeground(C_TEXT);
        lst.setFont(F_LLISTA);
        lst.setSelectionBackground(C_SEL);
        lst.setSelectionForeground(C_TEXT);

        // Alçada fixa per a cada fila de la llista
        lst.setFixedCellHeight(32);

        // El color de la fila es va intercanviant (fosc i claret) + marges interns
        lst.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                // Files parelles: C_INPUT; files senars: verd una mica diferent
                Color fonsNormal = index % 2 == 0 ? C_INPUT : new Color(0x1E, 0x2E, 0x20);
                setBackground(isSelected ? C_SEL : fonsNormal);
                setForeground(C_TEXT);
                setFont(F_LLISTA);

                // Marge intern per separar el text de les vores
                setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
                return this;
            }
        });
    }

    /** Aplica estil a un JScrollPane.
     * @param scroll el panell de scroll al qual s'aplica l'estil
     */
    static void estilitzarScrollPane(JScrollPane scroll) {
        scroll.setBackground(C_INPUT);

        // El viewport és l'àrea interior visible, a la qual també li posem fons
        scroll.getViewport().setBackground(C_INPUT);
        scroll.setBorder(BorderFactory.createLineBorder(C_VORA, 1));
    }

    /** Aplica estil a un desplegable JComboBox de cadenes de text (Strings).
     * @param cmb el desplegable al qual s'aplica l'estil
     */
    static void estilitzarComboBox(JComboBox<String> cmb) {
        cmb.setBackground(C_INPUT);
        cmb.setForeground(C_TEXT);
        cmb.setFont(F_COS);

        // Mida mínima per mostrar strings llargs
        cmb.setPreferredSize(new Dimension(520,36));


        // Canviar l'estètica del ComboBox
        cmb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? C_SEL : C_INPUT);
                setForeground(C_TEXT);
                setFont(F_COS);
                setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
                return this;
            }
        });
    }

    /** Aplica estil de botó "Acceptar" (color or) a un JButton.
     * @param btn el botó al qual s'aplica l'estil "Acceptar"
     */
    static void estilitzarBotoAcceptar(JButton btn) {
        btn.setFont(F_TITOL);
        btn.setBackground(new Color(0x4A, 0x36, 0x14));
        btn.setForeground(C_OR);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(C_OR, 1),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover: fons daurat quan passa el ratolí
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0x6A, 0x50, 0x1C));
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(0x4A, 0x36, 0x14));
            }
        });
    }

    /** Aplica estil de botó "Cancel·lar / Tornar" (color més fosc) a un JButton.
     * @param btn el botó al qual s'aplica l'estil "Cancel·lar"
     */
    static void estilitzarBotoCancel(JButton btn) {
        btn.setFont(F_COS);
        btn.setBackground(new Color(0x2A, 0x1E, 0x10));
        btn.setForeground(C_TEXT2);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x60, 0x50, 0x30), 1),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover: només el text s'aclareix, el fons no canvia
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setForeground(C_TEXT);
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setForeground(C_TEXT2);
            }
        });
    }
}
