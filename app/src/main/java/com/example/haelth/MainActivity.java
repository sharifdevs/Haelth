package com.example.haelth;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.context = getApplicationContext();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // NavView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.getMenu().getItem(0).setChecked(true);

        // Login
        if (!App.isLoggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent); // only if connects it goes to next line
        }

        // FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sendSMS
                String msg = "Hi! This message is sent by haelth app... \n I'm in emergency! \n " +
                        "This is my location: https://www.google.com/maps/@" + App.patient.getLocation() + ",12z";
                SendSMS sendSMS = new SendSMS("+989124382006",msg);
                sendSMS.sendMySMS("+989124382006",msg);
                Snackbar.make(view, "Your location is sent to close people!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavHeader();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAndRemoveTask();
            }
            finish();
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_logout: {
                googleSignOut();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch(id) {
            case R.id.nav_dashboard:
                setTitle("Dashboard");
                DashboardFragment dashboard = new DashboardFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment, dashboard).commit();
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setImageResource(R.drawable.ic_fab_emergency);
                break;
            case R.id.nav_profile:
                setTitle("My Profile");
                ProfileFragment profile = new ProfileFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment, profile).commit();
                break;
            case R.id.nav_calendar:
                setTitle("Calendar");
                CalendarFragment calendar = new CalendarFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment, calendar).commit();
                break;
            case R.id.nav_reminders:
                setTitle("Reminders");
                RemindersFragment reminders = new RemindersFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment, reminders).commit();
                break;
            case R.id.nav_messages:
                setTitle("Messages");
                MessagesFragment messagesFragment = new MessagesFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment, messagesFragment).commit();
                break;
            case R.id.nav_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;}
            case R.id.nav_about: {
                Intent intent = new Intent(this, AboutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;}
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navHeaderTitle = (TextView)headerView.findViewById(R.id.navHeaderTitle);
        TextView navHeaderSubtitle = (TextView)headerView.findViewById(R.id.navHeaderSubtitle);
        CircleImageView navHeaderImage = (CircleImageView)headerView.findViewById(R.id.navHeaderImage);

        if(App.accountType == App.AccountType.GOOGLE) {
            try {
                navHeaderTitle.setText(App.googleSignInAccount.getDisplayName());
                navHeaderSubtitle.setText(App.googleSignInAccount.getEmail());
                Picasso.get().load(App.googleSignInAccount.getPhotoUrl()).into(navHeaderImage);

                App.patient.setProfileImageUrl(App.googleSignInAccount.getPhotoUrl().toString());
            } catch (Exception e) {
                e.printStackTrace();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else if(App.accountType == App.AccountType.OFFLINE) {
            navHeaderTitle.setText("Guest");
            navHeaderSubtitle.setText("no email");
            navHeaderImage.setImageResource(R.mipmap.ic_launcher);
        }
    }

    public void googleSignOut() {
        App.mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        googleRevokeAccess();
                    }
                });
    }


    // for fully deleting account from app
    private void googleRevokeAccess() {
        App.mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        App.isLoggedIn = false;
                    }
                });
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
