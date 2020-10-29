package dk.nordfalk.robotopgave.ui.s3_execute

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.nordfalk.robotopgave.model.Model
import dk.nordfalk.robotopgave.model.Position
import dk.nordfalk.robotopgave.model.Robot
import dk.nordfalk.robotopgave.model.Rum

class ExecuteRobotViewModel : ViewModel() {

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
}