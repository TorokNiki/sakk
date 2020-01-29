package felulet;

import javax.swing.*;
import java.awt.*;

public class Mezo extends JButton {
    private int x;
    private int y;
    private int ertek;

    public Mezo(int x, int y, int ertek) {
        this.x = x;
        this.y = y;
        this.ertek = ertek;
        this.frissit();
    }

    public void frissit() {
        if ((this.x + this.y) % 2 == 0) {
            this.setBackground(Color.decode("#FFFFFF"));
        } else {
            this.setBackground(Color.decode("#8B4513"));
        }
        String filNev = "img/ures.png";
        switch (this.ertek) {
            case 11:
                filNev = "img/11-gyalog-feher.png";
                break;
            case 12:
                filNev = "img/12-bastya-feher.png";
                break;
            case 13:
                filNev = "img/13-huszar-feher.png";
                break;
            case 14:
                filNev = "img/14-futo-feher.png";
                break;
            case 15:
                filNev = "img/15-kiraly-feher.png";
                break;
            case 16:
                filNev = "img/16-vezer-feher.png";
                break;
            case 21:
                filNev = "img/21-gyalog-fekete.png";
                break;
            case 22:
                filNev = "img/22-bastya-fekete.png";
                break;
            case 23:
                filNev = "img/23-huszar-fekete.png";
                break;
            case 24:
                filNev = "img/24-futo-fekete.png";
                break;
            case 25:
                filNev = "img/25-kiraly-fekete.png";
                break;
            case 26:
                filNev = "img/26-vezer-fekete.png";
                break;
        }
        Image img = new ImageIcon(filNev).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(img));
        this.revalidate();
        this.repaint();
    }
}
