package com.example.bssapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import com.example.bssapp.ui.students.StudentListItem;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bssapp.databinding.ActivityMenuBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.bssapp.databinding.ActivityMenuBinding binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_calendar, R.id.nav_students, R.id.nav_classes, R.id.nav_professors, R.id.nav_newStudent, R.id.nav_editStudent)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void changeFragment() {
        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        navController.popBackStack(R.id.nav_students, true);
        navController.navigate(R.id.nav_newStudent);
    }

    public void changeToEditStudentFragment(StudentListItem student) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SelectedStudent", (Serializable) student);

        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        navController.popBackStack(R.id.nav_students, true);
        navController.navigate(R.id.nav_editStudent, bundle);
    }

    public void ShowSnackBar(String msg)
    {
        Snackbar.make(findViewById(R.id.nav_host_fragment_content_menu), msg, Snackbar.LENGTH_LONG)
                //.setAction("Submit", mOnClickListener)
                .setBackgroundTint(Color.YELLOW)
                .setTextColor(Color.BLUE)
                .show();
    }
}