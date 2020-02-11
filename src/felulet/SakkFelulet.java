package felulet;

import logika.SakkTabla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.TimerTask;

public class SakkFelulet extends JFrame {
    private Container foAblak;
    private JPanel pnlJatekTabla,pnlAllapotSav,pnlOldalso;
    private SakkTabla tabla;
    private Mezo kezdoMezo, erkezesiMezo;
    private JLabel lepo,feketePont,feherPont,allapot;
    private JList lepesekLista;
    private long stopperInditasa;
    private JLabel lblStopper;
    private Timer tmr;

    public SakkFelulet() {

        this.tabla = new SakkTabla();
        this.initComponents();
        this.sakkTablaMegjelenit();
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
        this.pnlJatekTabla = new JPanel();
        this.pnlJatekTabla.setLayout(new GridLayout(9, 9));
        this.foAblak.add(BorderLayout.CENTER, this.pnlJatekTabla);

        this.feherPont=new JLabel();
        this.feherPont.setText("Fehér: 16");
        this.feherPont.setBorder(BorderFactory.createLineBorder(Color.black));
        this.feherPont.setPreferredSize(new Dimension(100,50));
        this.feherPont.setHorizontalAlignment((int)CENTER_ALIGNMENT);

        this.feketePont=new JLabel();
        this.feketePont.setText("Fekete: 16");
        this.feketePont.setBorder(BorderFactory.createLineBorder(Color.black));
        this.feketePont.setPreferredSize(new Dimension(100,50));
        this.feketePont.setHorizontalAlignment((int)CENTER_ALIGNMENT);


        this.allapot=new JLabel();
        this.allapot.setText("Állapot:");
        this.allapot.setBorder(BorderFactory.createLineBorder(Color.black));
        this.allapot.setPreferredSize(new Dimension(100,50));
        this.allapot.setHorizontalAlignment((int)CENTER_ALIGNMENT);

        this.pnlAllapotSav=new JPanel();
        this.pnlAllapotSav.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        this.pnlAllapotSav.setBorder(BorderFactory.createLineBorder(Color.black));

        pnlAllapotSav.setPreferredSize(new Dimension(50,50));
        this.pnlAllapotSav.add(this.allapot);
        this.pnlAllapotSav.add(this.feherPont);
        this.pnlAllapotSav.add(this.feketePont);
        this.foAblak.add(BorderLayout.SOUTH,this.pnlAllapotSav);

        this.lepesekLista=new JList<>();
        this.lepo=new JLabel();
        this.lepo.setText("Fekete");
        this.lepo.setSize(50,50);
        this.pnlOldalso=new JPanel();
        this.pnlOldalso.setLayout(new FlowLayout());
        this.pnlOldalso.setBorder(BorderFactory.createLineBorder(Color.black));
        this.pnlOldalso.add(this.lepo);
        this.pnlOldalso.add(this.lepesekLista);
        this.foAblak.add(BorderLayout.EAST,this.pnlOldalso);



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
        Mezo aktualisMezo = (Mezo)me.getSource();

        if (kezdoMezo == null && aktualisMezo.getErtek() != 0){
            kezdoMezo = aktualisMezo;
        } else if (kezdoMezo != null && erkezesiMezo == null && aktualisMezo != kezdoMezo){
            erkezesiMezo = aktualisMezo;
        }

        if (kezdoMezo != null && erkezesiMezo != null){
            int sx = kezdoMezo.getPozicioX();
            int sy = kezdoMezo.getPozicioY();

            int dx = erkezesiMezo.getPozicioX();
            int dy = erkezesiMezo.getPozicioY();

            this.tabla.lep(sx,sy,dx,dy);

            this.kezdoMezo.setErtek(this.tabla.getErtek(sx,sy));
            this.erkezesiMezo.setErtek(this.tabla.getErtek(dx,dy));

            this.kezdoMezo = null;
            this.erkezesiMezo = null;

        }
    }
//    public void TimerTick(){
//        this.tmr = new Timer();
//        this.tmr.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                lblStopper.setText(String.format("%02d:%02d",
//                        (new Date().getTime() - stopperInditasa)/1000/60,
//                        ((new Date().getTime() - stopperInditasa)/1000)%60));
//            }
//        },0,100);
//    }
}
