package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.stateMachine.DrivingState;
import org.firstinspires.ftc.teamcode.stateMachine.StateMachine;

public class State2 extends DrivingState {
    public State2(StateMachine stateMachine) {
        super(stateMachine, 1.0,
                29.6875,
                State3.class,
                0.5,
                0.5);
    }
}
