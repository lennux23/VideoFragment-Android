package lennux.com.mx.movie_android.fragments;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import lennux.com.mx.movie_android.R;
import lennux.com.mx.movie_android.adapters.ListMovieAdapter;
import lennux.com.mx.movie_android.models.Movie;


public class ListMoviesFragment extends Fragment {

    public static final String API_KEY = "35hg37n2zaybbwf7wncj9vgw";

    private ArrayList<Movie> movies = new ArrayList<Movie>();

    private ListMovieAdapter adapter;

    private OnFragmentInteractionListener mListener;

    Uri.Builder builder = new  Uri.Builder();

    private MenuItem menuLoading;


    private boolean loading= false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_movies, container, false);
        ListView listMovies= (ListView) view.findViewById(R.id.listMovies);

        adapter = new ListMovieAdapter(getActivity(),R.layout.item_list_movies,movies);

        listMovies.setAdapter(adapter);

        listMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onMovieSelected(movies.get(position));
            }
        });

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

        inflater.inflate(R.menu.menu_list_movies,menu);

        this.menuLoading = menu.findItem(R.id.menuLoading);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getActivity().getComponentName());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals("") && !loading) {
                    builder.scheme("http")
                            .authority("api.rottentomatoes.com")
                            .appendPath("api")
                            .appendPath("public")
                            .appendPath("v1.0")
                            .appendPath("movies.json")
                            .appendQueryParameter("apikey", API_KEY)
                            .appendQueryParameter("q", query);

                    String url = builder.build().toString();

                    Log.e("URL", url);
                    Log.e("Pase al onmenu", "asas");
                    new RottenRequest().execute(url);
                   // builder.
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

            /*    builder.scheme("http")
                        .authority("api.rottentomatoes.com")
                        .appendPath("api")
                        .appendPath("public")
                        .appendPath("v1.0")
                        .appendPath("movies.json")
                        .appendQueryParameter("apikey", API_KEY)
                        .appendQueryParameter("q", newText);

                String url = builder.build().toString();

                Log.e("URL", url);

                new RottenRequest().execute(url);
                builder.clearQuery();*/
                return false;
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onMovieSelected(Movie movie);
    }

    public class RottenRequest extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse;
            String response;
            try {
                httpResponse = httpClient.execute(new HttpGet(params[0]));
                StatusLine statusLine = httpResponse.getStatusLine();

                if (statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    httpResponse.getEntity().writeTo(out);
                    out.close();
                    response = out.toString();
                    return response;
                } else {
                    Log.e("STATUSLINE", ""+statusLine.getStatusCode());
                    return null;
                }
            }
            catch (Exception e){
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setMenuLoading(true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null){
                Log.e("RESPONSE", result);
                movies = Movie.parseJSON(result);
                adapter.clear();
                adapter.addAll(movies);
                adapter.notifyDataSetChanged();
                setMenuLoading(false);
            }
        }


    }

    public void setMenuLoading(boolean load){
        this.loading = load;
        if (load){
            menuLoading.setVisible(true);
            menuLoading.setActionView(R.layout.menu_loading);
        } else {
            menuLoading.setVisible(false);
        }

    }

}
