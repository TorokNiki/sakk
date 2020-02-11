package felulet;

import logika.SakkTabla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
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

    public SakkFelulet() {

        this.tabla = new SakkTabla();
        this.initComponents();
        this.sakkTablaMegjelenit();
        stopperInditasa = new Date().getTime();
        TimerTick();
    }

    private void initComponents() {
        this.setTitle("Sakk 1.0");
//    this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int hight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setSize(width, hight);
        this.setAlwaysOnTop(true);

        this.setLocationRelativeTo(null);
        this.foAblak = this.getContentPane();
        this.foAblak.setLayout(new BorderLayout(10, 10));

        menuBar = new JMenuBar();
        menu = new JMenu();
        mentes = new JMenuItem();
        betoltes = new JMenuItem();
        ujJatek = new JMenuItem();
        menu.add(mentes);
        menu.add(betoltes);
        menu.add(ujJatek);
        menuBar.add(menu);

        this.foAblak.add(BorderLayout.NORTH, menuBar);


        this.pnlJatekTabla = new JPanel();
        this.pnlJatekTabla.setLayout(new GridLayout(9, 9));
        this.foAblak.add(BorderLayout.CENTER, this.pnlJatekTabla);

        this.feherPont = new JLabel();
        this.feherPont.setText("Fehér: 16");
        this.feherPont.setBorder(BorderFactory.createLineBorder(Color.black));
        this.feherPont.setPreferredSize(new Dimension(100, 50));
        this.feherPont.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        this.feketePont = new JLabel();
        this.feketePont.setText("Fekete: 16");
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
        this.pnlOldalso.setLayout(new GridLayout(3,1));
        this.pnlOldalso.setBorder(BorderFactory.createLineBorder(Color.black));
        this.pnlOldalso.setPreferredSize(new Dimension(150,50));
        this.pnlOldalso.add(this.lblStopper);
        this.pnlOldalso.add(this.lepo);
        this.pnlOldalso.add(new JScrollPane(this.lepesekLista));

        this.foAblak.add(BorderLayout.EAST, this.pnlOldalso);


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void sakkTablaMegjelenit() {
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
                        m.setBackground(Color.gray);

                    }


                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {
                        m.setBackground(Color.lightGray);
                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {
                        m.frissit();
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
            this.lepesekListaModel.addElement(getBabu(erkezesiMezo.getErtek())+" "+getPozicio(kezdoMezo.getPozicioX(), kezdoMezo.getPozicioY())+"-"+getPozicio(erkezesiMezo.getPozicioX(),erkezesiMezo.getPozicioY()));
            this.kezdoMezo = null;
            this.erkezesiMezo = null;

        }
    }
    public String getPozicio(int x, int y){
        String pozicio="";
        switch (y){
            case 0:
                pozicio+="A";
                break;
            case 1:
                pozicio+="B";
                break;
            case 2:
                pozicio+="C";
                break;
            case 3:
                pozicio+="D";
                break;
            case 4:
                pozicio+="E";
                break;
            case 5:
                pozicio+="F";
                break;
            case 6:
                pozicio+="G";
                break;
            case 7:
                pozicio+="H";
                break;
        }
        switch (x){
            case 0:
                pozicio+="8";
                break;
            case 1:
                pozicio+="7";
                break;
            case 2:
                pozicio+="6";
                break;
            case 3:
                pozicio+="5";
                break;
            case 4:
                pozicio+="4";
                break;
            case 5:
                pozicio+="3";
                break;
            case 6:
                pozicio+="2";
                break;
            case 7:
                pozicio+="1";
                break;
        }
        return pozicio;
    }
    public String getBabu(int ertek){
        switch (ertek) {
            case 11:
                return "gyalog-feher";
            case 12:
                return "bastya-feher";
            case 13:
                return "huszar-feher";
            case 14:
                return "futo-feher";
            case 15:
                return "kiraly-feher";
            case 16:
                return "vezer-feher";
            case 21:
                return "gyalog-fekete";
            case 22:
                return "bastya-fekete";
            case 23:
                return "huszar-fekete";
            case 24:
                return "futo-fekete";
            case 25:
                return "kiraly-fekete";
            case 26:
                return "vezer-fekete";
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
}
