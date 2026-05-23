package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Finestra de diàleg per afegir un nou usuari a la biblioteca.
 */
public class FrmAfegirUsuari extends JDialog {
    private JPanel contentPane;
    private JButton btnAcceptar;
    private JButton btnCancelar;
    private JTextField txtNom;
    private JTextField txtEmail;
    private JTextField txtAdreca;
    private JCheckBox chkEstudiant;
    private JLabel etNom;
    private JLabel etEmail;
    private JLabel etAdreca;
    private JLabel etFormat;
    private JPanel panelInferior;
    private JPanel panelBtns;
    private JPanel panelSuperior;
    private JPanel panelNom;
    private JPanel panelEmail;
    private JPanel panelAdreca;
    private JPanel panelChk;
    private Adaptador adaptador;

    /**
     * Crea i configura el diàleg per afegir un usuari.
     * @param parent el JDialog pare que llança aquest diàleg
     * @param adaptador l'adaptador que proporciona accés al model
     */
    public FrmAfegirUsuari(JDialog parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;

        setContentPane(contentPane);
        setSize(580, 420);
        setLocationRelativeTo(null);
        setTitle("Afegir Usuari — BiblioUB");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(btnAcceptar);

        // ESTÈTICA
        // ── Tema ─────────────────────────────────────────────────────────────
        contentPane.setBackground(AppBiblioUB.C_PANEL);
        contentPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 0, 0, 0, AppBiblioUB.C_OR),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));

        panelSuperior.setBackground(AppBiblioUB.C_PANEL);
        panelInferior.setBackground(AppBiblioUB.C_PANEL);
        panelBtns.setBackground(AppBiblioUB.C_PANEL);
        panelNom.setBackground(AppBiblioUB.C_PANEL);
        panelAdreca.setBackground(AppBiblioUB.C_PANEL);
        panelEmail.setBackground(AppBiblioUB.C_PANEL);
        panelChk.setBackground(AppBiblioUB.C_PANEL);

        AppBiblioUB.estilitzarEtiqueta(etNom);
        AppBiblioUB.estilitzarEtiqueta(etEmail);
        AppBiblioUB.estilitzarEtiqueta(etAdreca);
        AppBiblioUB.estilitzarCamp(txtNom);
        AppBiblioUB.estilitzarCamp(txtEmail);
        AppBiblioUB.estilitzarCamp(txtAdreca);
        AppBiblioUB.estilitzarCheckBox(chkEstudiant);
        AppBiblioUB.estilitzarBotoAcceptar(btnAcceptar);
        AppBiblioUB.estilitzarBotoCancel(btnCancelar);

        // L'etiqueta d'avís de format d'email rep un estil especial
        // Font: cursiva mida 11 i de color vermell
        etFormat.setFont(new Font("", Font.ITALIC, 12));
        etFormat.setForeground(new Color(215, 22, 22));

        // BOTÓ ACCEPTAR:
        // Al principi desactivat fins que els camps siguin vàlids
        btnAcceptar.setEnabled(false);

        // L'etiqueta és invisible fins que calgui mostrar-la
        etFormat.setVisible(false);

        /* Els DocumentListeners detecten quan s'insereix o s'esborra en un camp de text
            txtNom i txtAdreca: criden actualitzarActivacioBtnAcceptar()
            txtEmail: crida validarEmail(), que a més de recalcular l'estat del botó "Acceptar",
                                            també gestiona la visibilitat de etFormat
         */
        txtNom.getDocument().addDocumentListener(new DocumentListener() {
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

        txtEmail.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validarEmail();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                validarEmail();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                validarEmail();
            }
        });

        txtAdreca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { actualitzarActivacioBtnAcceptar();}
            @Override
            public void removeUpdate(DocumentEvent e) { actualitzarActivacioBtnAcceptar();}
            @Override
            public void changedUpdate(DocumentEvent e) { actualitzarActivacioBtnAcceptar();}
        });

        // Crida l'adaptador per afegir el nou usuari
        // En cas de poder, mostra la confirmació i tanca el diàleg
        // En cas d'error, mostra el missatge a l'usuari
        btnAcceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    adaptador.afegirUsuari(txtEmail.getText(), txtNom.getText(), txtAdreca.getText(), chkEstudiant.isSelected());
                    JOptionPane.showMessageDialog(parent, "Usuari afegit correctament", "", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    // FrmGestioUsuaris refresca la llista

                } catch (BiblioException ex) {
                    JOptionPane.showMessageDialog(FrmAfegirUsuari.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // BOTÓ CANCEL·LAR
        // Tanca el diàleg sense fer cap canvi
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });


    }


    // MÈTODES AUXILIARS

    /**
     * Activa o desactiva el botó "Acceptar" comprovant que els tres camps
     * siguin no buits i que l'email tingui un format mínim vàlid.
     */
    private void actualitzarActivacioBtnAcceptar() {

        boolean plens = !txtNom.getText().trim().isEmpty() &&
                        !txtEmail.getText().trim().isEmpty() &&
                        !txtAdreca.getText().trim().isEmpty() &&
                        txtEmail.getText().contains("@") &&
                        txtEmail.getText().contains(".");
        btnAcceptar.setEnabled(plens);
    }

    /**
     * Valida el format mínim de l'email i mostra o amaga l'etiqueta etFormat.
     */
    private void validarEmail() {
        String email = txtEmail.getText().trim();
        boolean valid = email.contains("@") && email.contains(".");

        // Mostrar l'avís només si hi ha text però el format és incorrecte
        etFormat.setVisible(!email.isEmpty() && !valid);

        // Recalcular l'estat del botó "Acceptar"
        actualitzarActivacioBtnAcceptar();
    }
}
