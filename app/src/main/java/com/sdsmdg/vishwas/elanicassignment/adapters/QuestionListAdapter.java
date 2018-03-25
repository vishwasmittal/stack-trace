package com.sdsmdg.vishwas.elanicassignment.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import com.bumptech.glide.request.RequestOptions;
import com.lsjwzh.widget.text.FastTextView;
import com.lsjwzh.widget.text.ReadMoreTextView;
import com.sdsmdg.vishwas.elanicassignment.R;
import com.sdsmdg.vishwas.elanicassignment.models.Items;
import com.sdsmdg.vishwas.elanicassignment.models.QuestionClass;


public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {

    private QuestionClass questionObject;
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        ReadMoreTextView questionTitle;
        FastTextView questionText;
        ImageView profilePic;

        ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            questionTitle = itemView.findViewById(R.id.question_title);
            questionText = itemView.findViewById(R.id.question_text);
            profilePic = itemView.findViewById(R.id.profile_image);

            questionTitle.setEllipsize(3);
            questionText.setEllipsize(3);
        }
    }

    public QuestionListAdapter(Context context, QuestionClass questionObject) {
        this.questionObject = questionObject;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionListAdapter.ViewHolder holder, int position) {
        final Items item = questionObject.getItems().get(position);
        holder.questionText.setText(Html.fromHtml(item.getBody()));
        holder.username.setText(item.getOwner().getDisplay_name());

        holder.questionTitle.setText(item.getTitle());
//        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(item.getTitle());
//        spannableStringBuilder.setSpan(new StrokeSpan(Color.BLUE, Color., 20), 0, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        holder.questionTitle.setText(spannableStringBuilder);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher_round);
        requestOptions.transform(new jp.wasabeef.glide.transformations.CropCircleTransformation());
        Glide.with(context)
                .load(item.getOwner().getProfile_image())
                .apply(requestOptions)
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
