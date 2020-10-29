package dk.nordfalk.robotopgave.ui.s3_execute

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dk.nordfalk.robotopgave.R
import dk.nordfalk.robotopgave.model.Rum
import kotlinx.android.synthetic.main.s3_execute_frag.view.*

class ExecuteFragment : Fragment() {

    private lateinit var vm: ExecuteRobotViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = ViewModelProvider(this).get(ExecuteRobotViewModel::class.java)

        if (vm.rum == Rum.ILLEGAL) {
            return inflater.inflate(
                R.layout.s3_execute_vaelg_foerst_rum_og_program,
                container,
                false
            )
        }

        val root = inflater.inflate(R.layout.s3_execute_frag, container, false)
        vm.robottilstand.observe(viewLifecycleOwner, Observer {
            root.textViewTilstand.text = it
            root.textViewProgram.text = vm.program
            root.imageButton1Skridt.isEnabled = !vm.kører && vm.program.length > 0
            root.imageButtonKoer.isEnabled = !vm.kører && vm.program.length > 0
        })

        root.overskriftRum.text = vm.rum.toString()
        root.imageButton1Skridt.setOnClickListener {
            vm.kør1Skridt()
        }

        val robotlyd = MediaPlayer.create(context, R.raw.robotlyd)

        root.imageButtonKoer.setOnClickListener {

            val koer1skridt = object : Runnable {
                val handler = Handler(Looper.getMainLooper());
                override fun run() {
                    vm.kører = true
                    vm.kør1Skridt()
                    if (vm.program.length > 0 && isVisible) {
                        handler.postDelayed(this, 200)
                        // https://freesound.org/people/dotY21/sounds/333174/
                        robotlyd.start()
                    } else {
                        vm.kører = false
                    }
                }
            }
            koer1skridt.run()
        }



        return root
    }
}