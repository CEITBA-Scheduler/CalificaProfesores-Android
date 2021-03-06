package com.gnd.calificaprofesores;

import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;


import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.AdapterClassFrontPage.AdapterClassFrontPage;

import com.gnd.calificaprofesores.IntentsManager.IntentCourseManager;
import com.gnd.calificaprofesores.MenuManager.MenuManager;

/*** Class front page: aqui adminsitramos la pagina principal de un curso. Tanto las diversas
 * opiniones como el puntaje ***/

/*** Se divide por el momento en cuatro tabs la informacion ***/
/* Vista general */
/* Opioniones */
/* Tu opinión */

/* Se mostrara siempre el puntaje en estrellas del curso */

/** Activity asociado: activity_class_front_page_v2.xml **/

public class ActivityClassFrontPageV2 extends AppCompatActivity {

    PagerSlidingTabStrip tabs;
    ViewPager pager;

    private AdapterClassFrontPage adapter;

    private static String CourseName;
    private static String CourseId;

    private LinearLayout toolbar;
    private IntentCourseManager intent;

    private MenuManager menuManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_front_page_v2);

        toolbar = findViewById(R.id.Toolbar);
        // CourseName
        // CourseId
        intent = new IntentCourseManager(getIntent());

        CourseName = intent.GetCourseName();
        CourseId = intent.GetCourseId();


        TextView title = findViewById(R.id.TextBuscarCurso);
        title.setText(CourseName);

        TextView uniDetail = findViewById(R.id.UniversityText);
        uniDetail.setText(intent.getUniName());

        /// Tabs and pager managment

        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);
        //setSupportActionBar(toolbar);

        adapter = new AdapterClassFrontPage(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(2);
        tabs.setViewPager(pager);

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));

        Toast.makeText(this, "Compartí tu experiencia en la materia! Por ejemplo, cuantos parciales tiene, ¿Tiene Tp? ¿Qué parcial te pareció más complicado?", Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onResume() {
        super.onResume();
        menuManager.closeDrawer();
    }
}
