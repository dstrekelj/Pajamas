package io.github.dstrekelj.pajamas.screens.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dstrekelj.pajamas.R;
import io.github.dstrekelj.pajamas.screens.record.RecordActivity;

/**
 * TODO: Comment.
 */
public class HomeActivity extends AppCompatActivity implements HomeContract.View {
    public static final String TAG = "HomeActivity";

    @BindView(R.id.activity_home_btn_navigate_about)
    Button btnNavigateAbout;
    @BindView(R.id.activity_home_btn_navigate_record)
    Button btnNavigateRecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
    }

    @Override
    public void goToRecordScreen() {
        startActivity(new Intent(this, RecordActivity.class));
    }

    @OnClick(R.id.activity_home_btn_navigate_about) void onClickNavigateAbout() {
        // TODO: 26.8.2016. Go to about screen
    }

    @OnClick(R.id.activity_home_btn_navigate_record) void onClickNavigateRecord() {
        goToRecordScreen();
    }
}
