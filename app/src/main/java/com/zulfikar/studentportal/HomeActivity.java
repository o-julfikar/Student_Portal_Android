package com.zulfikar.studentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationBarView;
import com.zulfikar.studentportal.account.ProfileFragment;
import com.zulfikar.studentportal.account.ProfileHeaderFragment;
import com.zulfikar.studentportal.account.SessionManager;
import com.zulfikar.studentportal.forum.ForumFragment;
import com.zulfikar.studentportal.forum.ForumHeaderFragment;
import com.zulfikar.studentportal.notification.NotificationFragment;
import com.zulfikar.studentportal.notification.NotificationHeaderFragment;
import com.zulfikar.studentportal.review.ReviewFragment;
import com.zulfikar.studentportal.review.ReviewHeaderFragment;
import com.zulfikar.studentportal.search.SearchFragment;
import com.zulfikar.studentportal.search.SearchHeaderFragment;
import com.zulfikar.studentportal.swap.SwapFragment;
import com.zulfikar.studentportal.swap.SwapHeaderFragment;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView spBottomNavView;
    ShapeableImageView imgUser;
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SessionManager.auth(HomeActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        spBottomNavView = findViewById(R.id.spBottomNavView);
        imgUser = findViewById(R.id.imgUser);
        imgLogo = findViewById(R.id.imgLogo);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new ForumFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.headerFragmentContainer, new ForumHeaderFragment()).commit();

        spBottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment mainFragment = null, headerFragment = null;
                if (item.getItemId() == R.id.bniForum) {
                    mainFragment = new ForumFragment();
                    headerFragment = new ForumHeaderFragment();
                } else if (item.getItemId() == R.id.bniReview) {
                    mainFragment = new ReviewFragment();
                    headerFragment = new ReviewHeaderFragment();
                } else if (item.getItemId() == R.id.bniSwap) {
                    mainFragment = new SwapFragment();
                    headerFragment = new SwapHeaderFragment();
                } else if (item.getItemId() == R.id.bniNotifications) {
                    mainFragment = new NotificationFragment();
                    headerFragment = new NotificationHeaderFragment();
                } else if (item.getItemId() == R.id.bniSearch) {
                    mainFragment = new SearchFragment();
                    headerFragment = new SearchHeaderFragment();
                }
                if (mainFragment == null) return true;
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, mainFragment).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.headerFragmentContainer, headerFragment).commit();
                return true;
            }
        });

        imgUser.setOnClickListener(v -> {
            ProfileHeaderFragment profileHeaderFragment = new ProfileHeaderFragment();
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.headerFragmentContainer,
                    profileHeaderFragment).commit();
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.mainFragmentContainer,
                    new ProfileFragment(-1, profileHeaderFragment)).commit();
        });

    }
}