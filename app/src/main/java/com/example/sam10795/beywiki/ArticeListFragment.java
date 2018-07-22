package com.example.sam10795.beywiki;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.example.sam10795.beywiki.Utility;

/**
 * Created by SAM10795 on 05-03-2015.
 */
public class ArticeListFragment extends android.support.v4.app.Fragment {

    public ArticeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String cat = getArguments().getString("CATEGORY");
        int sortcode = getArguments().getInt("SORTCODE");

        Log.i("Sortcode",Integer.toString(sortcode));
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.article_list);
        if(!((cat.contains("Beyblade"))||(cat.contains("Decent"))||(cat.contains("collection"))||(cat.contains("Zero-G"))||(cat.contains("Set")))) {
            ArrayList<Article> contents = getfilelist(cat);
            Log.i("DATA2", Integer.toString(contents.size()));
            ArticleAdapter articleAdapter = new ArticleAdapter(getActivity(), contents);
            listView.setAdapter(articleAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Article article = (Article) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(), ArticleView.class)
                            .putExtra("Title",article.ARTICLE_TITLE)
                            .putExtra("URL",article.ARTICLE_URL);
                    startActivity(intent);
                }
            });
        }
        else
        {
            ArrayList<Beyblade> beyblades = getBeylist(cat);
            if(sortcode == 1)
            {
                Collections.sort(beyblades,Beyblade.PID);
            }
            else if(sortcode == 2)
            {
                Collections.sort(beyblades,Beyblade.PSYS);
            }
            else if(sortcode == 3)
            {
                Collections.sort(beyblades,Beyblade.PTYPE);
            }
            else
            {
                Collections.sort(beyblades,Beyblade.PTITLE);
            }
            BeyAdapter beyAdapter = new BeyAdapter(getActivity(),beyblades);
            listView.setAdapter(beyAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Beyblade beyblade = (Beyblade)parent.getItemAtPosition(position);
                    Article article = beyblade.PRODUCT_ARTICLE;
                    Intent intent = new Intent(getActivity(), ArticleView.class)
                            .putExtra("Title",article.ARTICLE_TITLE)
                            .putExtra("URL",article.ARTICLE_URL);
                    startActivity(intent);
                }
            });
        }

        return rootView;
    }

    private ArrayList<Article> getfilelist(String foldername)
    {
        Log.i("DATA3",foldername);
        ArrayList<Article> articlelist = new ArrayList<Article>();
        AssetManager assetManager = getActivity().getAssets();
        try
        {
            InputStream is = assetManager.open(getfoldername(foldername)+".txt");
            Log.i("DATA4",getfoldername(foldername)+".txt");
            InputStreamReader fr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(fr);
            String text;
            while ((text = br.readLine())!=null)
            {
                Article article = new Article();
                article.setARTICLE_TITLE(Utility.getTitle(text));
                article.setARTICLE_URL(Utility.getURL(text));
                article.setARTICLE_TEXT(" ");
                articlelist.add(article);
            }
            is.close();
            fr.close();
            br.close();
        }
        catch(IOException io)
        {
            Log.e("Assets not found",getfoldername(foldername)+".txt");
        }
        return articlelist;
    }

    private ArrayList<Beyblade> getBeylist(String foldername)
    {
        ArrayList<Beyblade> beylist = new ArrayList<Beyblade>();
        AssetManager assetManager = getActivity().getAssets();
        try
        {
            InputStream is = assetManager.open(getfoldername(foldername)+".txt");
            Log.i("DATA4",getfoldername(foldername)+".txt");
            InputStreamReader fr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(fr);
            String text;
            while ((text = br.readLine())!=null)
            {
                Beyblade beyblade = new Beyblade();
                Article article = new Article();
                article.setARTICLE_TITLE(Utility.getTitle(text));
                article.setARTICLE_URL(Utility.getURL(text));
                article.setARTICLE_TEXT(" ");
                beyblade.setPRODUCT_ARTICLE(article);
                beyblade.setpimg(getBitmapfromAssets(article.ARTICLE_URL.replace(".html", "_00.jpg")));
                beyblade.setpsys(Utility.getSys(text));
                beyblade.setpid(Utility.getNum(text));
                beyblade.setptype(Utility.getType(text));
                beylist.add(beyblade);
            }
            is.close();
            fr.close();
            br.close();
        }
        catch(IOException io)
        {
            Log.e("Assets not found",getfoldername(foldername)+".txt");
        }
        return beylist;
    }

    private String getfoldername(String folder)
    {
        int c = 0;
        for(int i = 0;i<folder.length();i++)
        {
            if(Character.isLetterOrDigit(folder.charAt(i)))
            {
                c=i;
                break;
            }
        }
        return folder.substring(c);
    }

    private Bitmap getBitmapfromAssets(String str) {
        AssetManager assetManager = getActivity().getAssets();
        InputStream is = null;
        try {
            is = assetManager.open(str);
        } catch (IOException e) {
            try {
                is = assetManager.open("BB.png");
            } catch (IOException f) {
                Log.e("BMP", "Error in BMP");
            }
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }
}
