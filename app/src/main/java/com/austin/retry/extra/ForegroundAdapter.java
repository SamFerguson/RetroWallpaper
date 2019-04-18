package com.austin.retry.extra;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.austin.retry.ForegroundObject;
import com.austin.retry.R;
import com.austin.retry.wrappers.RecyclerWrapper;
import com.austin.retry.WallpaperDBHelper;

import java.util.ArrayList;

public class ForegroundAdapter extends RecyclerView.Adapter<ForegroundAdapter.MyViewHolder> {

    private ArrayList<ForegroundObject> objects = new ArrayList<>();

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
        ImageView imageView;
        TextView nameView;
        TextView desc1View;
        TextView desc2View;
        TextView desc3View;
        TextView desc4View;

        MyViewHolder(ConstraintLayout c) {
            super(c);
            constraintLayout = c;
            imageView = (ImageView) c.findViewById(R.id.objImgPreview);
            //nameView = (TextView) c.findViewById(R.id.objName);
            desc1View = (TextView) c.findViewById(R.id.objSize);
            desc2View = (TextView) c.findViewById(R.id.objSpeed);
            desc3View = (TextView) c.findViewById(R.id.objAngle);
            desc4View = (TextView) c.findViewById(R.id.objDesc4);
        }
    }

    ForegroundAdapter(ArrayList<ForegroundObject> reeraw) {
        this.objects = reeraw;
    }

    @Override
    public @NonNull ForegroundAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ConstraintLayout c = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.obj_view, parent, false);
        return new MyViewHolder(c);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ForegroundObject obj = objects.get(position);
        holder.imageView.setImageBitmap(obj.getImage());
        //holder.nameView.setText(obj.getName());
        holder.desc1View.setText(String.valueOf(obj.getSize()));
        holder.desc2View.setText(String.valueOf(obj.getSpeed()));
        holder.desc3View.setText(String.valueOf(obj.getAngle()));
        holder.desc4View.setText(obj.getShape());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("you clicked on some shit");
            }
        });

    }


    @Override
    public int getItemCount() {
        return objects.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout constraintLayout;

        public ViewHolder(View item){
            super(item);
            constraintLayout = (ConstraintLayout) item.findViewById(R.id.obj_view);

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

