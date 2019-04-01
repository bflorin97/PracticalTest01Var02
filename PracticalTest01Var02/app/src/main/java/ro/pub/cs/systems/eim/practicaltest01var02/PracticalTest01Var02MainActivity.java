package ro.pub.cs.systems.eim.practicaltest01var02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var02MainActivity extends AppCompatActivity {

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    private IntentFilter intentFilter = new IntentFilter();

    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;

    TextView rezultat;
    EditText numar1, numar2;
    Button plus, minus, nav;
    String lastOp = "";

    View.OnClickListener operatie = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.minus: {
                    try {
                        int a = Integer.parseInt(numar1.getText().toString());
                        int b = Integer.parseInt(numar2.getText().toString());
                        rezultat.setText(String.valueOf(a - b));
                        lastOp = "-";

                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02Service.class);
                        intent.putExtra("firstNumber", a);
                        intent.putExtra("secondNumber", b);
                        getApplicationContext().startService(intent);
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Bad input", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    break;
                }
                case R.id.plus: {
                    try {
                        int a = Integer.parseInt(numar1.getText().toString());
                        int b = Integer.parseInt(numar2.getText().toString());
                        rezultat.setText(String.valueOf(a + b));
                        lastOp = "+";

                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02Service.class);
                        intent.putExtra("number1", a);
                        intent.putExtra("number2", b);
                        getApplicationContext().startService(intent);
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Bad input", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    break;
                }
                case R.id.nav: {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02SecondaryActivity.class);
                    int rez = Integer.parseInt(rezultat.getText().toString());
                    intent.putExtra("rez", rez);
                    intent.putExtra("op", lastOp);
                    startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_main);

        rezultat = findViewById(R.id.rezultat);
        numar1 = findViewById(R.id.numar1);
        numar2 = findViewById(R.id.numar2);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        nav = findViewById(R.id.nav);

        plus.setOnClickListener(operatie);
        minus.setOnClickListener(operatie);
        nav.setOnClickListener(operatie);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("numar1")) {
                numar1.setText(savedInstanceState.getString("numar1"));
            } else {
                numar1.setText(String.valueOf(0));
            }
            if (savedInstanceState.containsKey("numar2")) {
                numar2.setText(savedInstanceState.getString("numar2"));
            } else {
                numar2.setText(String.valueOf(0));
            }
        } else {
            numar1.setText(String.valueOf(0));
            numar2.setText(String.valueOf(0));
        }

        intentFilter.addAction("sum");
        intentFilter.addAction("dif");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
