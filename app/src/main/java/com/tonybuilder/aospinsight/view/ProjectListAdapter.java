package com.tonybuilder.aospinsight.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonybuilder.aospinsight.R;
import com.tonybuilder.aospinsight.model.Project;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectEntryViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private String keyWords = null;
    private List<Project> projectList = new ArrayList<>();
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public ProjectListAdapter(Context context) {
        mContext = context;
    }

    public void setKeyWords(String s) {
        keyWords = s;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Project) v.getTag());
        }
    }

    @Override
    public ProjectEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_project_entry,
                parent, false);
        ProjectEntryViewHolder holder = new ProjectEntryViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProjectEntryViewHolder holder, int position) {
        holder.tvProjectId.setText(String.valueOf(projectList.get(position).getProjectId()));
        String projectName = projectList.get(position).getProjectName();
        SpannableString spanString = new SpannableString(projectList.get(position).getProjectName());
        if (keyWords != null && projectName.contains(keyWords)) {
            int begin = projectName.indexOf(keyWords);
            int end = begin + keyWords.length();
            ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.RED);
            spanString.setSpan(foregroundSpan, begin, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        holder.tvProjectName.setText(spanString);
        holder.itemView.setTag(projectList.get(position));
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    class ProjectEntryViewHolder extends RecyclerView.ViewHolder {
        TextView tvProjectName;
        TextView tvProjectId;
        ImageView imvProjectDetail;

        public ProjectEntryViewHolder(View view) {
            super(view);
            tvProjectId = view.findViewById(R.id.tv_project_id);
            tvProjectName = view.findViewById(R.id.tv_project_name);
            imvProjectDetail = view.findViewById(R.id.imv_project_detail);
        }
    }

    interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Project data);
    }
}