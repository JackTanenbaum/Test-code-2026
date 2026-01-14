package frc.robot;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkParameters;
import edu.wpi.first.math.controller.PIDController;

public class Neo implements MotorIO{
    private final SparkMax motor;
    private final SparkMaxConfig config;
    private final SparkClosedLoopController control;

    public Neo(int id) {
        motor = new SparkMax(id, SparkMax.MotorType.kBrushless);
        control = motor.getClosedLoopController();
        config = new SparkMaxConfig();
        config.smartCurrentLimit(40);
        config.idleMode(SparkMaxConfig.IdleMode.kBrake);
        config.inverted(false);
        config.voltageCompensation(12);
        config.closedLoop.feedForward.kV(.125);
        motor.configure(config , ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    @Override
    public void setVelocity(double velocity) {
        double RMP = velocity/60;
        control.setSetpoint(RMP,SparkMax.ControlType.kVelocity);
    }

    @Override
    public void periodic() {

    }
}
