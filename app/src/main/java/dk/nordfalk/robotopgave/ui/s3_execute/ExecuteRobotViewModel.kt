package dk.nordfalk.robotopgave.ui.s3_execute

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.nordfalk.robotopgave.model.Model
import dk.nordfalk.robotopgave.model.Position
import dk.nordfalk.robotopgave.model.Robot
import dk.nordfalk.robotopgave.model.Rum

class ExecuteRobotViewModel : ViewModel() {

    var kører = false
    var robot: Robot
    var rum: Rum
    var program: String

    private val _robottilstand = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val robottilstand: LiveData<String> = _robottilstand

    init {
        if (Model.get().valgtRum.value == null || Model.get().valgtProgram.value.isNullOrEmpty()) {
            System.err.println("Hmm... rum == null || program.isNullOrEmpty()")
            rum = Rum.ILLEGAL;
            program = ""
        } else {
            rum = Model.get().valgtRum.value!!
            program = Model.get().valgtProgram.value!!
        }
        robot = Robot(rum)
        _robottilstand.value = robot.report
    }


    fun kør1Skridt() {
        if (program.length==0) return;
        val skridt = program.substring(0, 1)
        program = program.substring(1)
        robot.execute(skridt)
        _robottilstand.value = robot.report
    }

    val handler = Handler(Looper.getMainLooper());
    val koer1skridtRunnable = object : Runnable {
        override fun run() {
            kører = true
            kør1Skridt()
            if (program.length > 0) { //  && isVisible
                handler.postDelayed(this, 3000)
            } else {
                kører = false
            }
        }
    }

    fun kørAlleSkridt() {
        koer1skridtRunnable.run()
    }

    override fun onCleared() { // Stop kørsel hvis man afslutter skærmbilledet
        super.onCleared()
        handler.removeCallbacks(koer1skridtRunnable)
    }
}