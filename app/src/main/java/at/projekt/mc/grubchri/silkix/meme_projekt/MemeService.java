package at.projekt.mc.grubchri.silkix.meme_projekt;

import android.util.JsonReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MemeService {

    private static final String TAG = MemeService.class.getSimpleName();

    public List<Meme> load() throws Exception {
        URL url = new URL("https://api.reddit.com/r/dankmemes.json?sort=hot");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        List<Meme> memes = new ArrayList<>();

        try (InputStream in = new BufferedInputStream(urlConnection.getInputStream())) {

            JsonReader jsonr = new JsonReader(new InputStreamReader(in));

            jsonr.beginArray();

            while (jsonr.hasNext())
            {
                memes.add(readMeme(jsonr));
            }
            jsonr.endArray();

        } finally {
            urlConnection.disconnect();
        }

        return  memes;
    }

    private Meme readMeme(JsonReader reader) throws IOException {

        Meme meme = new Meme();

        reader.beginObject();

        while (reader.hasNext())
        {
            String name = reader.nextName();

            if(name.equals("id"))
            {
                meme.setID(reader.nextInt());
            }
            else if(name.equals("title"))
            {
                meme.setTitle(reader.nextString());
            }
            else if(name.equals("selftext"))
            {
                meme.setText(reader.nextString());
            }
            else if(name.equals("author_fullname"))
            {
                meme.setAuthor(reader.nextString());
            }
            else if(name.equals("url"))
            {
                meme.setUrl(reader.nextString());
            }
            else if(name.equals("over_18"))
            {
                meme.setNsfw(reader.nextBoolean());
            }
            else
            {
                reader.skipValue();
            }

        }

        reader.endObject();

        return meme;

    }


}
