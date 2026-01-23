package frc.robot;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import org.littletonrobotics.junction.Logger;

public class Kraken implements MotorIO {
    private final TalonFX leader;
    private final TalonFX follower;
    private final TalonFXConfiguration config;

    private final StatusSignal<AngularVelocity> velocitySignal;
    private final StatusSignal<Current> supplyCurrentSignal;
    private final StatusSignal<Current> satorCurrentSignal;
    private final StatusSignal<Voltage> voltageSignal;

    private final VelocityVoltage control;
    private final Follower followerControl;






    public Kraken(int id) {
        leader = new TalonFX(id);
        follower = new TalonFX(1);
        followerControl = new Follower(id, MotorAlignmentValue.Opposed);

        config = new TalonFXConfiguration();
        config.CurrentLimits.SupplyCurrentLimit = 50;
        config.CurrentLimits.StatorCurrentLimit = 100;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;

        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        config.Voltage.PeakForwardVoltage = 12;
        config.Voltage.PeakReverseVoltage = -12;

        config.Audio.BeepOnConfig = true;
        config.Audio.BeepOnBoot = true;

        config.Slot0.kV = .125;


        leader.getConfigurator().apply(config);

        velocitySignal = leader.getVelocity();
        supplyCurrentSignal = leader.getSupplyCurrent();
        satorCurrentSignal = leader.getSupplyCurrent();
        voltageSignal = leader.getMotorVoltage();

        follower.setControl(followerControl);

        control = new VelocityVoltage(0);
        control.Slot = 0;
        control.EnableFOC = true;
        control.IgnoreHardwareLimits = false;
        control.LimitForwardMotion = false;
        control.LimitReverseMotion = false;

        leader.setControl(control);
    }
    @Override
    public void setVelocity(double velocity) {
        control.Velocity = velocity/60;
        leader.setControl(control);

    }

    @Override
    public void periodic() {
        voltageSignal.refresh();
        Logger.recordOutput("Velocity", -control.Velocity*60);
        Logger.recordOutput("Real Velocity", -leader.getVelocity().getValue().in(Units.RPM));
        Logger.recordOutput("Volts", leader.getMotorVoltage().getValue().in(Units.Volts));
        Logger.recordOutput("Amps Stator", leader.getStatorCurrent().getValue().in(Units.Amps));
        Logger.recordOutput("Amps Supply", leader.getSupplyCurrent().getValue().in(Units.Amps));
        Logger.recordOutput("Motor Temp 1", leader.getDeviceTemp().getValue().in(Units.Celsius));

        Logger.recordOutput("Volts 2", follower.getMotorVoltage().getValue().in(Units.Volts));
        Logger.recordOutput("Amps Stator 2", follower.getStatorCurrent().getValue().in(Units.Milliamps));
        Logger.recordOutput("Amps Supply 2", follower.getSupplyCurrent().getValue().in(Units.Milliamps));
        Logger.recordOutput("Motor Temp 2", follower.getDeviceTemp().getValue().in(Units.Celsius));





    }

}
