package aiims.survey.techmahindra.myapplication.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import aiims.survey.techmahindra.myapplication.Activities.QuestionListActivity;
import aiims.survey.techmahindra.myapplication.Activities.ResponderInfoActivity;
import aiims.survey.techmahindra.myapplication.Activities.SurveyListActivity;
import aiims.survey.techmahindra.myapplication.R;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Question;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Survey;

/**
 * Created by yashjain on 7/12/17.
 */

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder> {

    public static final String KEY_INTENT_SURVEY = "survey";
    public static final String KEY_INTENT_RESPONDERINFO = "responderinfo";
    private Survey[] surveys;
    private Context mContext;
    private String pages, total, version, type;

    public SurveyAdapter(Survey[] surveys, Context mContext) {
        this.surveys = surveys;
        this.mContext = mContext;
        type = mContext.getString(R.string.seType);
        version = mContext.getString(R.string.seVersion);
        pages = mContext.getString(R.string.seTotalP);
        total = mContext.getString(R.string.seTotalQ);
    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_element, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, final int position) {
        Survey s = surveys[position];
        holder.title.setText(s.getTitle());
        holder.desc.setText(s.getDescription());
        holder.pages.setText(pages + s.getTotalPage());
        holder.total.setText(total + s.getTotalQuestions());
        holder.version.setText(version + s.getVersion());
        holder.type.setText(type + s.getType());
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ResponderInfoActivity.class);
                intent.putExtra(KEY_INTENT_SURVEY, surveys[position]);
                mContext.startActivity(intent);
            }
        };
        holder.mCardView.setOnClickListener(onClickListener);
        holder.type.setOnClickListener(onClickListener);
        holder.version.setOnClickListener(onClickListener);
        holder.total.setOnClickListener(onClickListener);
        holder.pages.setOnClickListener(onClickListener);
        holder.desc.setOnClickListener(onClickListener);
        holder.title.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return surveys.length;
    }

    public static class SurveyViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public AppCompatTextView title, desc, pages, total, version, type;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.seCardView);
            title = itemView.findViewById(R.id.seTitle);
            desc = itemView.findViewById(R.id.seDescription);
            pages = itemView.findViewById(R.id.seTotalP);
            total = itemView.findViewById(R.id.seTotalQ);
            version = itemView.findViewById(R.id.seVersion);
            type = itemView.findViewById(R.id.seType);
        }

    }
}
