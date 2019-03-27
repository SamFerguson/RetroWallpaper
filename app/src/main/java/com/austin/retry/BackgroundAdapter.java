package com.austin.retry;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.MyViewHolder> {
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView imageView;
        MyViewHolder(ImageView v) {
            super(v);
            imageView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    BackgroundAdapter(ArrayList<Bitmap> bms) {
        this.bitmaps = bms;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public @NonNull BackgroundAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                      int viewType) {
        // create a new view
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.img_view, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.imageView.setImageBitmap(bitmaps.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return bitmaps.size();
    }
}
