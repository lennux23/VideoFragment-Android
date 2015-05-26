package lennux.com.mx.movie_android.models;

/**
 * Created by mobilestudio06 on 18/05/15.
 */
public class Ratings {
    private int criticScore;
    private int audienceScore;

    public Ratings(int criticScore, int audienceScore) {
        this.criticScore = criticScore;
        this.audienceScore = audienceScore;
    }

    public int getCriticScore() {
        return criticScore;
    }

    public void setCriticScore(int criticScore) {
        this.criticScore = criticScore;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }
}
