package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.stateMachine.DrivingState;
import org.firstinspires.ftc.teamcode.stateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.stateMachine.TurningState;

public class State5 extends DrivingState {
    public State5(StateMachine stateMachine) {
        super(stateMachine,3.84
                ,
                State6.class,
                -0.5);
    }
}

