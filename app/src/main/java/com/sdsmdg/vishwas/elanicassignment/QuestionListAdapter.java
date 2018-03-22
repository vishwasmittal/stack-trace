package com.sdsmdg.vishwas.elanicassignment;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by vishwas on 22/3/18.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {

    private QuestionClass questionObject;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView questionTitle;
        public TextView questionText;
        public ImageView profilePic;

        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            questionTitle = itemView.findViewById(R.id.question_title);
            questionText = itemView.findViewById(R.id.question_text);
            profilePic = itemView.findViewById(R.id.profile_image);
        }
    }

    public QuestionListAdapter(Context context, QuestionClass questionObject) {
        this.questionObject = questionObject;
        this.context = context;
    }


    @Override
    public QuestionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionListAdapter.ViewHolder holder, int position) {
        final QuestionClass.Items item = questionObject.getItems().get(position);
        holder.questionTitle.setText(item.getTitle());
        holder.questionText.setText(Html.fromHtml(item.getBody()));
        holder.username.setText(item.getOwner().getDisplay_name());

        Glide.with(context)
                .load(item.getOwner().getProfile_image())
                .placeholder(R.mipmap.ic_launcher_round)
                .thumbnail(0.5f)
                .bitmapTransform(new jp.wasabeef.glide.transformations.CropCircleTransformation(context))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.profilePic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
                customTabsIntent.launchUrl(v.getContext(), Uri.parse(item.getLink()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionObject.getSize();
    }

}
