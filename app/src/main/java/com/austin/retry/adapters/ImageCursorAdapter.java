package com.austin.retry.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.austin.retry.R;

import org.w3c.dom.Text;

public class ImageCursorAdapter extends CursorAdapter {

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    public ImageCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        System.out.println("Hello world!");
        TextView mTextView = (TextView) view.findViewById(R.id.imagesID);
        System.out.println(mTextView);
        int id = cursor.getInt(0);
        System.out.println(id);
        String name = cursor.getString(1);
        System.out.println(name);
        mTextView.setText((CharSequence)name+ "  " + Integer.toString(id));

        ImageView mImageView = (ImageView) view.findViewById(R.id.imageimage);
        byte[] bits = cursor.getBlob(2);
        Bitmap b = BitmapFactory.decodeByteArray(bits,0, bits.length);
        mImageView.setImageBitmap(b);

    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.image_list_view,parent,false);

    }
}