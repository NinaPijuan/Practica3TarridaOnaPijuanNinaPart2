package prog2.vista;

import prog2.adaptador.Adaptador;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;

/*
POSAR ADAPTADOR COM A ATRIBUT A TOT ARREU I PASSAR-LO PER PARAMETRE (FET)
MOSTRAR LLISTES A ALGUN LLOC + REFRESCAR LLISTES
BOTO CANCELAR NOMÉS FA DISPOSE
BOTO ACCPETAR NOMÉS ENABLED QUAN ELS CAMPS ESTAN TOTS PLENS
BOTO ACCEPTAR GESTIONA EXCEPCIONS (JOPTIONPANE: JDIALOG SUPERMEGAPETIT)
FER GUARDAR I CARREGAR

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

    private Adaptador adaptador;

    // ── Paleta de colors ────────────────────────────────────────────────────
    static final Color C_FONS    = new Color(0x1C, 0x2B, 0x1E);
    static final Color C_PANEL   = new Color(0x24, 0x38, 0x27);
    static final Color C_OR      = new Color(0xC8, 0xA8, 0x50);
    static final Color C_TEXT    = new Color(0xF0, 0xE8, 0xD0);
    static final Color C_TEXT2   = new Color(0xB8, 0xAA, 0x88);
    static final Color C_VORA    = new Color(0xA0, 0x85, 0x50);
    static final Color C_BTN     = new Color(0x3A, 0x4E, 0x28);
    static final Color C_BTN2    = new Color(0x2A, 0x38, 0x1C);
    static final Color C_INPUT   = new Color(0x18, 0x26, 0x1A);
    static final Color C_SEL     = new Color(0x4A, 0x6B, 0x30);

    // ── Fonts ───────────────────────────────────────────────────────────────
    static final Font F_TITOL    = new Font("Georgia", Font.BOLD, 15);
    static final Font F_COS      = new Font("Georgia", Font.PLAIN, 13);
    static final Font F_ITALIC   = new Font("Georgia", Font.ITALIC, 12);
    static final Font F_LLISTA   = new Font("Courier New", Font.PLAIN, 12);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppBiblioUB app = new AppBiblioUB();
            app.setVisible(true);
        });
    }

    public AppBiblioUB() {
        adaptador = new Adaptador();
        // setModal(true) ??
        // Quan obres una finestra no pots tocar l'altre??

        // Utilitzar adaptador en els JDialog, passar-li l'adaptador
        // Quan es crea el frame, passes l'adaptador

        // JDialog AfegirPrestec
        /*
            JComboBox i JList
            La persona selecciona l'element de la llista (ja no cal passar posició)
            Els objectes ja tenen mètodes que quan se selecciona l'element, tenen el getSelectedIndex().

            Mirar mètodes de cada element.

            JComboBoxUsuari, JComboBoxExemplar
            BotoCancelar, BotoOk
         */

        /*
            Quan afegim usuari o exemplar, el botó afegir ha d'actualitzar les llistes.
         */

        setTitle("BiblioUB — Sistema de Gestió de Biblioteca");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Aplicar estètica
        aplicarTema();

        btnGestioUsuaris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // creem pestanya, la fem modal i la fem visible (en akuest ordre!!)
                FrmGestioUsuaris frmGestioUsuaris = new FrmGestioUsuaris(AppBiblioUB.this, adaptador);
                frmGestioUsuaris.setModal(true);
                frmGestioUsuaris.setVisible(true);
            }
        });

        btnGestioExemplars.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmGestioExemplars frmGestioExemplars = new FrmGestioExemplars(AppBiblioUB.this, adaptador); // QUE PASSEM DE PARÀMETRES
                frmGestioExemplars.setModal(true);
                frmGestioExemplars.setVisible(true);
            }
        });

        btnGestioPrestecs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FrmGestioPrestecs frmGestioPrestecs = new FrmGestioPrestecs(AppBiblioUB.this, adaptador); // QUE PASSEM DE PARÀMETRES
                frmGestioPrestecs.setModal(true);
                frmGestioPrestecs.setVisible(true);
            }
        });


        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File fitxer;
                JFileChooser seleccio = new JFileChooser();
                int resultat = seleccio.showSaveDialog(AppBiblioUB.this);
                if (resultat == JFileChooser.APPROVE_OPTION) {
                    fitxer = seleccio.getSelectedFile();
                    try {
                        adaptador.guardaDades(fitxer.toString());
                        JOptionPane.showMessageDialog(AppBiblioUB.this, "Dades guardades", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (BiblioException ex) {
                        JOptionPane.showMessageDialog(AppBiblioUB.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnCarregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File fitxer;
                JFileChooser seleccio = new JFileChooser();
                int resultat = seleccio.showOpenDialog(AppBiblioUB.this);
                if (resultat == JFileChooser.APPROVE_OPTION) {
                    fitxer = seleccio.getSelectedFile();
                    try {
                        adaptador.carregaDades(fitxer.toString());
                        JOptionPane.showMessageDialog(AppBiblioUB.this, "Dades carergades", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (BiblioException ex) {
                        JOptionPane.showMessageDialog(AppBiblioUB.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }



    // VISUALS

    /** Aplica els colors i fonts del tema als components generats pel .form */
    private void aplicarTema() {
        // Panell principal
        panel.setBackground(C_FONS);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // Panells secundaris
        panelGestio.setBackground(C_FONS);
        panelPersistencia.setBackground(C_FONS);

        // Botons de gestió (primaris)
        estilitzarBotoPrimari(btnGestioExemplars, "Gestió d'Exemplars");
        estilitzarBotoPrimari(btnGestioUsuaris,   "Gestió d'Usuaris");
        estilitzarBotoPrimari(btnGestioPrestecs,  "Gestió de Préstecs");

        // Botons de fitxer (secundaris)
        estilitzarBotoSecundari(btnGuardar,  "Guardar dades");
        estilitzarBotoSecundari(btnCarregar, "Carregar dades");
    }

    static void estilitzarBotoPrimari(JButton btn, String text) {
        btn.setText(text);
        btn.setFont(F_TITOL);
        btn.setBackground(C_BTN);
        btn.setForeground(C_TEXT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(C_VORA, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0x4A, 0x68, 0x32));
                btn.setForeground(C_OR);
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(C_BTN);
                btn.setForeground(C_TEXT);
            }
        });
    }

    static void estilitzarBotoSecundari(JButton btn, String text) {
        btn.setText(text);
        btn.setFont(F_COS);
        btn.setBackground(C_BTN2);
        btn.setForeground(C_TEXT2);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x60, 0x50, 0x30), 1),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
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

    /** Aplica estil a un JTextField */
    static void estilitzarCamp(JTextField camp) {
        camp.setBackground(C_INPUT);
        camp.setForeground(C_TEXT);
        camp.setCaretColor(C_OR);
        camp.setFont(F_COS);
        camp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x60, 0x50, 0x30), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    /** Aplica estil a una JLabel d'etiqueta de camp */
    static void estilitzarEtiqueta(JLabel lbl) {
        lbl.setForeground(C_TEXT2);
        lbl.setFont(F_ITALIC);
    }

    /** Aplica estil a una JLabel de títol */
    static void estilitzarTitol(JLabel lbl) {
        lbl.setForeground(C_OR);
        lbl.setFont(new Font("Georgia", Font.BOLD, 16));
    }

    /** Aplica estil a un JCheckBox */
    static void estilitzarCheckBox(JCheckBox chk) {
        chk.setBackground(C_PANEL);
        chk.setForeground(C_TEXT);
        chk.setFont(F_COS);
        chk.setFocusPainted(false);
    }

    /** Aplica estil a un JList */
    static void estilitzarLlista(JList<String> lst) {
        lst.setBackground(C_INPUT);
        lst.setForeground(C_TEXT);
        lst.setFont(F_LLISTA);
        lst.setSelectionBackground(C_SEL);
        lst.setSelectionForeground(C_TEXT);
        lst.setFixedCellHeight(24);
        lst.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Color fonsNormal = index % 2 == 0 ? C_INPUT : new Color(0x1E, 0x2E, 0x20);
                setBackground(isSelected ? C_SEL : fonsNormal);
                setForeground(C_TEXT);
                setFont(F_LLISTA);
                setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
                return this;
            }
        });
    }

    /** Aplica estil a un JScrollPane */
    static void estilitzarScrollPane(JScrollPane scroll) {
        scroll.setBackground(C_INPUT);
        scroll.getViewport().setBackground(C_INPUT);
        scroll.setBorder(BorderFactory.createLineBorder(C_VORA, 1));
    }

    /** Aplica estil a un JComboBox */
    static void estilitzarComboBox(JComboBox<String> cmb) {
        cmb.setBackground(C_INPUT);
        cmb.setForeground(C_TEXT);
        cmb.setFont(F_COS);
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

    /** Aplica estil de botó Acceptar (or) */
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
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0x6A, 0x50, 0x1C));
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(0x4A, 0x36, 0x14));
            }
        });
    }

    /** Aplica estil de botó Cancel·lar / Tornar (fosc) */
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
