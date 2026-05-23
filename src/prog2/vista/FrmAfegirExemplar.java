package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**
 * Finestra de diàleg per afegir un nou exemplar a la biblioteca.
 */
public class FrmAfegirExemplar extends JDialog {
    private JPanel contentPane;
    private JButton btnAcceptar;
    private JButton btnCancelar;
    private JTextField txtId;
    private JTextField txtTitol;
    private JTextField txtAutor;
    private JCheckBox chkAdmetLlarg;
    private JLabel etId;
    private JLabel etTitol;
    private JLabel etAutor;
    private JPanel panelInferior;
    private JPanel panelSuperior;
    private JPanel panelId;
    private JPanel panelTitol;
    private JPanel panelAutor;
    private JPanel panelChk;
    private JPanel panelBtns;

    private Adaptador adaptador;


    /**
     * Crea i configura el diàleg per afegir un exemplar.
     * @param parent el JDialog pare que llança aquest diàleg,
     *               s'utilitza per centrar la finestra i com a pare dels
     *               missatges emergents JOptionPane
     * @param adaptador l'adaptador que proporciona accés al model
     */
    public FrmAfegirExemplar(JDialog parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;

        // Assignem el panell principal com a contingut del diàleg
        setContentPane(contentPane);

        // Mides i posició
        setSize(580, 400);
        setLocationRelativeTo(null);

        // Títol
        setTitle("Afegir Exemplar — BiblioUB");

        // Tancar un JDialog (diferent a com es tanca el JFrame)
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Prémer Enter activa el botó Acceptar
        getRootPane().setDefaultButton(btnAcceptar);

        // ESTÈTICA
        // ── Tema ─────────────────────────────────────────────────────────────

        // Fons C_PANEL i el Border
        contentPane.setBackground(AppBiblioUB.C_PANEL);
        contentPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 0, 0, 0, AppBiblioUB.C_OR), // franja superior
                BorderFactory.createEmptyBorder(16, 20, 16, 20) // marges interiors
        ));

        // Tots els subpanells tenen el mateix color de fons
        // (els JPanels per defecte són blancs)
        // (cal posar-ho a TOTS)
        panelSuperior.setBackground(AppBiblioUB.C_PANEL);
        panelInferior.setBackground(AppBiblioUB.C_PANEL);
        panelTitol.setBackground(AppBiblioUB.C_PANEL);
        panelAutor.setBackground(AppBiblioUB.C_PANEL);
        panelId.setBackground(AppBiblioUB.C_PANEL);
        panelBtns.setBackground(AppBiblioUB.C_PANEL);
        panelChk.setBackground(AppBiblioUB.C_PANEL);

        // Cal aplicar l'estil a cada component
        AppBiblioUB.estilitzarEtiqueta(etId);
        AppBiblioUB.estilitzarEtiqueta(etTitol);
        AppBiblioUB.estilitzarEtiqueta(etAutor);
        AppBiblioUB.estilitzarCamp(txtId);
        AppBiblioUB.estilitzarCamp(txtTitol);
        AppBiblioUB.estilitzarCamp(txtAutor);
        AppBiblioUB.estilitzarCheckBox(chkAdmetLlarg);
        AppBiblioUB.estilitzarBotoAcceptar(btnAcceptar);
        AppBiblioUB.estilitzarBotoCancel(btnCancelar);

        // BOTÓ ACCEPTAR:
        // Desacticat fins que els tres camps tenen contingut
        btnAcceptar.setEnabled(false);

        // El DocumentListener detecta qualsevol canvi en un JTextField
        // Cada cop que l'usuari escriu o esborra text, es crida actualitzarActivacioBtnAcceptar()
        // que recalcula si el botó "Acceptar" ha d'estar actiu
        txtId.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }
        });

        txtTitol.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }
        });

        txtAutor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }
        });

        // Crida l'adaptador per afegir el nou exemplar
        // Si es pot, mostra un missatge informatiu i tanca el diàleg
        // Si l'adaptador llança BiblioException, mostra l'error
        btnAcceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    adaptador.afegirExemplar(txtId.getText(), txtTitol.getText(), txtAutor.getText(),
                            chkAdmetLlarg.isSelected());
                    JOptionPane.showMessageDialog(FrmAfegirExemplar.this, "Exemplar afegit " +
                            "correctament", "", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Tanca el diàleg

                    // FrmGestioExemplars refresca la llista

                } catch (BiblioException ex) {
                    // Mostra error
                    JOptionPane.showMessageDialog(FrmAfegirExemplar.this, ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // BOTÓ CANCEL·LAR
        // Simplement tanca el diàleg sense fer cap canvi
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });



    }

    // MÈTODE AUXILIAR

    /**
     * Activa o desactiva el botó "Acceptar" en funció de si els tres camps
     * obligatoris (ID, títol i autor) contenen text no buit.
     */
    private void actualitzarActivacioBtnAcceptar() {

        boolean plens = !txtTitol.getText().trim().isEmpty() &&
                        !txtAutor.getText().trim().isEmpty() &&
                        !txtId.getText().trim().isEmpty();

        btnAcceptar.setEnabled(plens);
    }
}
