package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

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


    // és raro però em penso kue el chkAdmetLlarg no necessita escoltador pk hi ha el mètode
    // chk.isSelected() kue ja funciona
    public FrmAfegirExemplar(JDialog parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setSize(460, 300);
        setLocationRelativeTo(null);
        setTitle("Afegir Exemplar — BiblioUB");
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
        panelTitol.setBackground(AppBiblioUB.C_PANEL);
        panelAutor.setBackground(AppBiblioUB.C_PANEL);
        panelId.setBackground(AppBiblioUB.C_PANEL);
        panelBtns.setBackground(AppBiblioUB.C_PANEL);
        panelChk.setBackground(AppBiblioUB.C_PANEL);


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
        // de primeres està desactivat
        btnAcceptar.setEnabled(false);

        // els escoltadors dels tres textfield mirem si cal activa-lo
        // CHAT DIU KUE AKUEST ÉS EL MILLOR PER DETECTAR KUAN ENTREN COSES EN UN JTEXTFIELD
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

        // escoltador del botó un cop activat
        btnAcceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    adaptador.afegirExemplar(txtId.getText(), txtTitol.getText(), txtAutor.getText(), chkAdmetLlarg.isSelected());
                    JOptionPane.showMessageDialog(parent, "Exemplar afegit correctament", "", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                    // CAL REFRESCAR LA LLISTA??

                } catch (BiblioException ex) {
                    JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // BOTÓ CANCEL·LAR
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });



    }

    /*
    * Mètode kue activa el botó acceptar si els tres jtextfields estan plens
     */
    private void actualitzarActivacioBtnAcceptar() {

        boolean plens = !txtTitol.getText().trim().isEmpty() &&
                        !txtAutor.getText().trim().isEmpty() &&
                        !txtId.getText().trim().isEmpty();

        btnAcceptar.setEnabled(plens);
    }
}
