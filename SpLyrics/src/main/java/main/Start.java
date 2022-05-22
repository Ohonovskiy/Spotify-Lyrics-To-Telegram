package main;

import com.neovisionaries.i18n.CountryCode;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Start {
    static final String accessToken = "YourSpotifyToken";

    static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    static final GetUsersCurrentlyPlayingTrackRequest getUsersCurrentlyPlayingTrackRequest = spotifyApi
            .getUsersCurrentlyPlayingTrack()
            .market(CountryCode.SE)
            .additionalTypes("track,episode")
            .build();

    public static String getCurrentTrack() {
        try {
            final CurrentlyPlaying currentlyPlaying = getUsersCurrentlyPlayingTrackRequest.execute();
            Pattern a = Pattern.compile("Track\\(name=(?<track>[^,)]*), artists=\\[ArtistSimplified\\(name=(?<artist>[^)]*)\\,");
            Matcher m = a.matcher(currentlyPlaying.toString());
            String musicWithAuthor = "";

            while (m.find()) {
                musicWithAuthor = (m.group("track") + " by " + m.group("artist"));
            }
            return musicWithAuthor;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

}
