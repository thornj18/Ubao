package com.somaubao.ubao;


import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

//TODO: This class you will fetch all the blogs from the Cloud(Source name, link & details of the Source) and populate them in
//TODO: the Listview below.
//TODO: Once a source is clicked, a dialog will be displayed for the user containing the Details of the Source from the cloud.
//TODO: Once the User clicks submit, his/her Choices will be saved in her cloud account and also saved in the device using
//TODO: 'savedpreferences'

public class Subscriptions extends Activity implements android.app.FragmentManager.OnBackStackChangedListener {

    Button btnFlip;
    private Handler mHandler = new Handler();
    private boolean mShowingBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);

        btnFlip = (Button) findViewById(R.id.btnFlipper);

        if (savedInstanceState == null) {

            getFragmentManager().beginTransaction().add(R.id.container, new Blogs()).commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }
        getFragmentManager().addOnBackStackChangedListener(this);
    }

    public void onFlip(View view) {
        flipCard();
        btnFlip.setText(R.string.newspaper_label);
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
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);

        // When the back stack changes, invalidate the options menu (action bar).
        invalidateOptionsMenu();

    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.

        mShowingBack = true;


        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.card_flip_right_in, R.anim.card_flip_right_out,
                        R.anim.card_flip_left_in, R.anim.card_flip_left_out)
                .replace(R.id.container, new Newspapers())
                .addToBackStack(null)
                .commit();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidateOptionsMenu();
            }
        });
    }

}