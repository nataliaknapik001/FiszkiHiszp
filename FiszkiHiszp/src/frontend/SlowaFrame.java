package frontend;

import backend.BazaDanych;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class SlowaFrame extends JFrame {
    private List<String> listaSlowekPolskich;
    private List<String> listaSlowekHiszpanskich;
    private final int idKategorii;
    private int aktualnyIndeks = 0;
    private int poprawneOdpowiedzi = 0;
    private long czasStartu;
    
    private JLabel etykietaSlowo;
    private JLabel etykietaLicznik;
    private JTextField poleOdpowiedz;
    private JButton przyciskSprawdz;
    private JButton przyciskPodpowiedz;
    private JButton przyciskNastepne;

    public SlowaFrame(int idKategorii) {
        this.idKategorii = idKategorii;
        
        // Inicjalizacja list
        this.listaSlowekPolskich = new ArrayList<>();
        this.listaSlowekHiszpanskich = new ArrayList<>();
        
        // Pobranie danych z bazy
        List<String> polskie = BazaDanych.pobierzSlowka(idKategorii, "polski");
        List<String> hiszpanskie = BazaDanych.pobierzSlowka(idKategorii, "hiszpanski");
        
        if (polskie != null) this.listaSlowekPolskich = polskie;
        if (hiszpanskie != null) this.listaSlowekHiszpanskich = hiszpanskie;

        this.czasStartu = System.currentTimeMillis();
        
        if (listaSlowekPolskich.isEmpty() || listaSlowekHiszpanskich.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Brak słówek w wybranej kategorii!",
                "Błąd",
                JOptionPane.ERROR_MESSAGE);
            new StartFrame().setVisible(true);
            dispose();
            return;
        }

        inicjalizujInterfejs();
        wyswietlNastepneSlowo();
    }

    private void inicjalizujInterfejs() {
        setTitle("Nauka słówek - Kategoria " + idKategorii);
        setSize(500, 350); // Zwiększono wysokość dla dodatkowego przycisku
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel górny
        JPanel panelGorny = new JPanel(new BorderLayout());
        etykietaSlowo = new JLabel("", SwingConstants.CENTER);
        etykietaSlowo.setFont(new Font("Arial", Font.BOLD, 24));
        panelGorny.add(etykietaSlowo, BorderLayout.CENTER);
        
        etykietaLicznik = new JLabel("", SwingConstants.RIGHT);
        etykietaLicznik.setFont(new Font("Arial", Font.PLAIN, 14));
        panelGorny.add(etykietaLicznik, BorderLayout.SOUTH);
        
        add(panelGorny, BorderLayout.NORTH);

        // Panel środkowy
        poleOdpowiedz = new JTextField();
        poleOdpowiedz.setFont(new Font("Arial", Font.PLAIN, 18));
        poleOdpowiedz.setHorizontalAlignment(JTextField.CENTER);
        add(poleOdpowiedz, BorderLayout.CENTER);

        // Panel dolny - teraz z 3 przyciskami
        JPanel panelDolny = new JPanel(new GridLayout(1, 3, 10, 10));
        
        przyciskPodpowiedz = new JButton("Pokaż odpowiedź");
        przyciskPodpowiedz.addActionListener(e -> pokazPodpowiedz());
        
        przyciskSprawdz = new JButton("Sprawdź (Enter)");
        przyciskSprawdz.addActionListener(e -> sprawdzOdpowiedz());
        
        przyciskNastepne = new JButton("Następne słówko");
        przyciskNastepne.addActionListener(e -> przejdzDoNastepnego());
        
        panelDolny.add(przyciskPodpowiedz);
        panelDolny.add(przyciskSprawdz);
        panelDolny.add(przyciskNastepne);
        
        add(panelDolny, BorderLayout.SOUTH);

        // Obsługa klawisza Enter
        poleOdpowiedz.addActionListener(e -> sprawdzOdpowiedz());
    }

    private void wyswietlNastepneSlowo() {
        if (aktualnyIndeks < listaSlowekPolskich.size()) {
            etykietaSlowo.setText(listaSlowekPolskich.get(aktualnyIndeks));
            etykietaLicznik.setText((aktualnyIndeks + 1) + "/" + listaSlowekPolskich.size());
            poleOdpowiedz.setText("");
            poleOdpowiedz.requestFocus();
        } else {
            zakonczNauke();
        }
    }

    private void przejdzDoNastepnego() {
        aktualnyIndeks++;
        wyswietlNastepneSlowo();
    }

    private void pokazPodpowiedz() {
        JOptionPane.showMessageDialog(this,
            "Poprawna odpowiedź: " + listaSlowekHiszpanskich.get(aktualnyIndeks),
            "Podpowiedź",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void sprawdzOdpowiedz() {
        String odpowiedz = poleOdpowiedz.getText().trim();
        if (odpowiedz.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Wpisz odpowiedź!", "Uwaga", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String poprawnaOdpowiedz = listaSlowekHiszpanskich.get(aktualnyIndeks);
        
        if (odpowiedz.equalsIgnoreCase(poprawnaOdpowiedz)) {
            poprawneOdpowiedzi++;
            JOptionPane.showMessageDialog(this, "Poprawna odpowiedź!", "Gratulacje", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Błędna odpowiedź!\nPoprawnie: " + poprawnaOdpowiedz,
                "Spróbuj jeszcze raz",
                JOptionPane.WARNING_MESSAGE);
        }
        
        przejdzDoNastepnego();
    }

    private void zakonczNauke() {
        long czasCwiczenia = (System.currentTimeMillis() - czasStartu) / 1000;
        double procent = (poprawneOdpowiedzi * 100.0) / listaSlowekPolskich.size();
        
        String podsumowanie = String.format(
            "<html><center><h2>Koniec nauki!</h2>" +
            "<p>Wynik: <b>%d/%d</b> (%.1f%%)</p>" +
            "<p>Czas: %d sekund</p>" +
            "</center></html>",
            poprawneOdpowiedzi, 
            listaSlowekPolskich.size(),
            procent,
            czasCwiczenia
        );
        
        JLabel etykietaPodsumowanie = new JLabel(podsumowanie);
        etykietaPodsumowanie.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JOptionPane.showMessageDialog(this, etykietaPodsumowanie, "Podsumowanie", JOptionPane.INFORMATION_MESSAGE);
        
        // Zapisz wyniki do bazy danych
        BazaDanych.zapiszWynikiNauki(
            idKategorii,
            listaSlowekPolskich.size(),
            poprawneOdpowiedzi,
            (int)czasCwiczenia
        );
        
        new StartFrame().setVisible(true);
        dispose();
    }
}