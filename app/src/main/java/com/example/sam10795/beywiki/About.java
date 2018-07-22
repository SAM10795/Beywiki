package com.example.sam10795.beywiki;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class About extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        AdView adView = (AdView)findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        ConnectivityManager cmg = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if((cmg.getNetworkInfo(0).getState()== NetworkInfo.State.CONNECTED)||(cmg.getNetworkInfo(1).getState()== NetworkInfo.State.CONNECTED))
        {
            adView.setVisibility(View.VISIBLE);
        }
        else
        {
            adView.setVisibility(View.GONE);
        }
        TextView at1 = (TextView)findViewById(R.id.about_text);
        TextView at2 = (TextView)findViewById(R.id.about_text2);
        TextView at3 = (TextView)findViewById(R.id.about_text3);
        TextView at4 = (TextView)findViewById(R.id.about_text4);
        TextView am = (TextView)findViewById(R.id.about_mail);
        at1.setText("Content taken from");
        at2.setText("wiki.worldbeyblade.org");
        at3.setText("For queries/feedback");
        at4.setText("Thanks to Janstarblast for help with testing");
        am.setText("Contact: sam10795@yahoo.in");
        at2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW,Uri.parse("http://wiki.worldbeyblade.org"));
                startActivity(browse);
            }
        });
        am.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","sam10795@yahoo.in",null));
                mail.putExtra(Intent.EXTRA_SUBJECT,"Beywiki app");
                startActivity(mail);
            }
        });
    }

}
