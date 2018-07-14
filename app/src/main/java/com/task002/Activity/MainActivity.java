package com.task002.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.task002.Fragment.RegisterFragment;
import com.task002.R;
import com.task002.Utilities.Utilities;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utilities.deletePreferences(this);
        RegisterFragment registerFragment = new RegisterFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment,registerFragment).addToBackStack("").commit();
    }
}
