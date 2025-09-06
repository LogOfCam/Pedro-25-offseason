package nextFTC.routines;


import com.acmerobotics.dashboard.config.Config;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;


import nextFTC.subsystems.arm;
import nextFTC.subsystems.claw;
public class MechanismRoutines {
    private MechanismRoutines() {
    }
        public static Command preparePlace () {
            return new SequentialGroup(
                    claw.INSTANCE.close(),
                    arm.INSTANCE.preparePlace()
            );
        }
}
