package com.somaubao.ubao;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import com.somaubao.ubao.adapters.PostItemAdapter;
import com.somaubao.ubao.models.PostItem;
import java.util.ArrayList;
import java.util.List;

public class Explore extends Fragment {
    private ListView feedView;
    private Parser parser;
    protected List<PostItem> postItems;
    private PostItemAdapter postItemAdapter;

    public Explore() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //news sources with there rss links are declared here
    public static final String MICHUZIBLOG ="http://issamichuzi.blogspot.com/feeds/posts/default?alt=rss";
    public static final String SHAFIHDAUDA ="http://shaffihdauda.com/?feed=rss2";
    public static final String MISSIPOPULAR ="http://feeds.feedburner.com/MissiePopular?format=xml";

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

                switch (i){
                    case 0:
                       // Toast.makeText(getActivity(), "News clicked", Toast.LENGTH_SHORT).show();
                        if (isNetworkAvailable()) {
                            ParseFeed parseFeed = new ParseFeed();
                            parseFeed.execute(MICHUZIBLOG);

                        }else{
                            Toast.makeText(getActivity(), "Internet Connection Unavailable",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 1:
                        //Toast.makeText(getActivity(), "Showbiz", Toast.LENGTH_SHORT).show();
                        if (isNetworkAvailable()) {
                            ParseFeed parseFeed = new ParseFeed();
                            parseFeed.execute(MISSIPOPULAR);

                        }else{
                            Toast.makeText(getActivity(), "Internet Connection Unavailable",Toast.LENGTH_LONG).show();
                        }

                        break;
                    case 2:
                        //Toast.makeText(getActivity(), "Sports", Toast.LENGTH_SHORT).show();

                        if (isNetworkAvailable()) {
                            ParseFeed parseFeed = new ParseFeed();
                            parseFeed.execute(SHAFIHDAUDA);

                        }else{
                            Toast.makeText(getActivity(), "Internet Connection Unavailable",Toast.LENGTH_LONG).show();
                        }
                        break;
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        postItems = new ArrayList<PostItem>();


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

    private void updateList() {

        if (postItems == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.error_title));
            builder.setMessage(getString(R.string.error_message));
            builder.setPositiveButton(getString(R.string.retry), null);
            builder.setNegativeButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();

        }else {

            postItemAdapter = new PostItemAdapter(getActivity(),R.layout.post_layout,postItems);
            int count = postItemAdapter.getCount();

            if (count != 0) {
                feedView.setAdapter(postItemAdapter);
            }
               postItemAdapter.notifyDataSetChanged();
        }
    }


    //Everything is being executed in the background thread, The method parse from the Parser is called here
    public class ParseFeed extends AsyncTask<String, Void, List<PostItem>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<PostItem> doInBackground(String... params) {

            postItems = null;

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
        protected void onPostExecute(final List<PostItem> results) {
            postItems = results;
            updateList();
            feedView.setOnItemClickListener(new postClicked());

        }

        private class postClicked implements OnItemClickListener {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                PostItem data = postItems.get(i);

                   Bundle postinfo = new Bundle();
                                 postinfo.putString("link", data.post_Link);
                                 postinfo.putString("title", data.post_Title);
                                 postinfo.putString("description", data.post_description);
                Intent readPost = new Intent(getActivity(), Postview.class);
                readPost.putExtras(postinfo);
                startActivity(readPost);
            }
        }
    }




}
