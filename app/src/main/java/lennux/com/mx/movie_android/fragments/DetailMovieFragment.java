package lennux.com.mx.movie_android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lennux.com.mx.movie_android.R;
import lennux.com.mx.movie_android.models.Cast;
import lennux.com.mx.movie_android.models.Movie;
import lennux.com.mx.movie_android.models.Ratings;


public class DetailMovieFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_MOVIE= "param_movie";

    // TODO: Rename and change types of parameters
    private Movie movie;
    private String mParam2;

    public static DetailMovieFragment newInstance(Movie movie) {
        DetailMovieFragment fragment = new DetailMovieFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_MOVIE, movie);

        fragment.setArguments(args);
        return fragment;
    }

    public DetailMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.movie = (Movie) getArguments().getSerializable(ARG_PARAM_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        ImageView imaPoster = (ImageView) view.findViewById(R.id.imgPoster);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtDetailTitle);
        TextView txtRuntime = (TextView) view.findViewById(R.id.txtRuntime);
        TextView txtSynopsis= (TextView) view.findViewById(R.id.txtSynopsis);
        TextView txtCasting = (TextView) view.findViewById(R.id.txtCasting);
        RatingBar ratingCritic = (RatingBar) view.findViewById(R.id.ratingCritic);
        RatingBar ratingAudience = (RatingBar) view.findViewById(R.id.ratingAudience);

        Picasso.with(getActivity()).load(movie.getPosterUrl()).into(imaPoster);
        txtTitle.setText(movie.getTitle());
        txtRuntime.setText(movie.getRuntime() +" min.");

        ArrayList<Cast> casting = movie.getCasting();

        for (int i = 0; i<casting.size(); i++)
        {
            if (i == 0)
                txtCasting.setText(casting.get(i).getName());
            else
                txtCasting.append("," + casting.get(i).getName() );
        }
        txtSynopsis.setText(movie.getSynopsis());

        Ratings rating = movie.getRating();
        double score = rating.getAudienceScore()*0.01 * 5;
        ratingAudience.setRating((float) score);

        double scoreCritic = rating.getCriticScore()*0.01 * 5;
        ratingCritic.setRating((float) scoreCritic);

        return view;
    }



}
