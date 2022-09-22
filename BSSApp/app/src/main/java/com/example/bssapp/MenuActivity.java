package com.example.bssapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

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

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.bssapp.databinding.ActivityMenuBinding binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);

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
                R.id.nav_editProfessor, R.id.nav_addClass, R.id.nav_data_managment, R.id.nav_class, R.id.nav_AddClassStudent)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_students) {
            // DO your stuff
        }*/

        //Clear all stacked fragments
        navController.popBackStack();

        //navigate to fragment
        navController.navigate(id);

        //Close slide navigation menu
        drawer.closeDrawer(GravityCompat.START);

        return true;
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
}