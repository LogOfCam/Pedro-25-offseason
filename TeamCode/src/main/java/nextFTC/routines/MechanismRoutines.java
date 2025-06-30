package nextFTC.routines;


import com.acmerobotics.dashboard.config.Config;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;


import nextFTC.subsystems.arm;
import nextFTC.subsystems.claw;
public class MechanismRoutines {
    private MechanismRoutines() {
    }
        public static Command place () {
            return new SequentialGroup(
                    arm.INSTANCE.preparePlace()
            );
        }
    }
