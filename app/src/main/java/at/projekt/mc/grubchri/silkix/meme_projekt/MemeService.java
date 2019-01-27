package at.projekt.mc.grubchri.silkix.meme_projekt;

import android.util.JsonReader;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

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
        List<Meme> memes = new ArrayList<Meme>();

        try (InputStream in = new BufferedInputStream(urlConnection.getInputStream())) {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode node = objectMapper.readTree(in).get("data").get("children");

            Meme m = new Meme();
            for (JsonNode fin : node){
                fin=fin.get("data");
                System.out.println(fin.get("author_fullname").asText());
                m.setAuthor(fin.get("author_fullname").asText());
            }

            /*JsonNode node = objectMapper.readValue(in,JsonNode.class);
            JSONArray jsonArray = new JSONArray(JsonNode);
            //Meme meme = node.get()
            /*Meme meme = objectMapper.readValue(json,Meme.class);
            meme.setAuthor(String.valueOf(objectMapper.readValue(json,Meme.class)));
            //JsonReader jsonr = new JsonReader(new InputStreamReader(in, "UTF-8"));
            //Log.d(( MemeService.class.getSimpleName()),jsonr.toString());
            //jsonr.beginObject();

            /*while (jsonr.hasNext())
            {
                memes.add(readMeme(jsonr));
            }*/
            //jsonr.endArray();

            return  memes;
        } finally {
            urlConnection.disconnect();
        }


    }

    private Meme readMeme(JsonReader reader) throws IOException {
        //reader.beginArray();
        Meme meme = new Meme();

        while (reader.hasNext())
        {
            String name = reader.nextName();

            if(name.equals("data")){
                //meme=ReadData(reader,meme);

            }else{
                reader.skipValue();
            }

        }

        reader.endObject();

        return meme;

    }

    private Meme ReadData(JsonReader reader, Meme meme) throws  IOException {
        reader.beginObject();
        String name;
        while(reader.hasNext()){

            name=reader.nextName();

            if(name.equals("children")){
                meme=ReadChildren(reader,meme);
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return meme;
    }

    private Meme ReadChildren(JsonReader reader,Meme meme) throws IOException {

        reader.beginArray();
        reader.beginObject();
        String name;
        while(reader.hasNext()){

            name=reader.nextName();

            if(name.equals("data")){
                reader.beginObject();

                while(reader.hasNext()){
                    name=reader.nextName();
                    if(name.equals("id"))
                    {
                        meme.setID(reader.nextString());
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

            }else{
                reader.skipValue();
            }


        }
        reader.endObject();
        reader.endObject();
        return meme;
    }

}
