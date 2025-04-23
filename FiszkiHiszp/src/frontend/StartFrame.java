package frontend;

import backend.BazaDanych;
import javax.swing.*;
import java.util.Map;

public class StartFrame extends javax.swing.JFrame {
    private Map<String, Integer> mapaKategorii;

    public StartFrame() {
        initComponents();
        setTitle("Fiszki - strona główna");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        btnRozpocznij.setEnabled(false);
        
        btnModyfikuj.setEnabled(false);
        
        mniOProgramie.addActionListener(e -> pokazOProgramie());
        mniInstrukcja.addActionListener(e -> pokazInstrukcje()); 
        
        // Dodanie funkcjonalności wyjścia
        mnuWyjdz.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int wybor = JOptionPane.showConfirmDialog(
                    StartFrame.this,
                    "Czy na pewno chcesz wyjść z programu?",
                    "Potwierdzenie wyjścia",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                
                if (wybor == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        zaladujKategorie();
    }
    private void zaladujKategorie() {
        mapaKategorii = BazaDanych.pobierzKategorie();
        cmbKategorie.removeAllItems();

        if (mapaKategorii != null && !mapaKategorii.isEmpty()) {
            for (String kategoria : mapaKategorii.keySet()) {
                cmbKategorie.addItem(kategoria);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Brak kategorii do wyświetlenia",
                "Informacja",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void pokazOProgramie() {
        JOptionPane.showMessageDialog(this,
            "Fiszki hiszpańsko-polskie ułatwiające naukę języka obcego.",
            "O programie",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void pokazInstrukcje() {
        JOptionPane.showMessageDialog(this, """
                                            1. Wybierz kategorię słówek
                                            2. Kliknij 'Rozpocznij'
                                            3. Wpisz tłumaczenie wyświetlonego słowa
                                            4. Sprawdź poprawność odpowiedzi
                                            5. Ucz się dalej!""",
            "Instrukcja",
            JOptionPane.INFORMATION_MESSAGE);
    }
                               
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        cmbKategorie = new javax.swing.JComboBox<>();
        lblWybierzKat = new javax.swing.JLabel();
        btnRozpocznij = new javax.swing.JButton();
        btnModyfikuj = new javax.swing.JButton();
        mbrMenu = new javax.swing.JMenuBar();
        mnuOProgramie = new javax.swing.JMenu();
        mniInstrukcja = new javax.swing.JMenuItem();
        mniOProgramie = new javax.swing.JMenuItem();
        mnuWyjdz = new javax.swing.JMenu();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        cmbKategorie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKategorieActionPerformed(evt);
            }
        });

        lblWybierzKat.setText("Wybierz kategorię");

        btnRozpocznij.setText("Rozpocznij");
        btnRozpocznij.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRozpocznijActionPerformed(evt);
            }
        });

        btnModyfikuj.setText("Modyfikuj");
        btnModyfikuj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModyfikujActionPerformed(evt);
            }
        });

        mnuOProgramie.setText("O programie");

        mniInstrukcja.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniInstrukcja.setText("Instrukcja");
        mnuOProgramie.add(mniInstrukcja);

        mniOProgramie.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniOProgramie.setText("O programie");
        mniOProgramie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniOProgramieActionPerformed(evt);
            }
        });
        mnuOProgramie.add(mniOProgramie);

        mbrMenu.add(mnuOProgramie);

        mnuWyjdz.setText("Wyjdź");
        mbrMenu.add(mnuWyjdz);

        setJMenuBar(mbrMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblWybierzKat, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbKategorie, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnRozpocznij)
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnModyfikuj, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWybierzKat)
                    .addComponent(cmbKategorie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRozpocznij, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(btnModyfikuj, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbKategorieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKategorieActionPerformed
    String wybranaKategoria = (String) cmbKategorie.getSelectedItem();
    if (wybranaKategoria != null) {
        System.out.println("Wybrano kategorię: " + wybranaKategoria);
        btnRozpocznij.setEnabled(true);
        
        btnModyfikuj.setEnabled(true);
    } else {
        btnRozpocznij.setEnabled(false);
       
        btnModyfikuj.setEnabled(false);
    }   
    }//GEN-LAST:event_cmbKategorieActionPerformed

    private void btnStatystykiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatystykiActionPerformed
    String wybranaKategoria = (String) cmbKategorie.getSelectedItem();
    if (wybranaKategoria == null) {
        JOptionPane.showMessageDialog(this, 
            "Proszę wybrać kategorię aby zobaczyć statystyki", 
            "Brak wyboru", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        Integer kategoriaId = mapaKategorii.get(wybranaKategoria);
        if (kategoriaId == null) {
            throw new Exception("Nie znaleziono ID dla wybranej kategorii");
        }
        new StatystykiFrame(kategoriaId).setVisible(true);
        this.dispose();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Błąd podczas ładowania statystyk: " + e.getMessage(),
            "Błąd",
            JOptionPane.ERROR_MESSAGE);
         }
    }//GEN-LAST:event_btnStatystykiActionPerformed

    private void btnRozpocznijActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRozpocznijActionPerformed
    String wybranaKategoria = (String) cmbKategorie.getSelectedItem();
    if (wybranaKategoria == null) {
        JOptionPane.showMessageDialog(this, 
            "Proszę wybrać kategorię przed rozpoczęciem nauki", 
            "Brak wyboru", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        int kategoriaId = mapaKategorii.get(wybranaKategoria);
        new SlowaFrame(kategoriaId).setVisible(true);
        this.dispose();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Błąd podczas uruchamiania nauki: " + e.getMessage(),
            "Błąd",
            JOptionPane.ERROR_MESSAGE);
   
        }                                    
    }//GEN-LAST:event_btnRozpocznijActionPerformed

    private void btnModyfikujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModyfikujActionPerformed
    String wybranaKategoria = (String) cmbKategorie.getSelectedItem();
     if (wybranaKategoria == null) {
         JOptionPane.showMessageDialog(this, "Nie wybrano kategorii!", "Błąd", JOptionPane.ERROR_MESSAGE);
         return;
     }

     try {
         Integer kategoriaId = mapaKategorii.get(wybranaKategoria);
         if (kategoriaId == null) {
             throw new Exception("Nie znaleziono ID dla wybranej kategorii");
         }
         new ModyfikujFrame(kategoriaId).setVisible(true);
         this.dispose();
     } catch (Exception e) {
         JOptionPane.showMessageDialog(this,
             "Błąd podczas otwierania modyfikacji: " + e.getMessage(),
             "Błąd",
             JOptionPane.ERROR_MESSAGE);
     }
    }//GEN-LAST:event_btnModyfikujActionPerformed

    private void mniOProgramieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniOProgramieActionPerformed
                                     
        pokazOProgramie();
    }//GEN-LAST:event_mniOProgramieActionPerformed
     public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new StartFrame().setVisible(true);
        });
    }
    
     

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModyfikuj;
    private javax.swing.JButton btnRozpocznij;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbKategorie;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JLabel lblWybierzKat;
    private javax.swing.JMenuBar mbrMenu;
    private javax.swing.JMenuItem mniInstrukcja;
    private javax.swing.JMenuItem mniOProgramie;
    private javax.swing.JMenu mnuOProgramie;
    private javax.swing.JMenu mnuWyjdz;
    // End of variables declaration//GEN-END:variables
}

