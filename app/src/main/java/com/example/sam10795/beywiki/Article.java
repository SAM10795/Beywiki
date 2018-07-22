package com.example.sam10795.beywiki;

import java.util.Comparator;

/**
 * Created by SAM10795 on 06-03-2015.
 */
public class Article {
    public String ARTICLE_TITLE;
    public String ARTICLE_URL;
    public String ARTICLE_TEXT;
    public int ARTICLE_TERM_COUNT;

    public void setARTICLE_TITLE(String ARTICLE_TITLE) {
        this.ARTICLE_TITLE = ARTICLE_TITLE;
    }

    public void setARTICLE_URL(String ARTICLE_URL) {
        this.ARTICLE_URL = ARTICLE_URL;
    }

    public void setARTICLE_TEXT(String ARTICLE_TEXT) {
        this.ARTICLE_TEXT = ARTICLE_TEXT;
    }

    public void setARTICLE_TERM_COUNT(int ARTICLE_TERM_COUNT) {
        this.ARTICLE_TERM_COUNT = ARTICLE_TERM_COUNT;
    }

    public static Comparator<Article> TITLE = new Comparator<Article>() {
        @Override
        public int compare(Article lhs, Article rhs) {
            return lhs.ARTICLE_TITLE.compareToIgnoreCase(rhs.ARTICLE_TITLE);
        }
    };

    public static Comparator<Article> TERMCOUNT = new Comparator<Article>() {
        @Override
        public int compare(Article lhs, Article rhs) {
            return (rhs.ARTICLE_TERM_COUNT-lhs.ARTICLE_TERM_COUNT);
        }
    };
}
