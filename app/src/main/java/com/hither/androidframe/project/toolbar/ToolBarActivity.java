package com.hither.androidframe.project.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hither.androidframe.R;

public class ToolBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);

        Toolbar toolBarID = (Toolbar) findViewById(R.id.toolBarID);
        toolBarID.setNavigationIcon(R.drawable.back_icon);
        toolBarID.setTitle("");
        setSupportActionBar(toolBarID);
    }
}
