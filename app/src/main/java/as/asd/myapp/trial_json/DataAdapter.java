package as.asd.myapp.trial_json;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class DataAdapter extends ArrayAdapter<MovieModel> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MovieModel> mList;

    public DataAdapter(Context c) {
        super(c, R.layout.custom);
        mContext = c;

        mInflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        if (mList == null)
            return 0;
        else
            return mList.size();

    }

    public void setData(List<MovieModel> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public MovieModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        MovieModel model = getItem(position);

        ViewHolder viewHolder;
        final View result;
        if (view == null) {

            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.custom, viewGroup, false);


            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.poster= (ImageView) view.findViewById(R.id.poster);


            result = view;

            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
            result = view;
        }
        viewHolder.name.setText(mList.get(position).getOriginalTitle());
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w500"+mList.get(position).getPosterPath()).into(viewHolder.poster);

        return result;
    }

    private static class ViewHolder {
        TextView name;
        ImageView poster;
    }
}
