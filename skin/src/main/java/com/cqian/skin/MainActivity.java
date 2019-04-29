package com.cqian.skin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cqian.baselibrary.ioc.OnClick;
import com.cqian.baselibrary.ioc.ViewById;
import com.cqian.baselibrary.ioc.ViewUtils;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById(R.id.textView)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        System.out.println(-15 >> 2);
        System.out.println(-1 >> 1 );
        //mTextView.setText("  "+(Integer.MAX_VALUE >> 2));
    }

    @OnClick(R.id.textView)
    public void onClick(View view) {
        Toast.makeText(this, "dsds", Toast.LENGTH_LONG).show();
    }
}
