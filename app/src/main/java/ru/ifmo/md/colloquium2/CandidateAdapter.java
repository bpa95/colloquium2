package ru.ifmo.md.colloquium2;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CandidateAdapter extends ArrayAdapter<CandidateData> {
    private Activity context;
    private ArrayList<CandidateData> posts;
    private int viewId;

    public CandidateAdapter(Context context, int viewId, ArrayList<CandidateData> posts) {
        super(context, viewId, posts);
        this.context = (Activity) context;
        this.viewId = viewId;
        this.posts = posts;
    }

    private static class ViewHolder {
        TextView candidateName;
        TextView numOfVotes;
        TextView percents;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.candidate_item, null);

            viewHolder = new ViewHolder();
            viewHolder.candidateName = (TextView) convertView.findViewById(R.id.candidate_name);
            viewHolder.numOfVotes = (TextView) convertView.findViewById(R.id.num_of_votes);
            viewHolder.percents = (TextView) convertView.findViewById(R.id.percents);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.candidateName.setText(posts.get(position).getCandidateName());
        viewHolder.numOfVotes.setText(Integer.toString(posts.get(position).getNumOfVotes()));
        int percent = posts.get(position).getPercents();
        viewHolder.percents.setText((percent / 100) + "." + (percent % 100) + "%");

        return convertView;
    }
}
