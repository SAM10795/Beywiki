package com.example.sam10795.beywiki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SAM10795 on 05-03-2015.
 */
public class ArticleAdapter extends ArrayAdapter {
    private final Context mContext;
    private final ArrayList<Article> mArticles;

    public ArticleAdapter(Context context, ArrayList<Article> articles)
    {
        super(context,R.layout.list_item_article,articles);
        mContext = context;
        mArticles = articles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = (Article)getItem(position);
        ArticleHolder arholder;
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_article,parent,false);
            arholder = new ArticleHolder(convertView);
            convertView.setTag(arholder);
        }
        else
        {
            arholder = (ArticleHolder)convertView.getTag();
        }
        arholder.subtextview.setText(article.ARTICLE_TEXT);
        arholder.titleview.setText(article.ARTICLE_TITLE);
        if(article.ARTICLE_TEXT.equals(" "))
        {
            arholder.subtextview.setVisibility(View.GONE);
        }

        return convertView;
    }
}
class ArticleHolder{

    public TextView titleview;
    public TextView subtextview;

    public ArticleHolder(View view) {
        titleview = (TextView)view.findViewById(R.id.article_title);
        subtextview = (TextView)view.findViewById(R.id.article_text);
    }
}
