package br.senai.sc.livros.view;

import br.senai.sc.livros.controller.PessoaController;

import javax.swing.*;

public class RemoverUsuario extends JFrame {
    private JPanel removerUsuario;
    private JTable tabelaUsuarios;
    private JButton voltarButton;
    private JButton removerButton;

    public RemoverUsuario() {
        criarComponentes();
        voltarButton.addActionListener(e -> {
            dispose();
            new Menu(Menu.getUsuario());
        });
        removerButton.addActionListener(e -> {
            int row = tabelaUsuarios.getSelectedRow();
            if (row != -1) {
                int id = (int) tabelaUsuarios.getValueAt(row, 0);
                PessoaController.removePessoaPorID(id);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um usuário!");
            }
        });
    }

        private void criarComponentes () {
            setContentPane(removerUsuario);
            RemoverUsuario.setModel(new DefaultTableModelArrayList(PessoaController.listarPessoas()));
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            pack();
            setVisible(true);
        }

}
