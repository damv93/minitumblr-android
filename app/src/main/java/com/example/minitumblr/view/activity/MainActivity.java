package com.example.minitumblr.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.minitumblr.R;
import com.example.minitumblr.controller.util.Constants;
import com.example.minitumblr.controller.util.SharedPrefs;
import com.example.minitumblr.view.fragment.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotoSize;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.User;
import com.tumblr.jumblr.types.Video;
import com.tumblr.jumblr.types.VideoPost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DashboardFragment.OnFragmentInteractionListener {

    private final static int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 0;

    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;

    private boolean mIsFirstTime = true;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            (item) -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        if (mIsFirstTime || bnvMain.getSelectedItemId() != R.id.navigation_home) {
                            mIsFirstTime = false;
                            loadFragment(new DashboardFragment());
                        }
                        return true;
                    case R.id.navigation_search:
                        return true;
                    case R.id.navigation_notifications:
                        return true;
                    case R.id.navigation_profile:
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bnvMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bnvMain.setSelectedItemId(R.id.navigation_home);
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frl_main, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_logout:
                logout();
                return true;
            /*case R.id.menu_item_bd_backup:
                generateDbBackup();
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPrefs sp = new SharedPrefs(this);
        sp.remove(Constants.SharedKey.USER_ID);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void generateDbBackup() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST);

        } else {
            // Permission has already been granted
            doGenerateDbBackup();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    doGenerateDbBackup();
                }
                break;
            }
        }
    }

    protected void doGenerateDbBackup() {
        try {
            String bdName = getString(R.string.bd_name);

            //Get Path database
            File sd = Environment.getExternalStorageDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/" + bdName;
                String backupDBPath = "minitumblr.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    if (backupDB.exists()) {
                        backupDB.delete();
                    }
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
