package nextFTC.routines;


import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;


import nextFTC.subsystems.launch;
import nextFTC.subsystems.claw;
public class MechanismRoutines {
    private MechanismRoutines() {
    }
        public static Command preparePlace () {
            return new SequentialGroup(
                    claw.INSTANCE.close()
            );
        }
}
