package org.gearvrf.gvrbullet;

import org.gearvrf.GVRActivity;

import android.os.Bundle;

public class BulletSampleActivity extends GVRActivity {

    BulletSampleMain main = new BulletSampleMain();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMain(main, "gvr.xml");
    }

}
