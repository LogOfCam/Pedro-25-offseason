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
        public static Command place1Position () {
            return new SequentialGroup(
                    arm.INSTANCE.armPlace(),
                    claw.INSTANCE.open()
            );
        }
            public static Command clawOpen () {
                return new SequentialGroup(
                        claw.INSTANCE.open()
                );
            }
                public static Command pickupSpecimen() {
                    return new ParallelGroup(
                            arm.INSTANCE.Pickup(),
                            claw.INSTANCE.open()
                    );
        }
    public static Command clawClose() {
        return new SequentialGroup(
                new Delay(TimeSpan.fromSec(0.05)),
                claw.INSTANCE.close()
        );
    }
    public static Command preparePlace2 () {
        return new SequentialGroup(
                claw.INSTANCE.close(),
                arm.INSTANCE.ramp()
        );
    }
    public static Command Place2 () {
        return new SequentialGroup(
                arm.INSTANCE.armPlace()
        );
    }
}
