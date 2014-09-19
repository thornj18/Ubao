package com.somaubao.ubao.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.somaubao.ubao.R;
import com.somaubao.ubao.models.PostItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Tonny on 7/19/2014.
 */
public class PostItemAdapter extends BaseAdapter {

    List<PostItem> dataSource;
    Context  context;
    LayoutInflater layoutInflater;
    LinearLayout linearLayout;

    public PostItemAdapter(Context context, int post_layout, List<PostItem> dataSource) {
        this.dataSource = dataSource;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public Bitmap loadImage(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        Bitmap bitmap = null;
        try {
            HttpResponse response = client.execute(new HttpGet(url));
            HttpEntity entity = response.getEntity();
            if(entity != null) {
                InputStream in = entity.getContent();
                bitmap = BitmapFactory.decodeStream(in);
            }
        }
        catch (Exception e) {
        }
        return bitmap;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {


        View row = convertView;
        MyHolder holder = null;
        if (row == null){

            row = layoutInflater.inflate(R.layout.post_layout, viewGroup, false);
            holder = new MyHolder(row);
            row.setTag(holder);
        }else{
            holder = (MyHolder) row.getTag();

        }
        PostItem postItem = dataSource.get(i);
        holder.articleTitleText.setText(postItem.post_Title);
        holder.articlePublishDateText.setText(postItem.post_Time);
        //holder.articleImage.setImageBitmap(BitmapFactory.decodeFile(postItem.post_ImageURL));
       // holder.articleImage.setImageDrawable(Drawable.createFromPath(postItem.post_ImageURL));
       // holder.articleImage.setImageURI(Uri.parse(postItem.post_ImageURL));
        holder.articleImage.setImageBitmap(loadImage(postItem.post_ImageURL));
        holder.articlePublisher.setText("Michuzi Blog");
        return row;
    }
}
class MyHolder{
    TextView articleTitleText;
    TextView articlePublishDateText;
    TextView articlePublisher;
    ImageView articleImage;

    public MyHolder(View view){
        articleTitleText = (TextView) view.findViewById(R.id.txtTitle);
        articleImage = (ImageView) view.findViewById(R.id.imgPost);
        articlePublishDateText = (TextView) view.findViewById(R.id.txtTime);
        articlePublisher = (TextView) view.findViewById(R.id.txtSource);

    }
}
