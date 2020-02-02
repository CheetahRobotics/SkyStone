package org.firstinspires.ftc.teamcode.stateMachine;

public class TurningState extends DrivingState{

    public TurningState(StateMachine stateMachine,
                        Class<? extends StateBase> nextState,
                        boolean isLeftTurn){
        super(
                stateMachine,
                0.0,
                10000.0,
                nextState,
                isLeftTurn ? 0 : -.25 ,
                isLeftTurn ? .25 : 0
        );
    }
    @Override
    public void postEventsCallback() {
        super.postEventsCallback();

        addTelemetry("Back Left Motor Pos: ", "%d", leftDrive.getCurrentPosition());
        addTelemetry("Back Right Motor Pos: ", "%d", rightDrive.getCurrentPosition());
        addTelemetry("Front Left Motor Pos: ", "%d", leftBackDrive.getCurrentPosition());
        addTelemetry("Front Right Motor Pos: ", "%d", rightBackDrive.getCurrentPosition());


        if (Math.abs(leftDrive.getCurrentPosition()) > stateMachine.getRobotCalibration().countsFor90DegreeTurn ||
            Math.abs(rightDrive.getCurrentPosition()) > stateMachine.getRobotCalibration().countsFor90DegreeTurn) {
            addTelemetry("Left Motor Pos: ", "%d", leftDrive.getCurrentPosition());
            addTelemetry("Right Motor Pos: ", "%d", rightDrive.getCurrentPosition());
            addTelemetry("Left Motor Pos: ", "%d", leftBackDrive.getCurrentPosition());
            addTelemetry("Right Motor Pos: ", "%d", rightBackDrive.getCurrentPosition());
            stopMotors();
            sleep(1000);
            stateMachine.updateState(this.nextState);
        }
    }
}
