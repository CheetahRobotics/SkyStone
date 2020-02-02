package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.stateMachine.DrivingState;
import org.firstinspires.ftc.teamcode.stateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.stateMachine.Stop;

public class State1 extends DrivingState {
    public State1(StateMachine stateMachine) {
        // drive forward 45 inches:
        super(stateMachine,
                4.0,
                State2.class,
                -1);
    }
}

