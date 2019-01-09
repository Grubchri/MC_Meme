package at.projekt.mc.grubchri.silkix.meme_projekt;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
}
