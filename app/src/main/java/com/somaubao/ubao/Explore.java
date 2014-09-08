package com.somaubao.ubao;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.somaubao.ubao.parser.Parser;
import com.somaubao.ubao.parser.RssParser;
import com.somaubao.ubao.adapters.PostItemAdapter;
import com.somaubao.ubao.models.PostItem;
import java.util.ArrayList;
import java.util.List;

public class Explore extends Fragment {
    private ListView feedView;
    private RssParser rssParser;
    private Parser parser;
    private List<PostItem> postItems;
    private PostItemAdapter postItemAdapter;


    public Explore() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static final String MICHUZIBLOG ="http://issamichuzi.blogspot.com/feeds/posts/default?alt=rss";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        feedView = (ListView) rootView.findViewById(R.id.stories_list);
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_category);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_items, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        postItems = new ArrayList<PostItem>();

        if (isNetworkAvailable()){

        new ParseFeed().execute(MICHUZIBLOG);
        feedView.setAdapter(postItemAdapter);

        }
        else{

            Toast.makeText(getActivity(),"Ooops! Network is Unavailable",Toast.LENGTH_SHORT ).show();

        }

        return rootView;


    }

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }


    public class ParseFeed extends AsyncTask<String, Void, List<PostItem>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<PostItem> doInBackground(String... params) {
            for (String urlVal:params){
                parser = new Parser(urlVal);
            }
            try {
                postItems = parser.parse();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return postItems;
        }

        @Override
        protected void onPostExecute(final List<PostItem> postItems) {

            postItemAdapter = new PostItemAdapter(getActivity(),R.layout.post_layout,postItems);
            int count = postItemAdapter.getCount();
            if (count != 0 && postItemAdapter != null ){
                feedView.setAdapter(postItemAdapter);

                postItemAdapter.notifyDataSetChanged();
            }else {
                super.onPostExecute(postItems);


            }
            feedView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    PostItem data = postItems.get(i);

                    Bundle postinfo = new Bundle();
                    postinfo.putString("link", data.post_Link);
                    postinfo.putString("title", data.post_Title);

                    //Intent  postviewIntent = new Intent(getActivity(), PostViewActivity.class);
                    //postviewIntent.putExtras(postinfo);
                    //startActivity(postviewIntent);

                }
            });

        }
    }


}
