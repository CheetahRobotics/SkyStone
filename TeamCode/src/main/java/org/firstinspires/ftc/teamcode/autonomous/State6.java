package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.stateMachine.DrivingState;
import org.firstinspires.ftc.teamcode.stateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.stateMachine.Stop;

public class State6 extends DrivingState {

    public State6(StateMachine stateMachine) {
        super(stateMachine,
                1.0,
                6.75,
                State7.class,
                -0.5,
                0.5);
        // placing the flag
    }
}

