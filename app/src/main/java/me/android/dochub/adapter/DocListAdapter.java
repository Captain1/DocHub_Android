package me.android.dochub.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.android.dochub.models.DocItem;
import me.android.dochub.utils.DocImageView;
import me.android.dochub.app.AppController;
import me.android.dochub.R;
import me.android.dochub.utils.MD5Digest;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class DocListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<DocItem> docItems;
    MD5Digest digest = new MD5Digest();
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public DocListAdapter(Activity activity, List<DocItem> docItems) {
		this.activity = activity;
		this.docItems = docItems;
	}
	@Override
	public int getCount() {
		return docItems.size();
	}

	@Override
	public Object getItem(int location) {
		return docItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null){
			convertView = inflater.inflate(R.layout.doc_item, null);
        }

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView timestamp = (TextView) convertView
				.findViewById(R.id.timestamp);
		TextView description = (TextView) convertView
				.findViewById(R.id.description);
		TextView license = (TextView) convertView.findViewById(R.id.license);
		NetworkImageView profilePic = (NetworkImageView) convertView
				.findViewById(R.id.profilePic);
		DocImageView docImageView = (DocImageView) convertView
				.findViewById(R.id.docImage);

		DocItem item = docItems.get(position);

		title.setText(item.getTitle());

        String s = item.getCreated_at();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date d = formatter.parse(s);
            String t = formatter.format(d);
            timestamp.setText(t);
        } catch (ParseException e) {
            e.printStackTrace();
        }

		if (!TextUtils.isEmpty(item.getDescription())) {
			description.setText(item.getDescription());
			description.setVisibility(View.VISIBLE);
		} else {
			description.setVisibility(View.GONE);
		}

        if (item.getLicense()!= null) {
            license.setText(item.getLicense());
            license.setVisibility(View.VISIBLE);
        } else {
            license.setVisibility(View.GONE);
        }

//		user profile pic
        String image = null;
        try {
            image = item.getProfilePic()+
                           digest.encode(item.getEmail().toLowerCase())+".png?s=32";
            Log.d("image",image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        profilePic.setImageUrl(image, imageLoader);

//		Doc cover image
		if (item.getImage() != null) {
			docImageView.setImageUrl(item.getImage(), imageLoader);
			docImageView.setVisibility(View.VISIBLE);
			docImageView
					.setResponseObserver(new DocImageView.ResponseObserver() {
						@Override
						public void onError() {
						}

						@Override
						public void onSuccess() {
						}
					});
		} else {
			docImageView.setVisibility(View.GONE);
		}

		return convertView;

	}

}
