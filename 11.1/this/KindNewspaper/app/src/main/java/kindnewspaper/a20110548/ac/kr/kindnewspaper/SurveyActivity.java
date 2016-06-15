package kindnewspaper.a20110548.ac.kr.kindnewspaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by subin on 2016-06-10.
 */
public class SurveyActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    RadioGroup radioGroup3;
    RadioGroup radioGroup4;
    RadioGroup radioGroup5;

    int[] answer = {0,0,0,0,0};

    Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_view);

        radioGroup1 = (RadioGroup)findViewById(R.id.answer1);
        radioGroup2 = (RadioGroup)findViewById(R.id.answer2);
        radioGroup3 = (RadioGroup)findViewById(R.id.answer3);
        radioGroup4 = (RadioGroup)findViewById(R.id.answer4);
        radioGroup5 = (RadioGroup)findViewById(R.id.answer5);

        radioGroup1.setOnCheckedChangeListener(this);
        radioGroup2.setOnCheckedChangeListener(this);
        radioGroup3.setOnCheckedChangeListener(this);
        radioGroup4.setOnCheckedChangeListener(this);
        radioGroup5.setOnCheckedChangeListener(this);

        btn = (Button)findViewById(R.id.InsertBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //      Toast.makeText(SurveyActivity.this,Integer.toString(Answer1) + " " + Integer.toString(Answer2)+ " " + Integer.toString(Answer3)+ " " + Integer.toString(Answer4)+ " " + Integer.toString(Answer5),Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Answer1",Integer.toString(answer[0]));
                resultIntent.putExtra("Answer2",Integer.toString(answer[1]));
                resultIntent.putExtra("Answer3",Integer.toString(answer[2]));
                resultIntent.putExtra("Answer4",Integer.toString(answer[3]));
                resultIntent.putExtra("Answer5",Integer.toString(answer[4]));
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio1_1 :
                answer[0] = 1;
                break;
            case R.id.radio1_2 :
                answer[0] = 2;
                break;
            case R.id.radio1_3 :
                answer[0] = 3;
                break;
            case R.id.radio1_4 :
                answer[0] = 4;
                break;
            case R.id.radio1_5 :
                answer[0] = 5;
                break;
            //1번질문 끝

            case R.id.radio2_1 :
                answer[1] = 1;
                break;
            case R.id.radio2_2 :
                answer[1] = 2;
                break;
            case R.id.radio2_3 :
                answer[1] = 3;
                break;
            case R.id.radio2_4 :
                answer[1] = 4;
                break;
            case R.id.radio2_5 :
                answer[1] = 5;
                break;
            //2번질문 끝

            case R.id.radio3_1 :
                answer[2] = 1;
                break;
            case R.id.radio3_2 :
                answer[2] = 2;
                break;
            case R.id.radio3_3 :
                answer[2] = 3;
                break;
            case R.id.radio3_4 :
                answer[2] = 4;
                break;
            case R.id.radio3_5 :
                answer[2] = 5;
                break;
            //3번질문 끝

            case R.id.radio4_1 :
                answer[3] = 1;
                break;
            case R.id.radio4_2 :
                answer[3] = 2;
                break;
            case R.id.radio4_3 :
                answer[3] = 3;
                break;
            case R.id.radio4_4 :
                answer[3] = 4;
                break;
            case R.id.radio4_5 :
                answer[3] = 5;
                break;
            //4번질문 끝

            case R.id.radio5_1 :
                answer[4] = 1;
                break;
            case R.id.radio5_2 :
                answer[4] = 2;
                break;
            case R.id.radio5_3 :
                answer[4] = 3;
                break;
            case R.id.radio5_4 :
                answer[4] = 4;
                break;
            case R.id.radio5_5 :
                answer[4] = 5;
                break;
            //5번질문끝
        }

    }
}
