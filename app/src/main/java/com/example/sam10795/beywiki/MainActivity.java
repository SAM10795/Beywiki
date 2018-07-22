package com.example.sam10795.beywiki;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private String[] CATEGORIES;
    private GridView gridView;
    private FrameLayout frameLayout;
    public int SORT_TITLE = 0;
    public int SORT_PID = 1;
    public int SORT_PSYS = 2;
    public int SORT_PTYPE = 3;
    public int SORT_CODE = 0;
    MenuItem sort;
    MenuItem about;
    MenuItem sorttitle;
    MenuItem sortpid;
    MenuItem sortsys;
    MenuItem sorttype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_alt);

        CATEGORIES = getResources().getStringArray(R.array.Categories);
        gridView = (GridView) findViewById(R.id.gridView);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, CATEGORIES);
        gridView.setAdapter(categoryAdapter);
        gridView.setOnItemClickListener(new DrawerItemClickListener());
        ImageView imageView = (ImageView)findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ArticleView.class)
                        .putExtra("Title","Beyblade Burst")
                        .putExtra("URL","Beyblade_Burst.html");
                startActivity(intent);
            }
        });

        //if (savedInstanceState == null) {
        //    getSupportFragmentManager().beginTransaction()
        //            .add(R.id.content_frame, new ArticeListFragment())
        //            .commit();
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchme).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, Search.class)));
        searchView.setIconifiedByDefault(false);
        sort = menu.findItem(R.id.sort);
        sort.setVisible(false);
        sortpid = menu.findItem(R.id.sort_pid);
        sorttitle = menu.findItem(R.id.sort_title);
        sortsys = menu.findItem(R.id.sort_psys);
        sorttype = menu.findItem(R.id.sort_ptype);
        about = menu.findItem(R.id.action_settings);
        sorttitle.setIcon(R.drawable.abc_btn_check_to_on_mtrl_015);
        //getMenuInflater().inflate(R.menu.options_menu, menu);
        //
        //
        //
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int pos = 0;
        for(String cat:CATEGORIES)
        {
            Fragment f = getSupportFragmentManager().findFragmentByTag(cat);
            Log.v("FTAG",cat);
            if(f!=null) {
                boolean b = f.isVisible();
                if (b) {
                    break;
                }
            }
            pos++;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent intent = new Intent(this,About.class);
            startActivity(intent);
        }
        if(id == R.id.sort_title)
        {
            getSupportFragmentManager().popBackStack();
            SORT_CODE = SORT_TITLE;
            SharedPreferences sp = getSharedPreferences("PREFS",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("SORT_CODE",SORT_TITLE);
            editor.apply();
            selectItem(pos);
            item.setIcon(R.drawable.abc_btn_check_to_on_mtrl_015);
            sorttype.setIcon(null);
            sortpid.setIcon(null);
            sortsys.setIcon(null);
        }
        if(id == R.id.sort_pid)
        {
            getSupportFragmentManager().popBackStack();
            SORT_CODE = SORT_PID;
            SharedPreferences sp = getSharedPreferences("PREFS",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("SORT_CODE",SORT_PID);
            editor.apply();
            selectItem(pos);
            item.setIcon(R.drawable.abc_btn_check_to_on_mtrl_015);
            sorttype.setIcon(null);
            sorttitle.setIcon(null);
            sortsys.setIcon(null);
        }
        if(id == R.id.sort_psys)
        {
            getSupportFragmentManager().popBackStack();
            SORT_CODE = SORT_PSYS;
            SharedPreferences sp = getSharedPreferences("PREFS",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("SORT_CODE",SORT_PSYS);
            editor.apply();
            selectItem(pos);
            item.setIcon(R.drawable.abc_btn_check_to_on_mtrl_015);
            sorttype.setIcon(null);
            sortpid.setIcon(null);
            sorttitle.setIcon(null);
        }
        if(id == R.id.sort_ptype)
        {
            getSupportFragmentManager().popBackStack();
            SORT_CODE = SORT_PTYPE;
            SharedPreferences sp = getSharedPreferences("PREFS",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("SORT_CODE",SORT_PTYPE);
            editor.apply();
            selectItem(pos);
            item.setIcon(R.drawable.abc_btn_check_to_on_mtrl_015);
            sorttitle.setIcon(null);
            sortpid.setIcon(null);
            sortsys.setIcon(null);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setTitle("Beywiki");
        if(getSupportFragmentManager().getBackStackEntryCount()==0)
        {
            super.onBackPressed();
        }
        else
        {
            getSupportFragmentManager().popBackStack();
            sort.setVisible(false);
        }
    }

    private class DrawerItemClickListener implements GridView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(final int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new ArticeListFragment();
        Bundle args = new Bundle();
        args.putString("CATEGORY", CATEGORIES[position]);
        SharedPreferences sp = getSharedPreferences("PREFS",MODE_PRIVATE);
        SORT_CODE = sp.getInt("SORT_CODE",SORT_TITLE);
        args.putInt("SORTCODE", SORT_CODE);
        fragment.setArguments(args);

        Log.i("SCD", Integer.toString(SORT_CODE));

        setTitle(CATEGORIES[position]);
        sort.setVisible(true);
        about.setVisible(false);


        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment, CATEGORIES[position])
                .addToBackStack(null)
                .commit();
        Log.i("TAG",fragment.getTag());
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(CATEGORIES[position]);
        // Highlight the selected item, update the title, and close the drawer
        //gridView.setItemChecked(position, true);
        //setTitle(CATEGORIES[position]);
        //mDrawerLayout.closeDrawer(gridView);
    }
}


