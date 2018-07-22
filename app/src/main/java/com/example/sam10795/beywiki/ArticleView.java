package com.example.sam10795.beywiki;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ZoomControls;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import java.io.IOException;


public class ArticleView extends ActionBarActivity {

    public LinearLayout searchhelp;
    public ZoomControls zoomControls;
    AdView adView;
    ConnectivityManager cmg;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);
        adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        cmg = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if((cmg.getNetworkInfo(0).getState()== NetworkInfo.State.CONNECTED)||(cmg.getNetworkInfo(1).getState()== NetworkInfo.State.CONNECTED))
        {
            adView.setVisibility(View.VISIBLE);
            Log.i("AD","VISIBLE");
        }
        else
        {
            adView.setVisibility(View.GONE);
            Log.i("AD","GONE");
        }
        Intent intent = getIntent();
        if(intent!=null&&(intent.hasExtra("Title")&&(intent.hasExtra("URL")))) {
            String article_path = intent.getStringExtra("URL");
            webView = (WebView)findViewById(R.id.webView);
            try {
                getAssets().open(article_path);
                webView.loadUrl("file:///android_asset/" + article_path);
                WebSettings ws = webView.getSettings();
                ws.setJavaScriptEnabled(true);
                JavaScriptInterface javaScriptInterface = new JavaScriptInterface(this);
                webView.addJavascriptInterface(javaScriptInterface, "JSInterface");
                setTitle(intent.getStringExtra("Title"));
                searchhelp = (LinearLayout) findViewById(R.id.web_search);
                zoomControls = (ZoomControls) findViewById(R.id.zoomControls);
                Log.d("Path", article_path);
                zoomControls.setZoomSpeed(500);
                zoomControls.setIsZoomOutEnabled(false);
                zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean b = webView.zoomIn();
                        zoomControls.setIsZoomOutEnabled(true);
                        if (!b) {
                            zoomControls.setIsZoomInEnabled(false);
                        }
                    }
                });
                zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean b = webView.zoomOut();
                        zoomControls.setIsZoomInEnabled(true);
                        if (!b) {
                            zoomControls.setIsZoomOutEnabled(false);
                        }
                    }
                });
                final EditText searchterm = (EditText) findViewById(R.id.editText);
                searchterm.setSingleLine(true);
                searchterm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            String query = searchterm.getText().toString();
                            if (webView.findAll(query) < 1) {
                                //searchhelp.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "No match found", Toast.LENGTH_SHORT).show();
                                //searchhelp.setVisibility(View.VISIBLE);
                            }
                        }
                        return false;
                    }
                });
                ImageButton next = (ImageButton) findViewById(R.id.next);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        webView.findNext(true);
                    }
                });
                ImageButton close = (ImageButton) findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchhelp.setVisibility(View.INVISIBLE);
                        zoomControls.setVisibility(View.VISIBLE);
                        webView.clearMatches();
                    }
                });
            }
            catch(IOException e)
            {
                Toast.makeText(this,"Article does not exist",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else
        {
            Toast.makeText(this,"Article does not exist",Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.article_search)
        {
            zoomControls.setVisibility(View.INVISIBLE);
            searchhelp.setVisibility(View.VISIBLE);

        }

        return super.onOptionsItemSelected(item);
    }
}
