package com.appsmaggys.caear.appfuentedesodamaggys

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosUsuario
import com.appsmaggys.caear.appfuentedesodamaggys.Fragments.MenuFragment
import com.appsmaggys.caear.appfuentedesodamaggys.Fragments.PedidosFragment
import com.appsmaggys.caear.appfuentedesodamaggys.Fragments.PedirFragment
import com.appsmaggys.caear.appfuentedesodamaggys.Fragments.PerfilFragment
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_bottom.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import kotlinx.android.synthetic.main.content_menu.*

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var pedirFragment: PedirFragment//Primer Caso para crear un fragment
    lateinit var pedidosFragment: PedidosFragment
    lateinit var perfilFragment: PerfilFragment
    lateinit var menuFragment: MenuFragment
    lateinit var referenciaUsuarios:DatabaseReference
    lateinit var usuariosList:MutableList<DatosUsuario>
    lateinit var tituloToolbar:TextView



    lateinit var auth: FirebaseAuth

    private val mOnNavigationItemSelectedListener= BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_home -> {
                irFragment(menuFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                irFragment(pedirFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                irFragment(pedidosFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_perfil -> {
                irFragment(perfilFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        auth=FirebaseAuth.getInstance()
        var myuserobj = auth.currentUser

        val nombre = myuserobj?.displayName.toString()
        toolbar.setTitle(nombre)
        setSupportActionBar(toolbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.desactivarMovimiento()





            referenciaUsuarios = FirebaseDatabase.getInstance().getReference("Users")
            usuariosList = mutableListOf()

            referenciaUsuarios.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        for (h in p0.children) {
                            val user = FirebaseAuth.getInstance().uid
                            val cliente = h.getValue(DatosUsuario::class.java)?.uid
                            val hero = h.getValue(DatosUsuario::class.java)
                            if (cliente == user) {
                                usuariosList.add(hero!!)
                            }
                        }
                        for (h in usuariosList) {
                           toolbar.subtitle = "Pts:"+h.puntos

                        }
                    }
                }
            })
            pedirFragment = PedirFragment.newInstance()//segundo Paso para crear un fragment
            pedidosFragment = PedidosFragment.newInstance()
            perfilFragment = PerfilFragment.newInstance()
            menuFragment = MenuFragment.newInstance()

            supportFragmentManager
            .beginTransaction()
            .replace(R.id.contenedorFragments,menuFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

            val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
            )
            drawer_layout.addDrawerListener(toggle)
            toggle.syncState()

            nav_view.setNavigationItemSelectedListener(this)
    }

    private fun irFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contenedorFragments,fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_settings ->  {
                var firse = FirebaseAuth.getInstance()
                LoginManager.getInstance().logOut()
                firse.signOut()
                finish()
                startActivity(Intent(this,RegisterWhiteFacebook::class.java))
            }

        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_perfil -> {
                irFragment(perfilFragment)
                navigation.selectedItemId=R.id.navigation_perfil
     }
            R.id.nav_camera -> {
                irFragment(menuFragment)

                navigation.selectedItemId=R.id.navigation_home
            }
            R.id.nav_gallery -> {
                irFragment(pedirFragment)

                navigation.selectedItemId=R.id.navigation_dashboard
            }
            R.id.nav_slideshow -> {
                irFragment(pedidosFragment)

                navigation.selectedItemId=R.id.navigation_notifications
            }
            R.id.nav_share -> {}
            R.id.nav_send -> {}
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun BottomNavigationView.desactivarMovimiento() {
        val menuView = getChildAt(0) as BottomNavigationMenuView

        menuView.javaClass.getDeclaredField("mShiftingMode").apply {
            isAccessible = true
            setBoolean(menuView, false)
            isAccessible = false
        }

        @SuppressLint("RestrictedApi")
        for (i in 0 until menuView.childCount) {
            (menuView.getChildAt(i) as BottomNavigationItemView).apply {
                setShiftingMode(false)
                setChecked(false)
            }
        }
    }
}
