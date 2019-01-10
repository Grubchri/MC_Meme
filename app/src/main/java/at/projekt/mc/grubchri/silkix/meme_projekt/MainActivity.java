package at.projekt.mc.grubchri.silkix.meme_projekt;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
       //String[] meme = new String[mm.getMemes().size()];
         final String TAG = MainActivity.class.getSimpleName();
        ImageView memeView;
        memeView = (ImageView) findViewById(R.id.MemeView);
        for(int i=0;i<mm.getMemes().size();i++){
            try{
                InputStream url = new ByteArrayInputStream(mm.getMemes().get(i).getUrl().getBytes());
                Drawable image = Drawable.createFromStream(url,"string");
                memeView.setImageDrawable(image);
            }catch(Exception e){
                Log.d(TAG, "Oh je!", e);
            }
        }

    }

}
