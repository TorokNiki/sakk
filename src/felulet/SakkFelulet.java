package felulet;

import logika.SakkTabla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SakkFelulet extends JFrame {
    private Container foAblak;
    private JPanel pnlJatekTabla, pnlAllapotSav, pnlOldalso;
    private SakkTabla tabla;
    private Mezo kezdoMezo, erkezesiMezo;
    private JLabel lepo, feketePont, feherPont, allapot;
    private DefaultListModel<String> lepesekListaModel;
    private JList<String> lepesekLista;
    private long stopperInditasa;
    private JLabel lblStopper;
    private java.util.Timer tmr;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem mentes, betoltes, ujJatek;
    private int Feher ;
    private int Fekete;

    public SakkFelulet() {

        this.tabla = new SakkTabla();
        this.initComponents();
        this.sakkTablaMegjelenit();
        stopperInditasa = new Date().getTime();
        TimerTick();
    }

    public void initComponents() {
        this.setTitle("Sakk 1.0");
//    this.setUndecorated(true);
        // this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int hight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setSize(width, hight);
        this.setAlwaysOnTop(true);

        this.setLocationRelativeTo(null);
        this.foAblak = this.getContentPane();
        this.foAblak.setLayout(new BorderLayout(10, 10));

        menuBar = new JMenuBar();
        menu = new JMenu("Opciók");
        mentes = new JMenuItem(new AbstractAction("Játék mentése") {
            public void actionPerformed(ActionEvent e) {
                jatekMentes();
            }
        });
        betoltes = new JMenuItem("Játék betőltése");
        ujJatek = new JMenuItem(new AbstractAction("Játék újraindítása") {
            public void actionPerformed(ActionEvent e) {
                tabla = new SakkTabla();
                sakkTablaMegjelenit();
                lepesekListaModel.clear();
                lepo.setText("Fehér");
                stopperInditasa = new Date().getTime();
                TimerTick();
            }
        });
        menu.add(mentes);
        menu.add(betoltes);
        menu.add(ujJatek);
        menuBar.add(menu);

        this.foAblak.add(BorderLayout.NORTH, menuBar);


        this.pnlJatekTabla = new JPanel();
        this.pnlJatekTabla.setLayout(new GridLayout(9, 9));
        this.foAblak.add(BorderLayout.CENTER, this.pnlJatekTabla);

        this.feherPont = new JLabel();
        this.feherPont.setText("Fehér: " + tabla.getSotetFigurakSzama());
        this.feherPont.setBorder(BorderFactory.createLineBorder(Color.black));
        this.feherPont.setPreferredSize(new Dimension(100, 50));
        this.feherPont.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        this.feketePont = new JLabel();
        this.feketePont.setText("Fekete: " + tabla.getVilagosFigurakSzama());
        this.feketePont.setBorder(BorderFactory.createLineBorder(Color.black));
        this.feketePont.setPreferredSize(new Dimension(100, 50));
        this.feketePont.setHorizontalAlignment((int) CENTER_ALIGNMENT);


        this.allapot = new JLabel();
        this.allapot.setText("Állapot:");
        this.allapot.setBorder(BorderFactory.createLineBorder(Color.black));
        this.allapot.setPreferredSize(new Dimension(100, 50));
        this.allapot.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        this.pnlAllapotSav = new JPanel();
        this.pnlAllapotSav.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.pnlAllapotSav.setBorder(BorderFactory.createLineBorder(Color.black));

        pnlAllapotSav.setPreferredSize(new Dimension(50, 50));
        this.pnlAllapotSav.add(this.allapot);
        this.pnlAllapotSav.add(this.feherPont);
        this.pnlAllapotSav.add(this.feketePont);
        this.foAblak.add(BorderLayout.SOUTH, this.pnlAllapotSav);

        this.lblStopper = new JLabel();
        this.lepesekListaModel = new DefaultListModel<>();
        this.lepesekLista = new JList<>(lepesekListaModel);
        this.lepesekLista.setAutoscrolls(true);
        this.lepo = new JLabel();
        this.lepo.setText("Fehér");
        this.lepo.setSize(50, 50);
        this.pnlOldalso = new JPanel();
        this.pnlOldalso.setLayout(new GridLayout(3, 1));
        this.pnlOldalso.setBorder(BorderFactory.createLineBorder(Color.black));
        this.pnlOldalso.setPreferredSize(new Dimension(150, 50));
        this.pnlOldalso.add(this.lblStopper);
        this.pnlOldalso.add(this.lepo);
        this.pnlOldalso.add(new JScrollPane(this.lepesekLista));

        this.foAblak.add(BorderLayout.EAST, this.pnlOldalso);


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void sakkTablaMegjelenit() {
        this.pnlJatekTabla.removeAll();
        this.pnlJatekTabla.add(new JLabel());
        for (int i = 0; i < 8; i++) {
            JLabel hivatkozasABC = new JLabel();
            hivatkozasABC.setText(String.format("%s.", (char) (i + 65)));
            hivatkozasABC.setHorizontalAlignment((SwingConstants.CENTER));
            hivatkozasABC.setVerticalAlignment((SwingConstants.CENTER));
            this.pnlJatekTabla.add(hivatkozasABC);
        }

        for (int i = 0; i < 8; i++) {
            JLabel hivatkozas123 = new JLabel();
            hivatkozas123.setText(String.format("%d.", (8 - i)));
            hivatkozas123.setHorizontalAlignment((SwingConstants.CENTER));
            hivatkozas123.setVerticalAlignment((SwingConstants.CENTER));
            this.pnlJatekTabla.add(hivatkozas123);
            for (int j = 0; j < 8; j++) {
                Mezo m = new Mezo(i, j, tabla.getErtek(i, j));
                m.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        mezoKattintas(mouseEvent);

                    }


                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {
                        if (!m.getBackground().equals(Color.GRAY)) {
                            m.setBackground(Color.lightGray);
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {
                        if (!m.getBackground().equals(Color.GRAY)) {
                            m.frissit();
                        }
                    }
                });
                this.pnlJatekTabla.add(m);
            }
        }
        this.revalidate();
        this.repaint();

    }

    private void mezoKattintas(MouseEvent me) {
        Mezo aktualisMezo = (Mezo) me.getSource();

        if (kezdoMezo == null && aktualisMezo.getErtek() != 0) {
            kezdoMezo = aktualisMezo;
            ((Mezo) me.getSource()).setBackground(Color.GRAY);
        } else if (kezdoMezo != null && erkezesiMezo == null && aktualisMezo != kezdoMezo) {
            erkezesiMezo = aktualisMezo;
        }

        if (kezdoMezo != null && erkezesiMezo != null) {
            int sx = kezdoMezo.getPozicioX();
            int sy = kezdoMezo.getPozicioY();

            int dx = erkezesiMezo.getPozicioX();
            int dy = erkezesiMezo.getPozicioY();

            this.tabla.lep(sx, sy, dx, dy);

            this.kezdoMezo.setErtek(this.tabla.getErtek(sx, sy));
            this.erkezesiMezo.setErtek(this.tabla.getErtek(dx, dy));
            this.lepesekListaModel.addElement(getBabu(erkezesiMezo.getErtek()) + " " + getPozicio(kezdoMezo.getPozicioX(), kezdoMezo.getPozicioY()) + "-" + getPozicio(erkezesiMezo.getPozicioX(), erkezesiMezo.getPozicioY()));
            this.lepo.setText(getSzin(erkezesiMezo.getErtek()));
            this.kezdoMezo = null;
            this.erkezesiMezo = null;

        }
    }
    public void mattEllenorzes(int dx, int dy){
        if( this.tabla.isMatt(dx,dy)){

            if(this.lepo.getText().equals("Feher")){
                this.tabla.topListaMentes(this.lepo.getText(),Feher);
            }
            else {
                this.tabla.topListaMentes(this.lepo.getText(),Fekete);
            }

            tabla = new SakkTabla();
            sakkTablaMegjelenit();
            lepesekListaModel.clear();
            lepo.setText("Fehér");
            stopperInditasa = new Date().getTime();
            TimerTick();
        }
    }

    public void pontszam(String szin){
        if(szin.equals("Feher")){
            this.Feher++;
        }
        else {
            this.Fekete++;
        }
    }

    public String getSzin(int ertek) {
        switch (ertek) {
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                return "Fekete";
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
                return "Fehér";
        }
        return "";
    }

    public String getPozicio(int x, int y) {
        String pozicio = "";
        switch (y) {
            case 0:
                pozicio += "A";
                break;
            case 1:
                pozicio += "B";
                break;
            case 2:
                pozicio += "C";
                break;
            case 3:
                pozicio += "D";
                break;
            case 4:
                pozicio += "E";
                break;
            case 5:
                pozicio += "F";
                break;
            case 6:
                pozicio += "G";
                break;
            case 7:
                pozicio += "H";
                break;
        }
        switch (x) {
            case 0:
                pozicio += "8";
                break;
            case 1:
                pozicio += "7";
                break;
            case 2:
                pozicio += "6";
                break;
            case 3:
                pozicio += "5";
                break;
            case 4:
                pozicio += "4";
                break;
            case 5:
                pozicio += "3";
                break;
            case 6:
                pozicio += "2";
                break;
            case 7:
                pozicio += "1";
                break;
        }
        return pozicio;
    }

    public String getBabu(int ertek) {
        switch (ertek) {
            case 11:
                return "gyalog-fehér";
            case 12:
                return "bástya-fehér";
            case 13:
                return "huszár-fehér";
            case 14:
                return "futó-fehér";
            case 15:
                return "király-fehér";
            case 16:
                return "vezér-fehér";
            case 21:
                return "gyalog-fekete";
            case 22:
                return "bástya-fekete";
            case 23:
                return "huszár-fekete";
            case 24:
                return "futó-fekete";
            case 25:
                return "király-fekete";
            case 26:
                return "vezér-fekete";
        }
        return "";
    }

    public void TimerTick() {

        this.tmr = new Timer();
        this.tmr.schedule(new TimerTask() {

            @Override
            public void run() {
                lblStopper.setText(String.format("%02d:%02d",
                        (new Date().getTime() - stopperInditasa) / 1000 / 60,
                        ((new Date().getTime() - stopperInditasa) / 1000) % 60));
            }
        }, 0, 100);
    }

    private void jatekMentes() {
        try {
            FileWriter fw = new FileWriter("mentett.txt");

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    fw.write(this.tabla.getErtek(i, j) + " ");
                }
                fw.write(";");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("Hiba: " + e);
        }
        System.out.println("Mentett");
    }

}
