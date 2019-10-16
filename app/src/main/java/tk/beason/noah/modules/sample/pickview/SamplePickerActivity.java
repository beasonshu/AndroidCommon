package tk.beason.noah.modules.sample.pickview;

import android.os.Bundle;
import android.view.View;

import tk.beason.common.utils.DateUtils;
import tk.beason.common.widget.pickview.DateTimePicker;
import tk.beason.common.widget.pickview.DateTimePickerDialog;
import tk.beason.common.widget.pickview.PickerView;
import tk.beason.noah.R;
import tk.beason.noah.modules.base.AppBaseActivity;

import tk.beason.noah.modules.base.AppBaseActivity;

/**
 * 选择View的Sample
 */
public class SamplePickerActivity extends AppBaseActivity implements View.OnClickListener {

    private DateTimePicker mDateTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_pickview);
    }

    @Override
    protected void initViews() {
        super.initViews();
        PickerView pickerView = (PickerView) findViewById(R.id.picker_view);
        pickerView.setAdapter(new SamplePickerAdapter());

        mDateTimePicker = (DateTimePicker) findViewById(R.id.datePicker);

        View getTime = findViewById(R.id.get_time);
        getTime.setOnClickListener(this);

        View dialogTest = findViewById(R.id.dialog_test);
        dialogTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_time:
                String time = DateUtils.format(mDateTimePicker.getCurrentTime(), DateUtils.FORMAT_YMDHMS_CN);
                dialog(time);
                break;
            case R.id.dialog_test:
                DateTimePickerDialog dialog = new DateTimePickerDialog(this);
                dialog.show();
                break;
        }
    }
}
