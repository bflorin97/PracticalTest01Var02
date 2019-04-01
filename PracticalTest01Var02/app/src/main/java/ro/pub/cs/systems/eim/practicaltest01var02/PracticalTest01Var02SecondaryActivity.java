package ro.pub.cs.systems.eim.practicaltest01var02;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var02SecondaryActivity extends AppCompatActivity {

    private TextView rez = null;
    private Button okButton = null;
    private Button cancelButton = null;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.correct:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.incorrect:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_secondary);

        rez = findViewById(R.id.rez);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("rez") && intent.getExtras().containsKey("op")) {
            int rezValue = intent.getIntExtra("rez", -1);
            String opValue = intent.getStringExtra("op");
            rez.setText(String.valueOf(rezValue) + opValue);
        }

        okButton = (Button)findViewById(R.id.correct);
        okButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button)findViewById(R.id.incorrect);
        cancelButton.setOnClickListener(buttonClickListener);
    }



}
