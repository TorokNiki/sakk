package logika;

public class SakkTabla {
    private int[][] tabla;

    public SakkTabla() {
        this.tabla = new int[][]{
                {22, 23, 24, 25, 26, 24, 23, 22},
                {21, 21, 21, 21, 21, 21, 21, 21},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {11, 11, 11, 11, 11, 11, 11, 11},
                {12, 13, 14, 15, 16, 14, 13, 12}
        };
    }

    public int getErtek(int x, int y) {
        return this.tabla[x][y];
    }

    public void lep(int sx, int sy, int dx, int dy) {
        if (isErvenyesLepes(sx, sy, dx, dy)) {
            this.tabla[dx][dy] = this.tabla[sx][sy];
            this.tabla[sx][sy] = 0;
        }
    }

    public boolean isKivalasztottFigura(int x, int y, int figura) {
        return this.tabla[x][y] == figura;
    }

    public boolean isUresHely(int x, int y) {
        return isKivalasztottFigura(x, y, 0);
    }

    public boolean isFigura(int x, int y) {
        return !isUresHely(x, y);
    }

    public boolean isSotetFigura(int x, int y) {
        return this.tabla[x][y] >= 21 && this.tabla[x][y] <= 26;
    }

    public boolean isVilagosFigura(int x, int y) {
        return this.tabla[x][y] >= 11 && this.tabla[x][y] <= 16;
    }

    private int getFigurakSzama(int m, int n) {
        int db = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.tabla[i][j] >= m && this.tabla[i][j] <= n) {
                    db++;
                }
            }
        }
        return db;
    }

    public int getVilagosFigurakSzama() {
        return getFigurakSzama(11, 16);
    }

    public int getSotetFigurakSzama() {
        return getFigurakSzama(21, 26);
    }

    public int figurakSzama() {
        return getVilagosFigurakSzama() + getSotetFigurakSzama();
    }

    public boolean isErvenyesLepes(int sx, int sy, int dx, int dy) {
        if (isFigura(sx, sy)) {
            if (isKivalasztottFigura(sx, sy, 11) || isKivalasztottFigura(sx, sy, 21)) {
                return isErvenyesGyalogLepes(sx, sy, dx, dy);
            } else if (isKivalasztottFigura(sx, sy, 12) || isKivalasztottFigura(sx, sy, 22)) {
                return isErvenyesBastyaLepes(sx, sy, dx, dy);
            } else if (isKivalasztottFigura(sx, sy, 13) || isKivalasztottFigura(sx, sy, 23)) {
                return isErvenyesHuszarLepes(sx, sy, dx, dy);
            } else if (isKivalasztottFigura(sx, sy, 14) || isKivalasztottFigura(sx, sy, 24)) {
                return isErvenyesFutoLepes(sx, sy, dx, dy);
            } else if (isKivalasztottFigura(sx, sy, 15) || isKivalasztottFigura(sx, sy, 25)) {
                return isErvenyesKiralyLepes(sx, sy, dx, dy);
            } else if (isKivalasztottFigura(sx, sy, 16) || isKivalasztottFigura(sx, sy, 26)) {
                return isErvenyesVezerLepes(sx, sy, dx, dy);
            } else {
                return false;
            }
        } else return false;
    }

    public boolean isMatt() {
        return true;
    }

    public boolean isSakk() {
        return true;
    }

    public boolean isErvenyesGyalogLepes(int sx, int sy, int dx, int dy) {
        boolean helyesVilagosLepesE = false;
        boolean helyesSotetLepesE = false;
        if (isVilagosFigura(sx, sy)) {
            boolean kezdoLepes = (sx == 6 && sx - dx <= 2 && sy == dy);
            boolean lepes = (sx - dx == 1 && sy == dy && isUresHely(dx, dy));
            boolean utes = (sx - dx == 1 && Math.abs(sy - dy) == 1 && isSotetFigura(dx, dy));
            helyesVilagosLepesE = kezdoLepes || lepes || utes;
        } else if (isSotetFigura(sx, sy)) {
            boolean kezdoLepes = (sx == 1 && dx - sx <= 2 && sy == dy);
            boolean lepes = (dx - sx == 1 && sy == dy && isUresHely(dx, dy));
            boolean utes = (dx - sx == 1 && Math.abs(sy - dy) == 1 && isVilagosFigura(dx, dy));
            helyesSotetLepesE = kezdoLepes || lepes || utes;
        }
        return helyesSotetLepesE || helyesVilagosLepesE;
    }

    public boolean isErvenyesBastyaLepes(int sx, int sy, int dx, int dy) {
        boolean helyesVilagosLepesE = false;
        boolean helyesSotetLepesE = false;

        if(isVilagosFigura(sx,sy)){
            boolean kezdoLepes = ((sx == 7 && sy==0 )||(sx==0 && sy==0) &&(sx == dx || sy == dy) );
            boolean lepes = sx == dx || sy == dy && isUresHely(dx,dy);
            boolean utes =  sx == dx || sy == dy && isSotetFigura(dx,dy);

            helyesVilagosLepesE = kezdoLepes || lepes || utes;
        }
        else if(isSotetFigura(sx,sy)){
            boolean kezdoLepes = ((sx == 7 && sy==7 )||(sx==0 && sy==7) &&(sx == dx || sy == dy));
            boolean lepes = sx == dx || sy == dy && isUresHely(dx,dy);
            boolean utes =  sx == dx || sy == dy && isVilagosFigura(dx,dy);

            helyesSotetLepesE = kezdoLepes || lepes || utes;
        }
        return helyesVilagosLepesE || helyesSotetLepesE;
    }

    public boolean isErvenyesHuszarLepes(int sx, int sy, int dx, int dy) {
        boolean helyesVilagosLepesE = false;
        boolean helyesSotetLepesE = false;

        if(isVilagosFigura(sx,sy)){
            boolean kezdoLepes = ((sx == 6 && sy==0 )||(sx==1 && sy==0) &&((Math.abs(sx-dx)==2 && Math.abs(sy-dy)==1)||((Math.abs(sx-dx)==1 && Math.abs(sy-dy)==2))));
            boolean lepes = (Math.abs(sx-dx)==2 && Math.abs(sy-dy)==1)||((Math.abs(sx-dx)==1 && Math.abs(sy-dy)==2)) && isUresHely(dx,dy);
            boolean utes =  (Math.abs(sx-dx)==2 && Math.abs(sy-dy)==1)||((Math.abs(sx-dx)==1 && Math.abs(sy-dy)==2))&& isSotetFigura(dx,dy);

            helyesVilagosLepesE = kezdoLepes || lepes || utes;
        }
        else if(isSotetFigura(sx,sy)){
            boolean kezdoLepes = ((sx == 6 && sy==7 )||(sx==1 && sy==7) &&((Math.abs(sx-dx)==2 && Math.abs(sy-dy)==1)||((Math.abs(sx-dx)==1 && Math.abs(sy-dy)==2))));
            boolean lepes = (Math.abs(sx-dx)==2 && Math.abs(sy-dy)==1)||((Math.abs(sx-dx)==1 && Math.abs(sy-dy)==2))&& isUresHely(dx,dy);
            boolean utes =  (Math.abs(sx-dx)==2 && Math.abs(sy-dy)==1)||((Math.abs(sx-dx)==1 && Math.abs(sy-dy)==2))&& isVilagosFigura(dx,dy);

            helyesSotetLepesE = kezdoLepes || lepes || utes;
        }
        return helyesVilagosLepesE || helyesSotetLepesE;
    }

    public boolean isErvenyesFutoLepes(int sx, int sy, int dx, int dy) {
        boolean helyesVilagosLepesE = false;
        boolean helyesSotetLepesE = false;

        if(isVilagosFigura(sx,sy)){
            boolean kezdoLepes = ((sx == 5 && sy==0 )||(sx==2 && sy==0) &&(Math.abs(sx-dx)==Math.abs(sy-dy)));
            boolean lepes = (Math.abs(sx-dx)==Math.abs(sy-dy)) && isUresHely(dx,dy);
            boolean utes =  (Math.abs(sx-dx)==Math.abs(sy-dy))&& isSotetFigura(dx,dy);

            helyesVilagosLepesE = kezdoLepes || lepes || utes;
        }
        else if(isSotetFigura(sx,sy)){
            boolean kezdoLepes = ((sx == 5 && sy==7 )||(sx==2 && sy==7) &&(Math.abs(sx-dx)==Math.abs(sy-dy)));
            boolean lepes = (Math.abs(sx-dx)==Math.abs(sy-dy)) && isUresHely(dx,dy);
            boolean utes =  (Math.abs(sx-dx)==Math.abs(sy-dy))&& isVilagosFigura(dx,dy);

            helyesSotetLepesE = kezdoLepes || lepes || utes;
        }
        return helyesVilagosLepesE || helyesSotetLepesE;
    }

    public boolean isErvenyesVezerLepes(int sx, int sy, int dx, int dy) {
        boolean helyesVilagosLepesE = false;
        boolean helyesSotetLepesE = false;

        if(isVilagosFigura(sx,sy)){
            boolean kezdoLepes = ((sx == 4 && sy==0 ) &&(Math.abs(sx-dx)==Math.abs(sy-dy)||(sx == dx || sy == dy)));
            boolean lepes = (Math.abs(sx-dx)==Math.abs(sy-dy)||(sx == dx || sy == dy)) && isUresHely(dx,dy);
            boolean utes =  (Math.abs(sx-dx)==Math.abs(sy-dy)||(sx == dx || sy == dy))&& isSotetFigura(dx,dy);

            helyesVilagosLepesE = kezdoLepes || lepes || utes;
        }
        else if(isSotetFigura(sx,sy)){
            boolean kezdoLepes = ((sx == 4 && sy==7 ) &&(Math.abs(sx-dx)==Math.abs(sy-dy)||(sx == dx || sy == dy)));
            boolean lepes = (Math.abs(sx-dx)==Math.abs(sy-dy)||(sx == dx || sy == dy)) && isUresHely(dx,dy);
            boolean utes =  (Math.abs(sx-dx)==Math.abs(sy-dy)||(sx == dx || sy == dy))&& isVilagosFigura(dx,dy);

            helyesSotetLepesE = kezdoLepes || lepes || utes;
        }
        return helyesVilagosLepesE || helyesSotetLepesE;
    }

    public boolean isErvenyesKiralyLepes(int sx, int sy, int dx, int dy) {
        boolean helyesVilagosLepesE = false;
        boolean helyesSotetLepesE = false;

        if(isVilagosFigura(sx,sy)){
            boolean kezdoLepes = ((sx == 3 && sy==0 ) &&(Math.abs(sx-dx)==1 || Math.abs(sy-dy)==1));
            boolean lepes = (Math.abs(sx-dx)==1 || Math.abs(sy-dy)==1) && isUresHely(dx,dy);
            boolean utes =  (Math.abs(sx-dx)==1 || Math.abs(sy-dy)==1)&& isSotetFigura(dx,dy);

            helyesVilagosLepesE = kezdoLepes || lepes || utes;
        }
        else if(isSotetFigura(sx,sy)){
            boolean kezdoLepes = ((sx == 3 && sy==7 ) &&(Math.abs(sx-dx)==1 || Math.abs(sy-dy)==1));
            boolean lepes = (Math.abs(sx-dx)==1 || Math.abs(sy-dy)==1) && isUresHely(dx,dy);
            boolean utes =  (Math.abs(sx-dx)==1 || Math.abs(sy-dy)==1)&& isVilagosFigura(dx,dy);

            helyesSotetLepesE = kezdoLepes || lepes || utes;
        }
        return helyesVilagosLepesE || helyesSotetLepesE;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                s += String.format("%2d ", this.tabla[i][j]);
            }
            s += "\n";
        }
        return s;
    }
}
