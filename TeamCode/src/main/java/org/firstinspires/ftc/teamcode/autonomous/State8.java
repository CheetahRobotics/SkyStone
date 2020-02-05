package org.firstinspires.ftc.teamcode.autonomous;
import org.firstinspires.ftc.teamcode.stateMachine.DrivingState;
import org.firstinspires.ftc.teamcode.stateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.stateMachine.Stop;

public class State8 extends DrivingState{
    public State8(StateMachine stateMachine) {
        super(stateMachine, 1.0,
                36.25,
                Stop.class,
                0.5,
                0.5);
    }
}
