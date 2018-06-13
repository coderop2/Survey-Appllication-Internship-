package aiims.survey.techmahindra.myapplication.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import aiims.survey.techmahindra.myapplication.R;
import aiims.survey.techmahindra.myapplication.SurveyComponents.AnsweredQuestion;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Question;
import aiims.survey.techmahindra.myapplication.SurveyComponents.ResponseElement;

/**
 * Created by yashjain on 7/14/17.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private Context mContext;
    private AnsweredQuestion[] questions;

    public QuestionAdapter(AnsweredQuestion[] questions, Context mContext) {
        this.questions = questions;
        this.mContext = mContext;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_element, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, final int position) {
        AnsweredQuestion aq = questions[position];

        holder.qText.setText(aq.getqText());
        String options[] = aq.getOptions();
        int oLength = options.length;

        if (aq.isMultiSelect()) {
            LinearLayout ll = new LinearLayout(mContext);
            ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.setOrientation(LinearLayout.VERTICAL);
            holder.checkBoxes = new AppCompatCheckBox[oLength];
            for (int i = 0; i < oLength; i++) {
                holder.checkBoxes[i] = getCheckBoxView(options[i]);
                aq.setViewId(holder.checkBoxes[i].getId());
                holder.checkBoxes[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((AppCompatCheckBox) view).isChecked())
                            questions[position].addAnswer(((AppCompatCheckBox) view).getText().toString());
                        else
                            questions[position].removeAnswer(((AppCompatCheckBox) view).getText().toString());
                    }
                });
                /*
                if(questions[position].isAnswered()){
                    if(questions[position].isPresentInOptions(options[i])){
                        Log.i("In VIEW CHECKED: ","Position: "+position+"  "+ " Option  No. "+ i+ "  Option "+options[i]);
                        holder.checkBoxes[i].setChecked(true);
                    }
                }
                */
                if (!holder.optionAdded)
                    ll.addView(holder.checkBoxes[i]);
            }
            if (!holder.optionAdded) {
                holder.qeInner.addView(ll);
                holder.optionAdded = true;
            }
        } else {
            int checkedPos = -1;
            holder.radioGroup = new RadioGroup(mContext);
            holder.radioButtons = new AppCompatRadioButton[oLength];
            for (int i = 0; i < oLength; i++) {
                holder.radioButtons[i] = getRadioButtonView(options[i]);
                questions[position].setViewId(holder.radioButtons[i].getId());
                holder.radioButtons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        questions[position].addAnswer(((RadioButton) view).getText().toString());
                    }
                });
                if (questions[position].isAnswered()) {
                    if (questions[position].isPresentInOptions(options[i])) {
                        checkedPos = i;
                        //holder.radioButtons[i].setChecked(true);//TODO: TRY AND REMIOVE THIS LINE
                    }
                }
                holder.radioGroup.addView(holder.radioButtons[i]);
            }

            /*
             if(checkedPos!=-1){
               holder.radioGroup.check(holder.radioButtons[checkedPos].getId());
                 Log.i("In VIEW RADIO: ","Position: "+position+"  "+ " Option  No. "+ i+ "  Option "+options[i]);

             }
            */

            if (!holder.optionAdded) {
                holder.qeInner.addView(holder.radioGroup);
                holder.optionAdded = true;

            }
        }
    }


    private AppCompatCheckBox getCheckBoxView(String option) {
        AppCompatCheckBox compatCheckBox = new AppCompatCheckBox(mContext);
        Resources resources = mContext.getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            compatCheckBox.setTextColor(mContext.getColor(R.color.qeOption));
        } else {
            compatCheckBox.setTextColor(resources.getColor(R.color.qeOption));
        }
        int padding = (int) resources.getDimension(R.dimen.qeOptionPadding);
        compatCheckBox.setPadding(padding, padding, padding, padding);
        compatCheckBox.setTextSize(resources.getDimensionPixelSize(R.dimen.qeOptionSize) / resources.getDisplayMetrics().scaledDensity);
        compatCheckBox.setText(option);
        return compatCheckBox;
    }

    private AppCompatRadioButton getRadioButtonView(String option) {
        AppCompatRadioButton compatRadioButton = new AppCompatRadioButton(mContext);
        Resources resources = mContext.getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            compatRadioButton.setTextColor(mContext.getColor(R.color.qeOption));
        } else {
            compatRadioButton.setTextColor(resources.getColor(R.color.qeOption));
        }
        int padding = (int) resources.getDimension(R.dimen.qeOptionPadding);
        compatRadioButton.setPadding(padding, padding, padding, padding);
        compatRadioButton.setTextSize(resources.getDimensionPixelSize(R.dimen.qeOptionSize) / resources.getDisplayMetrics().scaledDensity);
        compatRadioButton.setText(option);
        return compatRadioButton;
    }


    @Override
    public int getItemCount() {
        return questions.length;
    }

    public ArrayList<ResponseElement> getResponse() {
        ArrayList<ResponseElement> responseElements = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            ArrayList<String> answer = questions[i].getAnswer();
            if (answer.isEmpty()) continue;
            if (questions[i] == null) {
                Log.d("Questions " + String.valueOf(i), "is NULLLL");
            }
            ResponseElement responseElement = new ResponseElement();
            responseElement.setqNo(questions[i].getqNo());
            responseElement.setOptionSelected(answer);
            responseElement.setPageNo(questions[i].getPageNo());
            responseElements.add(responseElement);
        }
        return responseElements;
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout qeLinear, qeInner;
        public CardView mCardView;
        public AppCompatTextView qText;
        public AppCompatCheckBox[] checkBoxes;
        public RadioGroup radioGroup;
        public AppCompatRadioButton[] radioButtons;
        public boolean optionAdded;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            qeLinear = (LinearLayout) itemView;
            qText = itemView.findViewById(R.id.qeQtext);
            mCardView = itemView.findViewById(R.id.qeCardView);
            qeInner = itemView.findViewById(R.id.qeInner);
            optionAdded = false;
        }
    }
}
