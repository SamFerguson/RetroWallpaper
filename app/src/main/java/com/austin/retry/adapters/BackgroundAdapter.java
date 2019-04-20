package com.austin.retry.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.austin.retry.R;
import com.austin.retry.activities.BackgroundActivity;
import com.austin.retry.activities.nested.SettingsActivity;
import com.austin.retry.wrappers.RecyclerWrapper;
import com.austin.retry.WallpaperDBHelper;

import java.util.ArrayList;


public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.MyViewHolder> {

    private ArrayList<RecyclerWrapper> wrappers = new ArrayList<>();
    private int objId = -1;
    private String[] previousSEttings;

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
    public BackgroundAdapter(ArrayList<RecyclerWrapper> bms, int obId, String[] previousSEt) {
        this.wrappers = bms;
        //if you're coming from the settings then there'll be a different click listener
        this.objId = obId;
        previousSEttings = previousSEt;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final RecyclerWrapper wrapper = wrappers.get(position);
        final Bitmap b = wrapper.getBitmap();
        final String filename = wrapper.getFileName();
        System.out.println(objId+ "     " + wrapper.getId());
        holder.imageView.setImageBitmap(b);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(objId == -1) {
                    System.out.println("you clicked: " + b.toString() + "      " + filename);
                    SetCheckedAsyncTask setChecked = new SetCheckedAsyncTask(wrapper);
                    setChecked.execute();
                }
                else{
                    System.out.println("you're from the settings");
                    WallpaperDBHelper db = new WallpaperDBHelper(wrapper.getContext());
                    //make a new db thing and put the wallpaperid in the object that they clicked on
                    db.updateImage(wrapper.getId(), objId);
                    Intent backToSettings = new Intent(holder.imageView.getContext(), SettingsActivity.class);
                    backToSettings.putExtra("currentbitmapfile", wrapper.getFileName());
                    backToSettings.putExtra("currentSettings", previousSEttings);
                    backToSettings.putExtra("objectID", Integer.toString(objId));
                    //go back to the settings
                    holder.imageView.getContext().startActivity(backToSettings);
                }
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
