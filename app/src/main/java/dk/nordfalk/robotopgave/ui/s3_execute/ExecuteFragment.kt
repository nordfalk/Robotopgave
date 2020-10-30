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
import dk.nordfalk.robotopgave.model.Retning
import dk.nordfalk.robotopgave.model.Rum
import kotlinx.android.synthetic.main.s3_execute_frag.*
import kotlinx.android.synthetic.main.s3_execute_frag.view.*

class ExecuteFragment : Fragment() {

    private lateinit var vm: ExecuteRobotViewModel
    private lateinit var playerRobotfejl: MediaPlayer
    private lateinit var playerRobotFrem: MediaPlayer

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

        root.overskriftRum.text = vm.rum.toString()
        root.imageButton1Skridt.setOnClickListener {
            vm.kør1Skridt()
        }
        root.rumView.setRum(vm.rum)

        // https://freesound.org/people/dotY21/sounds/333174/
        playerRobotFrem = MediaPlayer.create(context, R.raw.robotlyd)
        playerRobotfejl = MediaPlayer.create(context, R.raw.dyt)

        root.imageButtonKoer.setOnClickListener {
            vm.kørAlleSkridt()
        }

        vm.robottilstand.observe(viewLifecycleOwner, Observer {
            root.textViewTilstand.text = it
            root.textViewInstuksfejl.text = vm.robot.senesteInstruksfejl
            root.textViewProgram.text = vm.program
            root.imageButton1Skridt.isEnabled = !vm.kører && vm.program.length > 0
            root.imageButtonKoer.isEnabled = !vm.kører && vm.program.length > 0

            flytRobot(true)
            if (vm.robot.senesteInstruksfejl != null) playerRobotfejl.start()
            else if (vm.robot.senesteInstruks=='F') playerRobotFrem.start()
        })

        root.post {
            flytRobot(false) // Flyt robotten hen på sin aktuelle placering når RumView er renderet og kender sin størrelse
        }
        return root
    }

    private fun flytRobot(animeret: Boolean) {
        if (rumView.width==0) return // Views er ikke klar
        val x = rumView.getSkærmkoordinat(vm.robot.position.x)
        val y = rumView.getSkærmkoordinat(vm.robot.position.y)
        val rot = (vm.robot.position.retning.ordinal - Retning.E.ordinal + 4) * 360.0f / 4
        println("vm.robot.position = ${vm.robot.position}")

        animationViewContainer.animate().translationX(x).translationY(y).rotation(rot).setDuration(if (animeret) 1000 else 0)
        if (animeret) animationView.playAnimation()
    }
}