package at.projekt.mc.grubchri.silkix.meme_projekt;

import java.util.LinkedList;
import java.util.List;

public class MemeModel {

    private List<Meme> memes = new LinkedList<>();

    public List<Meme> getMemes() {
        return memes;
    }

    public void setMemes(List<Meme> memes) {
        this.memes = memes;
    }
}
