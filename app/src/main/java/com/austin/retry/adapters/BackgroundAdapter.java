package com.austin.retry.adapters;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.austin.retry.R;
import com.austin.retry.wrappers.RecyclerWrapper;
import com.austin.retry.WallpaperDBHelper;

import java.util.ArrayList;


public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.MyViewHolder> {

    private ArrayList<RecyclerWrapper> wrappers = new ArrayList<>();


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
    public BackgroundAdapter(ArrayList<RecyclerWrapper> bms) {
        this.wrappers = bms;
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
        final RecyclerWrapper wrapper = wrappers.get(position);
        final Bitmap b = wrapper.getBitmap();
        final String filename = wrapper.getFileName();

        holder.imageView.setImageBitmap(b);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("you clicked: " + b.toString() + "      " + filename);
                SetCheckedAsyncTask setChecked = new SetCheckedAsyncTask(wrapper);
                setChecked.execute();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return wrappers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;

        public ViewHolder(View item){
            super(item);
            imageView = (ImageView) item.findViewById(R.id.img_view);

        }
    }


    static class SetCheckedAsyncTask extends AsyncTask<Void, Void, Void>{

        private RecyclerWrapper rw;

        public SetCheckedAsyncTask(RecyclerWrapper r){
            rw = r;
        }
        @Override
        protected Void doInBackground(Void ... voids) {

            WallpaperDBHelper db = new WallpaperDBHelper(rw.getContext());
            db.setSelected(rw.getFileName());

            return null;
        }
    }


}
