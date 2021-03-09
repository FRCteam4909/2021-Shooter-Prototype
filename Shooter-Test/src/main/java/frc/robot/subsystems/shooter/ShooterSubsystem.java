package frc.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{

    public double targetSpeed;
    public double currentSetSpeed = 5000; //In RPM

    //TODO test values
    public double SHOOTER_kF = 0.05; 
    public double SHOOTER_kP = 0.02; 
    public double SHOOTER_kI = 0.0;
    public double SHOOTER_kD = 0.0;

    WPI_TalonFX flyWheel1;
    WPI_TalonFX flyWheel2;
    
    public ShooterSubsystem(){
        flyWheel1 = new WPI_TalonFX(1);//Check Port Number
        flyWheel2 = new WPI_TalonFX(2);

        flyWheel1.config_kF(0, SHOOTER_kF);
        flyWheel1.config_kP(0, SHOOTER_kP);
        flyWheel1.config_kI(0, SHOOTER_kI);
        flyWheel1.config_kD(0, SHOOTER_kD);
        flyWheel1.set(TalonFXControlMode.Velocity, currentSetSpeed);

        flyWheel2.follow(flyWheel1);
        flyWheel1.configFactoryDefault();
        
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Target Velocity", flyWheel1.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("Target Speed", currentSetSpeed);
        SmartDashboard.getNumber("Target Speed", targetSpeed);


        if(targetSpeed != currentSetSpeed){
            currentSetSpeed = targetSpeed;
        }
    }

}