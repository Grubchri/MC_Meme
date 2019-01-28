package at.projekt.mc.grubchri.silkix.meme_projekt;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    private MemeModel mm = new MemeModel();
    private MemeService ms = new MemeService();
    private DownloadTask dt = new DownloadTask();


    private class DownloadTask extends AsyncTask<Void, Integer, List<Meme>>
    {

        @Override
        protected List<Meme> doInBackground(Void... voids) {

            try {
                return ms.load();
            } catch (Exception e) {
                Log.e(TAG, "OJE!", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Meme> memes) {
            super.onPostExecute(memes);
            mm.setMemes(memes);
            MemesFill();
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load();
    }

    private void load()
    {
        dt.execute();
    }

    private void  MemesFill(){
        List<String> list = new ArrayList<String>();

        for(Meme m : mm.getMemes())
        {
            if(m.getTitle()!=null){
                list.add(m.getTitle());
            }

        }

        ListAdapter itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        ListView listView = (ListView) findViewById(R.id.ListView);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                Meme meme =mm.getMemes().get(position);

                CreateIntent(meme);

            }
        });

    }

    private void CreateIntent(Meme meme) {

        Intent intent = new Intent(this,MemeActivity.class);
        intent.putExtra("meme",meme);
        startActivity(intent);
    }

}
