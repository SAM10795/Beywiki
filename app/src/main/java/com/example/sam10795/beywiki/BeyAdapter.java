package com.example.sam10795.beywiki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SAM10795 on 05-03-2015.
 */
public class BeyAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<Beyblade> values;

    public BeyAdapter(Context context,ArrayList<Beyblade> values)
    {
        super(context,R.layout.list_item_beyblade,values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View beyview = convertView;
        final Beyblade beyitem = (Beyblade)getItem(position);
        ViewHolder beholder;
        if(beyview == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            beyview = inflater.inflate(R.layout.list_item_beyblade, parent, false);
            beholder = new ViewHolder(beyview);
            beyview.setTag(beholder);
        }
        else
        {
            beholder = (ViewHolder)beyview.getTag();
        }
        beholder.iview.setImageBitmap(beyitem.PRODUCT_IMAGE);
        beholder.ptypeview.setText(beyitem.PRODUCT_TYPE);
        beholder.psysview.setText(beyitem.PRODUCT_SYSTEM);
        beholder.pidview.setText(beyitem.PRODUCT_ID);
        beholder.pnameview.setText(beyitem.PRODUCT_ARTICLE.ARTICLE_TITLE);

        if(beyitem.PRODUCT_TYPE.length()<4)
        {
            beholder.ptypeview.setVisibility(View.GONE);
        }

        return beyview;

    }
}

class ViewHolder{
    public ImageView iview;
    public TextView pidview;
    public TextView pnameview;
    public TextView psysview;
    public TextView ptypeview;

    public ViewHolder(View view){
        pidview = (TextView) view.findViewById(R.id.bey_id);
        pnameview = (TextView)view.findViewById(R.id.bey_name);
        psysview = (TextView) view.findViewById(R.id.bey_system);
        ptypeview = (TextView) view.findViewById(R.id.bey_type);
        iview = (ImageView) view.findViewById(R.id.bey_image);
    }
}
