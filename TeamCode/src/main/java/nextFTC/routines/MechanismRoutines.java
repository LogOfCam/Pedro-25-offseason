package nextFTC.routines;


import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;


import nextFTC.subsystems.arm;
public class MechanismRoutines {
    private MechanismRoutines() {
    }
        public static Command preparePlace () {
            return new SequentialGroup(
                    arm.INSTANCE.notPushing()
            );
        }
}
