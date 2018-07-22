package com.example.sam10795.beywiki;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by SAM10795 on 10-03-2015.
 */
public class JavaScriptInterface{
    private Context mContext;
    public JavaScriptInterface(Context context)
    {
        mContext = context;
    }
    @JavascriptInterface public void imageclick(String src)
    {
        String url = "http://wiki.worldbeyblade.org"+src;
        String imgname = imagename(src);
        File imgfile = new File(Environment.getExternalStorageDirectory()
                + File.separator + imgname);
        if(!imgfile.exists())
        {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements images = doc.select(".internal");
                String im = images.get(0).attr("abs:href");
                Toast.makeText(mContext, "Please wait... Downloading Image", Toast.LENGTH_LONG).show();
                Bitmap img = download(im);
                Toast.makeText(mContext, "Image downloaded", Toast.LENGTH_SHORT).show();
                File f = save(img, imgname);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(f), "image/*");
                mContext.startActivity(intent);
            } catch (IOException e) {
                Toast.makeText(mContext, "Connection error", Toast.LENGTH_SHORT).show();
                Log.e("IOError", "Error in URL");
            }
        }
        else
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(imgfile), "image/*");
            mContext.startActivity(intent);
        }
    }

    @JavascriptInterface public void linkclick(String ref)
    {
        Intent articleintent = new Intent(mContext,ArticleView.class)
                .putExtra("URL",ref)
                .putExtra("Title",ref.replace("_"," ").replace(".html",""));
        mContext.startActivity(articleintent);
    }

    public static Bitmap download(String url)
    {
        Bitmap bmp = null;
        try {
            URL IM_URL = new URL(url);
            URLConnection connection = IM_URL.openConnection();
            InputStream is = connection.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;

    }

    private static File save(Bitmap bmp,String imagename)
    {
        File file;
        FileOutputStream fos = null;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    /*--- you can select your preferred CompressFormat and quality.
     * I'm going to use JPEG and 100% quality ---*/
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    /*--- create a new file on SD card ---*/
        file = new File(Environment.getExternalStorageDirectory()
                + File.separator + imagename);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    /*--- create a new FileOutputStream and write bytes to file ---*/
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bytes.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String imagename(String text)
    {
        int in;
        if((in = text.lastIndexOf(":"))!=-1)
        {
            if(text.endsWith(".jpg")) {
                return text.substring(in + 1);
            }
            else
            {
                return text.substring(in+1)+".jpg";
            }
        }
        else
        {
            return "BeywikiImage.jpg";
        }
    }
}
