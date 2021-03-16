package frc.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystemOLD extends SubsystemBase{

    public static final double FINAL_SPEED = 5000; //In RPM

    //TODO test value
    public double SHOOTER_kP = 0.02; 
    public double SHOOTER_kI = 0.0;
    public double SHOOTER_kD = 0.0;

    WPI_TalonFX flyWheel1;
    WPI_TalonFX flyWheel2;
    PIDController shooterPID;

    double currentSpeed;

    
    public ShooterSubsystemOLD(){
        flyWheel1 = new WPI_TalonFX(1);//Check Port Number
        flyWheel2 = new WPI_TalonFX(2);
        shooterPID = new PIDController(SHOOTER_kP, SHOOTER_kI, SHOOTER_kD);

        flyWheel2.follow(flyWheel1);
        shooterPID.setSetpoint(FINAL_SPEED);

        flyWheel1.configFactoryDefault();
        
    }

    @Override
    public void periodic() { //Runs every 20ms
        if(shooterPID.atSetpoint()){ //Are we at 5000 RPM?
            flyWheel1.set(currentSpeed);
        } else {
            currentSpeed = shooterPID.calculate(flyWheel1.getSelectedSensorVelocity());
            flyWheel1.set(currentSpeed);

        }
    }

}