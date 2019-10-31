package com.fronke.betatransmit.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fronke.betatransmit.R;
import com.kokotchy.betaSeriesAPI.model.Episode;

import java.util.List;

/**
 * Created by Kevin on 22/09/2015.
 */

public class EpisodeAdapter extends BaseAdapter {
    protected Context context;
    protected List<Episode> adapterList;
    protected LayoutInflater inflater;

    public EpisodeAdapter(Context context, List<Episode> episodes) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.adapterList = episodes;
    }

    @Override
    public Object getItem(int position) {
        return adapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return adapterList.size();
    }

    private class ViewHolder {
        TextView serie;
        TextView title;
        TextView status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_episode, null);
            holder.serie = (TextView) convertView.findViewById(R.id.item_episode_serie);
            holder.title = (TextView) convertView.findViewById(R.id.item_episode_title);
            holder.status = (TextView) convertView.findViewById(R.id.item_episode_status);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.serie.setText(adapterList.get(position).getShow());
        holder.title.setText(adapterList.get(position).getNb());
        if (adapterList.get(position).isDownloaded()) {
            holder.status.setText("Downloaded");
            holder.status.setTextColor(context.getResources().getColor(R.color.TextGreen));
        }
        else{
            holder.status.setText("Not Downloaded");
            holder.status.setTextColor(context.getResources().getColor(R.color.TextRed));
        }

        return convertView;
    }

}
