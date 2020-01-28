package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.stateMachine.DrivingState;
import org.firstinspires.ftc.teamcode.stateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.stateMachine.TurningState;

public class State3 extends TurningState {
    public State3(StateMachine stateMachine) {
        super(stateMachine,
                State6.class,
                false);
    }
}

