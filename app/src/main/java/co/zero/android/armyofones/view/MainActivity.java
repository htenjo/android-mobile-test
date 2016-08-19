package co.zero.android.armyofones.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.zero.android.armyofones.R;
import co.zero.android.armyofones.presenter.MainPresenter;

public class MainActivity extends BaseActivity implements ConverterView{
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        Button convert = (Button)findViewById(R.id.button_convert);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.updateExchangeRates();
            }
        });
    }

    @Override
    public void updateRateValues(double euro, double gbr, double jpy, double brz) {
        Toast.makeText(this, "Exchage Rates Updated", Toast.LENGTH_SHORT);
        TextView flag1 = (TextView)findViewById(R.id.text2_flag_1);
        TextView flag2 = (TextView)findViewById(R.id.text2_flag_2);
        TextView flag3 = (TextView)findViewById(R.id.text2_flag_3);
        TextView flag4 = (TextView)findViewById(R.id.text2_flag_4);

        flag1.setText(Double.toString(euro));
        flag2.setText(Double.toString(gbr));
        flag3.setText(Double.toString(jpy));
        flag4.setText(Double.toString(brz));
        Log.i(getLogTag(), "::::::::::: ACTUALIZADOS LOS VALORES");
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT);
    }
}
