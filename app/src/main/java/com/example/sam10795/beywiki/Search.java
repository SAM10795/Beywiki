package com.example.sam10795.beywiki;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Search extends ActionBarActivity {

    private ProgressDialog progressDialog;
    public static final int progress_bar_type = 0;
    public ListView listView;
    public int SCODE = 0;
    SearchTask st;
    MenuItem sart;
    MenuItem srel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        SharedPreferences sp = getSharedPreferences("PREFS",MODE_PRIVATE);
        SCODE = sp.getInt("SCODE",0);
        listView = (ListView)findViewById(R.id.searchlist);
        Log.i("THIS", "There");
        Intent intent = new Intent(getIntent());
        if(intent!=null&&intent.getAction().equals(Intent.ACTION_SEARCH))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            st = new SearchTask(this,listView);
            if(query.length()<3)
            {
                Toast.makeText(this,"Search query too short",Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                setTitle("Results for : "+query);
                st.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,query);
            }
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id)
        {
            case progress_bar_type:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Searching... Please wait");
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();
                return progressDialog;
            default:
                return null;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        sart = menu.findItem(R.id.sort_art_title);
        srel = menu.findItem(R.id.sort_rel);
        srel.setIcon(R.drawable.abc_btn_check_to_on_mtrl_015);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sort_art_title)
        {
            SCODE = 1;
            item.setIcon(R.drawable.abc_btn_check_to_on_mtrl_015);
            SharedPreferences sp = getSharedPreferences("PREFS",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("SCODE",1);
            editor.apply();
            srel.setIcon(null);
            st.onPostExecute(null);
        }
        if (id == R.id.sort_rel)
        {
            SCODE = 0;
            SharedPreferences sp = getSharedPreferences("PREFS",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("SCODE",0);
            editor.apply();
            item.setIcon(R.drawable.abc_btn_check_to_on_mtrl_015);
            sart.setIcon(null);
            st.onPostExecute(null);
        }

        return super.onOptionsItemSelected(item);
    }

    private static String gettext(String text, int c)
    {
        int start,end;
        if(c<26)
        {
            start = c;
        }
        else
        {
            start = c-25;
        }
        if(c+24>text.length())
        {
            end = text.length();
        }
        else
        {
            end = c+25;
        }
        while((start!=0) && (Character.isLetterOrDigit(text.charAt(start))))
        {
            start--;
        }
        while((end<text.length()-2)&&(Character.isLetterOrDigit(text.charAt(end+1))))
        {
            end++;
        }
        return text.substring(start,end+1);
    }

    public class SearchTask extends AsyncTask<String,Integer,Void> {
        ArrayList<Article> artl = new ArrayList<>();
        Context mContext;
        ListView mlistView;

        public SearchTask(Context context,ListView listView)
        {
            mContext = context;
            mlistView = listView;
        }

        @Override
        protected void onPreExecute() {
            showDialog(progress_bar_type);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            String query = params[0];
            try
            {
                InputStream is = getAssets().open("TEXT.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String tex;
                int i = 0;
                while((tex = br.readLine())!=null) {
                    i++;
                    if (i < 100) {
                        publishProgress(i / 100);
                    } else {
                        publishProgress(99 / 100);
                    }
                        String text = new Scanner(getAssets().open(tex)).useDelimiter("\\A").next();
                        String text2 = text.toLowerCase();
                        int index;
                        if ((index = text2.indexOf(query.toLowerCase())) != -1)
                        {
                            int count = 0;
                            int l = 0;
                            int li = text2.lastIndexOf(query.toLowerCase());
                            while (l < li)
                            {
                                int in = text2.indexOf(query.toLowerCase(), l);
                                l = in + 1;
                                count++;
                            }
                            Article article = new Article();
                            article.setARTICLE_URL(tex.replace("_s.txt", ".html"));
                            article.setARTICLE_TITLE(text.substring(0, text.indexOf(" - ")));
                            article.setARTICLE_TEXT(gettext(text, index));
                            article.setARTICLE_TERM_COUNT(count);
                            artl.add(article);
                        }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dismissDialog(0);
            if (!artl.isEmpty()) {
                if(SCODE==1)
                {
                    Collections.sort(artl, Article.TITLE);
                }
                else
                {
                    Collections.sort(artl, Article.TERMCOUNT);
                }
                ArticleAdapter articleAdapter = new ArticleAdapter(mContext, artl);
                listView.setAdapter(articleAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Article article = (Article) parent.getItemAtPosition(position);
                        Intent intent = new Intent(getApplicationContext(), ArticleView.class)
                                .putExtra("URL",article.ARTICLE_URL)
                                .putExtra("Title",article.ARTICLE_TITLE);
                        startActivity(intent);
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "No match found", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


}


