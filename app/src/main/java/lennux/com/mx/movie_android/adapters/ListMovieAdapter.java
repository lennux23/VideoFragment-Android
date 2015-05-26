package lennux.com.mx.movie_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lennux.com.mx.movie_android.R;
import lennux.com.mx.movie_android.models.Movie;

/**
 * Created by mobilestudio06 on 25/05/15.
 */
public class ListMovieAdapter extends ArrayAdapter{
    private ArrayList<Movie> movies;
    private Context context;
    private int resource;
    private LayoutInflater inflater;

    public ListMovieAdapter(Context context, int resource, ArrayList<Movie> movies) {
        super(context, resource, movies);
        this.movies = movies;
        this.context = context;
        this.resource = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(resource,parent,false);
            holder.imgThumbnail = (ImageView) convertView.findViewById(R.id.imgThumbnail);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
           //Para crear la vista una vez
            convertView.setTag(holder);
        } else {
            //ya se creo una ves el holder ahora solo lo vamos a llenar
            holder = (ViewHolder) convertView.getTag();
        }

        Movie movie = movies.get(position);
        holder.txtTitle.setText(movie.getTitle());

        Picasso.with(context).load(movie.getPosterUrl()).into(holder.imgThumbnail);

        return convertView;
    }

    class ViewHolder {
        ImageView imgThumbnail;
        TextView txtTitle;
    }
}
