package com.tonybuilder.aospinsight.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonybuilder.aospinsight.R;
import com.tonybuilder.aospinsight.model.Commit;
import com.tonybuilder.aospinsight.model.Project;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CommitListAdapter extends RecyclerView.Adapter<CommitListAdapter.CommitEntryViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private List<Commit> commitList = new ArrayList<>();
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public CommitListAdapter(Context context) {
        mContext = context;
    }

    public void setCommitList(List<Commit> commitList) {
        this.commitList = commitList;
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
    public CommitEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_commit_entry,
                parent, false);
        CommitEntryViewHolder holder = new CommitEntryViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommitEntryViewHolder holder, int position) {
        Commit c = commitList.get(position);

        // TODO: modify commit data
        holder.tvCommitHashId.setText(c.getCommitHashId());
        holder.tvCommitAuthor.setText(c.getCommitAuthorMail());
        holder.tvCommitDate.setText(c.getCommitSubmitDate().toString());
        holder.tvCommitMessage.setText(c.getCommitLog());
        holder.tvCommitNumStat.setText(c.getCommitChangedLines());

        holder.itemView.setTag(commitList.get(position));
    }

    @Override
    public int getItemCount() {
        return commitList.size();
    }

    class CommitEntryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCommitHashId;
        TextView tvCommitAuthor;
        TextView tvCommitDate;
        TextView tvCommitMessage;
        TextView tvCommitNumStat;
        ImageView imvCommitDetail;

        public CommitEntryViewHolder(View view) {
            super(view);
            tvCommitHashId = view.findViewById(R.id.tv_commit_hash_id);
            tvCommitAuthor = view.findViewById(R.id.tv_commit_author);
            tvCommitDate = view.findViewById(R.id.tv_commit_date);
            tvCommitMessage = view.findViewById(R.id.tv_commit_message);
            tvCommitNumStat = view.findViewById(R.id.tv_commit_numstat);
            imvCommitDetail = view.findViewById(R.id.imv_commit_detail);
        }
    }

    interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Project data);
    }
}