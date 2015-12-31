package com.raise.domain;

import java.util.List;

/**
 * Created by zhoumingche on 2015/10/24.
 */
public class UserwordBean {

    /**
     * bg : 0
     * ed : 0
     * ls : false
     * sn : 1
     * ws : [{"bg":0,"cw":[{"sc":0,"w":"去"}]},{"bg":0,"cw":[{"sc":0,"w":"智能"}]},{"bg":0,"cw":[{"sc":0,"w":"场馆"}]}]
     */

    private int bg;
    private int ed;
    private boolean ls;
    private int sn;
    private List<Ws> ws;

    public void setBg(int bg) {
        this.bg = bg;
    }

    public void setEd(int ed) {
        this.ed = ed;
    }

    public void setLs(boolean ls) {
        this.ls = ls;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public void setWs(List<Ws> ws) {
        this.ws = ws;
    }

    public int getBg() {
        return bg;
    }

    public int getEd() {
        return ed;
    }

    public boolean getLs() {
        return ls;
    }

    public int getSn() {
        return sn;
    }

    public List<Ws> getWs() {
        return ws;
    }

    public static class Ws {
        /**
         * bg : 0
         * cw : [{"sc":0,"w":"去"}]
         */

        private int bg;
        private List<Cw> cw;

        public void setBg(int bg) {
            this.bg = bg;
        }

        public void setCw(List<Cw> cw) {
            this.cw = cw;
        }

        public int getBg() {
            return bg;
        }

        public List<Cw> getCw() {
            return cw;
        }

        public static class Cw {
            /**
             * sc : 0.0
             * w : 去
             */

            private double sc;
            private String w;

            public void setSc(double sc) {
                this.sc = sc;
            }

            public void setW(String w) {
                this.w = w;
            }

            public double getSc() {
                return sc;
            }

            public String getW() {
                return w;
            }
        }
    }
}
