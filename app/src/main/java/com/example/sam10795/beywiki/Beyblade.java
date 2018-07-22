package com.example.sam10795.beywiki;

import android.graphics.Bitmap;

import java.util.Comparator;

/**
 * Created by SAM10795 on 05-03-2015.
 */
public class Beyblade
{
    public String PRODUCT_ID;
    public String PRODUCT_SYSTEM;
    public String PRODUCT_TYPE;
    public Bitmap PRODUCT_IMAGE;
    public Article PRODUCT_ARTICLE;

    public void setpid(String pid)
    {
        PRODUCT_ID = pid;
    }

    public void setpsys(String psys)
    {
        PRODUCT_SYSTEM = psys;
    }

    public void setptype(String ptype)
    {
        PRODUCT_TYPE = ptype;
    }

    public void setpimg(Bitmap pimg)
    {
        PRODUCT_IMAGE = pimg;
    }

    public void setPRODUCT_ARTICLE(Article PRODUCT_ARTICLE) {
        this.PRODUCT_ARTICLE = PRODUCT_ARTICLE;
    }

    public static Comparator<Beyblade> PID = new Comparator<Beyblade>() {
        @Override
        public int compare(Beyblade lhs, Beyblade rhs) {
            String pidlhs = lhs.PRODUCT_ID;
            String pidrhs = rhs.PRODUCT_ID;
            if(Utility.getmainID(pidlhs).equalsIgnoreCase(Utility.getmainID(pidrhs)))
            {
                return (Utility.getnumID(pidlhs)-Utility.getnumID(pidrhs));
            }
            else
            {
                return Utility.getmainID(pidlhs).compareToIgnoreCase(Utility.getmainID(pidrhs));
            }
        }
    };

    public static Comparator<Beyblade> PSYS = new Comparator<Beyblade>() {
        @Override
        public int compare(Beyblade lhs, Beyblade rhs) {
            String syslhs = lhs.PRODUCT_SYSTEM;
            String sysrhs = rhs.PRODUCT_SYSTEM;
            return syslhs.compareToIgnoreCase(sysrhs);
        }
    };

    public static Comparator<Beyblade> PTYPE = new Comparator<Beyblade>() {
        @Override
        public int compare(Beyblade lhs, Beyblade rhs) {
            String typelhs = lhs.PRODUCT_TYPE;
            String typerhs = rhs.PRODUCT_TYPE;
            return typelhs.compareToIgnoreCase(typerhs);
        }
    };

    public static Comparator<Beyblade> PTITLE = new Comparator<Beyblade>() {
        @Override
        public int compare(Beyblade lhs, Beyblade rhs) {
            String lhstitle = lhs.PRODUCT_ARTICLE.ARTICLE_TITLE;
            String rhstitle = rhs.PRODUCT_ARTICLE.ARTICLE_TITLE;
            return lhstitle.compareToIgnoreCase(rhstitle);
        }
    };

}
