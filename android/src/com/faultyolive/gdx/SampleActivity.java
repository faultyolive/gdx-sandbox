package com.faultyolive.gdx;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class SampleActivity extends AndroidApplication {
    public void onCreate (Bundle bundle) {
        super.onCreate(bundle);

        // obtain the test info
        Bundle extras = getIntent().getExtras();
        String name = (String)extras.get("sample");
        Sample sample = Sample.create(name);

        // and run the application...
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        initialize(sample, config);
    }
}
