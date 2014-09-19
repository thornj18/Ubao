package com.somaubao.ubao;

//import com.example.ubao.dialogs.CategorySources;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Subscriptions extends Activity  implements AdapterView.OnItemClickListener{
    ListView blogList;
    String[] categoryItems = {"LOCAL STORIES","SHOWBIZ","FASHION","SPORTS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        blogList = (ListView) findViewById(R.id.categoryListview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryItems);
        blogList.setAdapter(adapter);
        blogList.setOnItemClickListener(this);
    }


  

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position){
            case 0:
                //android.app.FragmentManager manager = getFragmentManager();
                //CategorySources categorySources = new CategorySources();
                //categorySources.show(manager, "CategoryDialog");
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;

            default:
                break;

        }

    }
}
