package com.example.sam10795.beywiki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SAM10795 on 06-03-2015.
 */
public class CategoryAdapter extends ArrayAdapter {
    private final Context mContext;
    private final String[] mCategories;

    public CategoryAdapter(Context context, String[] arrayList)
    {
        super(context,R.layout.list_item_cat,arrayList);
        mContext = context;
        mCategories = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        String text = mCategories[position];
        CategHolder categHolder;
        if(view==null)
        {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_cat,parent,false);
            categHolder = new CategHolder(view);
            view.setTag(categHolder);
        }
        else
        {
            categHolder = (CategHolder)view.getTag();
        }

        categHolder.categview.setText(text);
        return view;
    }
}

class CategHolder
{
    public TextView categview;

    public CategHolder(View view)
    {
        categview = (TextView)view.findViewById(R.id.cat_text);
    }
}
