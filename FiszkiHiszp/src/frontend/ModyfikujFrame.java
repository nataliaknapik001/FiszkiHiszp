package frontend;

import backend.BazaDanych;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ModyfikujFrame extends JFrame {
    private final int kategoriaId;
    private final DefaultTableModel modelTabeli;
    private final JTable tabela;

    public ModyfikujFrame(int kategoriaId) {
        this.kategoriaId = kategoriaId;
        
        setTitle("Modyfikacja słówek - Kategoria " + kategoriaId);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        modelTabeli = new DefaultTableModel(new Object[]{"ID", "Polski", "Hiszpański", "Akcje"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2; // Edytowalne tylko kolumny z tekstem
            }
        };
        
        tabela = new JTable(modelTabeli);
        tabela.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        tabela.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        initComponents();
        zaladujSlowka();
    }

    private void initComponents() {
        JPanel panelGlowny = new JPanel(new BorderLayout(10, 10));
        panelGlowny.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Przyciski na górze
        JPanel panelPrzyciskow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JButton btnDodaj = new JButton("Dodaj nowe słówko");
        btnDodaj.addActionListener(this::dodajSlowko);
        
        JButton btnZapisz = new JButton("Zapisz zmiany");
        btnZapisz.addActionListener(this::zapiszZmiany);
        
        JButton btnPowrot = new JButton("Powrót");
        btnPowrot.addActionListener(e -> powrot());
        
        panelPrzyciskow.add(btnDodaj);
        panelPrzyciskow.add(btnZapisz);
        panelPrzyciskow.add(btnPowrot);

        // Tabela ze słówkami
        tabela.setRowHeight(30);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(650, 400));

        panelGlowny.add(panelPrzyciskow, BorderLayout.NORTH);
        panelGlowny.add(scrollPane, BorderLayout.CENTER);
        
        add(panelGlowny);
    }

    private void zaladujSlowka() {
        modelTabeli.setRowCount(0);
        List<Object[]> slowka = BazaDanych.pobierzSlowkaZId(kategoriaId);
        
        for (Object[] slowko : slowka) {
            Object[] row = new Object[]{
                slowko[0], // ID
                slowko[1], // Polski
                slowko[2], // Hiszpański
                "Usuń"     // Przycisk usuwania
            };
            modelTabeli.addRow(row);
        }
    }

    private void dodajSlowko(ActionEvent e) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        
        JTextField tfPolski = new JTextField(20);
        JTextField tfHiszpanski = new JTextField(20);
        
        panel.add(new JLabel("Słówko po polsku:"));
        panel.add(tfPolski);
        panel.add(new JLabel("Tłumaczenie na hiszpański:"));
        panel.add(tfHiszpanski);
        
        int result = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Dodaj nowe słówko", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String polski = tfPolski.getText().trim();
            String hiszpanski = tfHiszpanski.getText().trim();
            
            if (!polski.isEmpty() && !hiszpanski.isEmpty()) {
                if (BazaDanych.dodajSlowko(kategoriaId, polski, hiszpanski)) {
                    zaladujSlowka();
                } else {
                    JOptionPane.showMessageDialog(
                        this, 
                        "Nie udało się dodać słówka", 
                        "Błąd", 
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    private void zapiszZmiany(ActionEvent e) {
        for (int i = 0; i < modelTabeli.getRowCount(); i++) {
            int id = (int) modelTabeli.getValueAt(i, 0);
            String polski = modelTabeli.getValueAt(i, 1).toString();
            String hiszpanski = modelTabeli.getValueAt(i, 2).toString();
            
            if (!BazaDanych.aktualizujSlowko(id, polski, hiszpanski)) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Nie udało się zaktualizować słówka ID: " + id, 
                    "Błąd", 
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        }
        
        JOptionPane.showMessageDialog(
            this, 
            "Wszystkie zmiany zostały zapisane", 
            "Sukces", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void usunSlowko(int id) {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Czy na pewno chcesz usunąć to słówko?", 
            "Potwierdzenie usunięcia", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (BazaDanych.usunSlowko(id)) {
                zaladujSlowka();
            } else {
                JOptionPane.showMessageDialog(
                    this, 
                    "Nie udało się usunąć słówka", 
                    "Błąd", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void powrot() {
        dispose();
        new StartFrame().setVisible(true);
    }

    // Klasa wewnętrzna do renderowania przycisków w tabeli
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Klasa wewnętrzna do obsługi kliknięć przycisków w tabeli
    class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private int clickedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                int id = (int) modelTabeli.getValueAt(clickedRow, 0);
                usunSlowko(id);
                fireEditingStopped();
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
            clickedRow = row;
            button.setText(value.toString());
            return button;
        }

        public Object getCellEditorValue() {
            return button.getText();
        }
    }
}