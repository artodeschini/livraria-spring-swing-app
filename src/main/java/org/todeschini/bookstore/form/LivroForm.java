package org.todeschini.bookstore.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.todeschini.bookstore.model.Livro;
import org.todeschini.bookstore.service.IBookStoreService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Component // permite o spring invocar essa classe como um dos seus componentes
public class LivroForm extends JFrame {

    private IBookStoreService service;
    private JTable tableLivros;

    private DefaultTableModel dtm;

    private JTextField jtId = new JTextField();
    private JTextField jtNome = new JTextField();
    private JTextField jtAutor = new JTextField();
    private JTextField jtPreco = new JTextField();
    private JTextField jtQuantidade = new JTextField();

    private JButton jbSalvar = new JButton();
    private JButton jbLimpar = new JButton();

    private JButton jbDeletar = new JButton();

    @Autowired // permite a injecao do Servico criado com spring
    public LivroForm(IBookStoreService service) {
        this.service = service;
        this.mock();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.init();
    }

    private void mock() {
        for (int i = 1; i < 5; i++) {
            service.saveLivro(Livro.builder().nome("l" + i).autor("a" + i).preco(10.0 * i).quantidade(i).build());
        }
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema de livros");

        this.configLayoutComponentsUI();
        this.addActionsListener();
    }

    private void addActionsListener() {
        // so virá informacao pela grid para o id
        jtId.setEnabled(false);
        jtId.setEditable(false);

        this.tableLivros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadLivroByGrid();
            }
        });

        // acao de salvar ou editar
        jbSalvar.addActionListener((e) -> {
            var livro = new Livro();
            try {

                // para adicionar ou editar
                if (!jtId.getText().equals("")) {
                    livro.setId(Integer.parseInt(jtId.getText()));
                }

                livro.setNome(validJtextFiled(jtNome, "Nome"));
                livro.setAutor(validJtextFiled(jtAutor, "Autor"));
                livro.setPreco(Double.valueOf(validJtextFiled(jtPreco, "Preço")));
                livro.setQuantidade(Integer.parseInt(validJtextFiled(jtQuantidade, "Quantidade")));

                if (livro.getId() == null) {
                    service.saveLivro(livro);
                } else {
                    service.saveLivro(livro);
                }
                limpar();

            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        });

        jbLimpar.addActionListener((e) -> {
            this.limpar();
        });

        jbDeletar.addActionListener((e) -> {
            if (jtId.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Você deve selecionar um item da grid para deletar" , "Selecione", JOptionPane.INFORMATION_MESSAGE );
            } else {
                service.deleteById(Integer.parseInt(jtId.getText()));
                limpar();
            }
        });
    }

    public void loadLivroByGrid() {
        var selectRow = tableLivros.getSelectedRow();

        if (selectRow != -1) {
            jtId.setText(tableLivros.getModel().getValueAt(selectRow, 0).toString()); // id
            jtNome.setText(tableLivros.getModel().getValueAt(selectRow, 1).toString()); // nomw
            jtAutor.setText(tableLivros.getModel().getValueAt(selectRow, 2).toString()); // autor
            jtPreco.setText(tableLivros.getModel().getValueAt(selectRow, 3).toString()); // preco
            jtQuantidade.setText(tableLivros.getModel().getValueAt(selectRow, 4).toString()); // quantidade
        }
    }

    private void configLayoutComponentsUI() {
        this.dtm = new DefaultTableModel(0, 5);

        String[] th = {"id", "Nome", "Autor", "Preço", "Quantidade"};
        this.dtm.setColumnIdentifiers(th); // cabecario

        this.tableLivros = new JTable(dtm);
        this.refleshTableLivros();

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(new JLabel(" Sistema de Controle de Livros"), BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(tableLivros), BorderLayout.CENTER);
        this.getContentPane().add(createPanelForm(), BorderLayout.WEST);
        this.getContentPane().add(createPanelBotoes(), BorderLayout.SOUTH);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private JPanel createPanelForm() {
        var grid = new JPanel();
        var lg = new GridLayout(5,2);

        grid.setLayout(lg);

        grid.add(new JLabel(" Id"));
        grid.add(jtId);

        grid.add(new JLabel(" Livro*"));
        grid.add(jtNome);

        grid.add(new JLabel(" Autor*"));
        grid.add(jtAutor);

        grid.add(new JLabel(" Preco*"));
        grid.add(jtPreco);

        grid.add(new JLabel(" Quantidade*"));
        grid.add(jtQuantidade);

        return grid;
    }

    public void showMessage(String campo) {
        JOptionPane.showMessageDialog(this, "Campo Obrigatorio " + campo + " deve ser preenchido" , "Campo Obrigatorio", JOptionPane.INFORMATION_MESSAGE );
    }

    public String validJtextFiled(JTextField jt, String msg) {
        if (!jt.getText().trim().equals("")) {
            return jt.getText();
        } else {
            jt.requestFocusInWindow(); // coloca o focus no elemento
            showMessage(msg);
            throw new RuntimeException("JtextFiled " + msg + " vazia precisa ser preenchido");
        }
    }

    public void limpar() {
        var fileds = new JTextField[] {jtId, jtNome, jtAutor, jtPreco, jtQuantidade};
        Arrays.stream(fileds).forEach((jt) -> {
            jt.setText("");
        });

        this.refleshTableLivros();
    }

    public JPanel createPanelBotoes() {
        jbSalvar.setText("Salvar");
        jbLimpar.setText("Limpar");
        jbDeletar.setText("Deletar");

        var botoes = new JPanel(new FlowLayout());
        botoes.add(jbSalvar);
        botoes.add(jbLimpar);
        botoes.add(jbDeletar);
        botoes.add(jbDeletar);

        return botoes;
    }

    private void refleshTableLivros() {
        this.dtm.setRowCount(0); // limpa a tabela

        // transforma livros um a um em Object[] e popula a tabela
        var livros = service.listarLivros();
        livros.forEach((l) -> {
            //create row of table
            Object[] row = {l.getId(), l.getNome(), l.getAutor(), l.getPreco(), l.getQuantidade()};
            dtm.addRow(row);
        });
    }
}
