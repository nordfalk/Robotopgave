package dk.nordfalk.robotopgave

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var robotlyd: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_s1_home, R.id.navigation_s2_nyt_rum, R.id.navigation_s3_execute
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        if (savedInstanceState==null) {
            animationView.post {
                animationView.animate().setDuration(7000).setInterpolator(LinearInterpolator())
                    .translationXBy(animationView.width + container.width + 0f)
            }
            // https://freesound.org/people/dotY21/sounds/345973/
            robotlyd = MediaPlayer.create(this, R.raw.robotlyd_lang)
            robotlyd!!.start()
            robotlyd!!.setOnCompletionListener { animationView.visibility = View.GONE }
        }
    }
}