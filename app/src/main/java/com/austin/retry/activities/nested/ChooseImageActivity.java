
package com.austin.retry.activities.nested;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.austin.retry.adapters.ImageCursorAdapter;
import com.austin.retry.R;
import com.austin.retry.WallpaperDBHelper;


public class ChooseImageActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_list_view);

        mListView= (ListView)findViewById(R.id.image_list_view);
        CursorAdapter mCursorAdapter;
        adapterAsync async = new adapterAsync(mListView,this);
        async.execute();
        try{
            mCursorAdapter = async.get();
            System.out.println(mCursorAdapter);
        }catch(Exception e){
            System.out.println("oopsie");
            mCursorAdapter = null;
        }

        mListView.setAdapter(mCursorAdapter);

    }



    static class adapterAsync extends AsyncTask<Void, Void, CursorAdapter>{

        private ListView mListView;
        private Context mContext;

        public adapterAsync(ListView l, Context c){
            mContext = c;
            mListView = l;
        }

        @Override
        protected CursorAdapter doInBackground(Void... voids) {

            WallpaperDBHelper mHelper = new WallpaperDBHelper(mContext);
            Cursor mCursor = mHelper.getImages();
            mCursor.moveToFirst();
            System.out.println(mCursor.getString(0));
            CursorAdapter mCursorAdapter = new ImageCursorAdapter(mContext, mCursor, 0);

            return mCursorAdapter;
        }
    }

}

