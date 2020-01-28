package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.stateMachine.DrivingState;
import org.firstinspires.ftc.teamcode.stateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.stateMachine.Stop;

public class State1 extends DrivingState {
    public State1(StateMachine stateMachine) {
        // drive forward 45 inches:
        super(stateMachine,
                0.0,
                28.5,
                Stop.class,
                -0.5,
                0.5);
    }
}

