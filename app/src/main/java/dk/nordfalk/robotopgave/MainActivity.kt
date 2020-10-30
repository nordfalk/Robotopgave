package dk.nordfalk.robotopgave

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import dk.nordfalk.robotopgave.model.Model
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


        if (!Model.instantieret()) {
            // Hent tidligere data persisteret som JSON i Prefs
            val gson = Gson()
            val json = PreferenceManager.getDefaultSharedPreferences(this).getString("model", null)
            if (json!=null) {
                val model = gson.fromJson(json, Model::class.java)
                Model.get().setFraModel(model!!);
            }

        }

        if (savedInstanceState==null) {
            animationView.post { // forsink til UI er klar og bredde er kendt
                animationView.animate().setDuration(7000).setInterpolator(LinearInterpolator())
                    .translationXBy(animationView.width + container.width + 0f)
                startRobotlyd()
            }
        }
    }

    private fun startRobotlyd() {
        volumeControlStream = AudioManager.STREAM_MUSIC

        // Hvis lyden er lavere end 1/5 af fuld lydstyrke så skru volumen og til 1/5 af fuld lydstyrke
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val fuldStyrke = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val aktuelStyrke = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        if (aktuelStyrke < fuldStyrke / 5) {
            audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                fuldStyrke / 5,
                AudioManager.FLAG_SHOW_UI
            )
        }
        // https://freesound.org/people/dotY21/sounds/345973/
        robotlyd = MediaPlayer.create(this, R.raw.robotlyd_lang)
        robotlyd!!.start()
        robotlyd!!.setOnCompletionListener { animationView.visibility = View.GONE }
    }

    override fun onStop() {
        super.onStop()
        // Gem model som JSON i Prefs
        val gson = Gson()
        val json = gson.toJson(Model.get())
        println("JSON streng = $json")
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("model", json).apply()

        val lyd = robotlyd
        if (lyd!=null && lyd.isPlaying) lyd.stop()
    }
}