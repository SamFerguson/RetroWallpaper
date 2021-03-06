package com.austin.retry.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.austin.retry.R;
import com.austin.retry.activities.ObjectActivity;
import com.austin.retry.wrappers.RecyclerWrapper;
import com.austin.retry.WallpaperDBHelper;
import com.austin.retry.activities.nested.SettingsActivity;

import java.util.ArrayList;

public class ObjectAdapter extends RecyclerView.Adapter<ObjectAdapter.MyViewHolder> {


    private ArrayList<RecyclerWrapper> wrappers = new ArrayList<>();

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ConstraintLayout constraintLayout;
        MyViewHolder(ConstraintLayout v) {
            super(v);
            constraintLayout = v;
        }
    }

    public ObjectAdapter(ArrayList<RecyclerWrapper> bms) {
        this.wrappers = bms;
    }
    public @NonNull
    ObjectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.obj_view, parent, false);
        return new ObjectAdapter.MyViewHolder(v);
    }


    public void onBindViewHolder(@NonNull ObjectAdapter.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final RecyclerWrapper wrapper = wrappers.get(position);
        final Bitmap b = wrapper.getBitmap();
        final String filename = wrapper.getFileName();
        final String name = wrapper.getObjectName();
        String mSettings = wrapper.getSettings();
        final String[] settingsArray = mSettings.split(",");
        final String size = settingsArray[0];
        final String angle = settingsArray[1];
        final String speed = settingsArray[2];
        final int objectID = wrapper.getId();
        final int isChecked = wrapper.isChosen();

        ConstraintLayout temp = holder.constraintLayout;
        ImageView iv = (ImageView) temp.getViewById(R.id.objImgPreview);
        TextView textViewSize = (TextView) temp.getViewById(R.id.objSize);
        TextView textViewAngle = (TextView) temp.getViewById(R.id.objAngle);
        TextView textViewSpeed = (TextView) temp.getViewById(R.id.objSpeed);
        final CheckBox isSelected = (CheckBox) temp.getViewById(R.id.isSelected);

        //if the boolean value is 1 set it to true
        System.out.println(isChecked);
        isSelected.setChecked(isChecked == 1);
        iv.setImageBitmap(b);
        textViewAngle.setText(angle);
        textViewSize.setText(size);
        textViewSpeed.setText(speed);

        isSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                WallpaperDBHelper db = new WallpaperDBHelper(wrapper.getContext());
                db.doSQL("UPDATE OBJECT SET IS_CHOSEN = " + (isChecked? 1:0)+ " WHERE OBJECT_ID = " + objectID);
                System.out.println("yay i did it");

            }
        });

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("you clicked: " + b.toString() + "      " + filename);
                //get the id of the one at that position
                final String itemID = Integer.toString(objectID);
                Intent i = new Intent(v.getContext(), SettingsActivity.class);
                i.putExtra("objectID", itemID);
                i.putExtra("currentbitmapfile", filename);
                i.putExtra("currentSettings", settingsArray);
                v.getContext().startActivity(i);

            }
        });

    }


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

    static class SetCheckedAsyncTask extends AsyncTask<Void, Void, Void> {

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
