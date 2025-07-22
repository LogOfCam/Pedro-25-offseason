package nextFTC.routines;


import com.acmerobotics.dashboard.config.Config;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;


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
                claw.INSTANCE.close()
        );
    }
}
