package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.stateMachine.GamepadWrapper;
import org.firstinspires.ftc.teamcode.stateMachine.OpModeBase;
import org.firstinspires.ftc.teamcode.stateMachine.RobotCalibration;
import org.firstinspires.ftc.teamcode.stateMachine.StateMachine;

@Autonomous(name = "AutonomousOpMode", group = "Linear Opmode")
public class OpMode extends OpModeBase {
    private static final String TAG = "GamepadListenerBase";

    @Override
    public void initStateMachine(StateMachine stateMachine) {
        // If you want to use this code with a new robot,
        // Encoder counts for REV HD HEX Motor is 2240 per revolution
        // The wheel Circumfrence for each wheel is as follows
        //REV 60mm Traction wheel: 10.99cm, Tetrix Max All Terrain Tire: 15.70796cm
        // Tetrix Max 4" Wheel: 12.56cm
        // just update the RobotCallibration numbers on the next line:
        stateMachine.setRobotCalibration(new RobotCalibration(
                12.56,
                1307,
                1960));

        stateMachine.updateState(State1.class);    // Start at state number 1.
    }
}
