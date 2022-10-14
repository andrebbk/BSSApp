package com.example.bssapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.example.bssapp.ui.classes.ClassListItem;
import com.example.bssapp.ui.professors.ProfessorListItem;
import com.example.bssapp.ui.students.StudentListItem;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bssapp.databinding.ActivityMenuBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MenuActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private DrawerLayout drawer;

    public ImageView calendarRangeFilter;
    public MenuItem buttonLoadBackUp;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.bssapp.databinding.ActivityMenuBinding binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);
        calendarRangeFilter = findViewById(R.id.calendarRangeFilter);

        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.theme_primary_color));
        window.setStatusBarContrastEnforced(true);

        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_calendar, R.id.nav_students, R.id.nav_classes, R.id.nav_professors, R.id.nav_newStudent, R.id.nav_editStudent, R.id.nav_newProfessor,
                R.id.nav_editProfessor, R.id.nav_addClass, R.id.nav_data_managment, R.id.nav_class, R.id.nav_AddClassStudent, R.id.nav_statistic)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if(navDestination.getId() == R.id.nav_classes)
                calendarRangeFilter.setVisibility(View.VISIBLE);
            else
                calendarRangeFilter.setVisibility(View.GONE);

            //Button backup
            /*if (buttonLoadBackUp != null) {
                if(navDestination.getId() == R.id.nav_students)
                    buttonLoadBackUp.setVisible(true);
                else
                    buttonLoadBackUp.setVisible(false);
            }*/
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        if (menu != null) {
            buttonLoadBackUp = menu.findItem(R.id.action_loadBackup);
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //if (id == R.id.nav_classes) {
        //}

        //Clear all stacked fragments
        navController.popBackStack();

        //navigate to fragment
        navController.navigate(id);

        //Close slide navigation menu
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_loadBackup) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
            builder.setMessage(getResources().getString(R.string.load_backup_validation))
                    .setPositiveButton("Sim", (dialog, id12) -> UtilsClass.LoadStudentsBackupData())
                    .setNegativeButton("Não", (dialog, id1) -> {});

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeFragment() {
        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        //navController.popBackStack(R.id.nav_students, true);
        navController.navigate(R.id.nav_newStudent);
    }

    public void changeToEditStudentFragment(StudentListItem student) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SelectedStudent", student);

        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        //navController.popBackStack(R.id.nav_students, true);
        navController.navigate(R.id.nav_editStudent, bundle);
    }

    public void changeToStudentsFromEditFragment() {
        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        //navController.popBackStack(R.id.nav_editStudent, true);
        navController.navigate(R.id.nav_students);
    }

    public void changeToNewProfessor() {
        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        //navController.popBackStack(R.id.nav_professors, true);
        navController.navigate(R.id.nav_newProfessor);
    }

    public void changeToEditProfessorFragment(ProfessorListItem professor) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SelectedProfessor", professor);

        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        //navController.popBackStack(R.id.nav_professors, true);
        navController.navigate(R.id.nav_editProfessor, bundle);
    }

    public void changeToProfessorsFromEditFragment() {
        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        //navController.popBackStack(R.id.nav_editProfessor, true);
        navController.navigate(R.id.nav_professors);
    }

    public void changeToAddClass(Calendar newClassDate) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("newClassDate", newClassDate);

        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        //navController.popBackStack(R.id.nav_classes, true);
        navController.navigate(R.id.nav_addClass, bundle);
    }

    public void ShowSnackBar(String msg)
    {
        Snackbar.make(findViewById(R.id.nav_host_fragment_content_menu), msg, Snackbar.LENGTH_LONG)
                //.setAction("Submit", mOnClickListener)
                .setBackgroundTint(Color.YELLOW)
                .setTextColor(Color.BLUE)
                .show();
    }

    public void changeToClassFragment(ClassListItem classItem) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SelectedClass", classItem);

        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        //navController.popBackStack(R.id.nav_classes, true);
        navController.navigate(R.id.nav_class, bundle);
    }

    public void changeToEditClassFragment(ClassListItem classItem) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SelectedClass", classItem);

        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        //navController.popBackStack(R.id.nav_class, true);
        navController.navigate(R.id.nav_editClass, bundle);
    }

    public void changeToAddStudentClassFragment(ClassListItem classItem) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SelectedClass", classItem);

        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        //navController.popBackStack(R.id.nav_class, true);
        navController.navigate(R.id.nav_AddClassStudent, bundle);
    }

    public void changeToClassesFragment() {
        navController.navigate(R.id.nav_classes);
    }

    public void changeToStudentsFragment() {
        navController.navigate(R.id.nav_students);
    }

    public void changeToProfessorsFragment() {
        navController.navigate(R.id.nav_professors);
    }

    public void changeToCalendarFragment(Calendar classDate) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ClassDate", classDate);

        //Drawable menu works with a navigation controller that functions like a stack
        //Use this first line to clear the stack and then navigate to destination view
        //navController.popBackStack(R.id.nav_classes, true);
        navController.navigate(R.id.nav_calendar, bundle);
    }

    public void changeToCalendarFragmentNoArgs() {
        navController.navigate(R.id.nav_calendar);
    }
}