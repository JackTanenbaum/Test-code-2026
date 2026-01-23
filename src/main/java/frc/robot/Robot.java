// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.StadiaController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends LoggedRobot{
    private MotorIO motor1;
    private MotorIO motor2;

    private final XboxController controller;
//    private final StadiaController controller;
    public Robot() {
        Logger.recordMetadata("ProjectName", "Shooter test code");
        if(isReal()){
            Logger.addDataReceiver(new NT4Publisher());
            Logger.addDataReceiver(new WPILOGWriter());
        }else{
            Logger.addDataReceiver(new NT4Publisher());
        }
        Logger.start();

        controller = new XboxController(0);
//        controller = new StadiaController(1);

            motor1 = new Kraken(0);
//            motor2 = new Kraken(1);
//            motor1 = new Neo(1);
//            motor2 = new Neo(2);


        SmartDashboard.setDefaultNumber("speed", 0);
//        SmartDashboard.setDefaultNumber("Bottom speed", 0);
    }
    
    
    @Override
    public void robotPeriodic() {
        if(controller.getAButton()) {
            motor1.setVelocity(SmartDashboard.getNumber("Top speed", 0));
//            motor2.setVelocity(SmartDashboard.getNumber("Bottom speed", 0));
        }else{
            motor1.setVelocity(0);
//            motor2.setVelocity(0);
        }
        motor1.periodic();
//        motor2.periodic();
    }


    @Override
    public void autonomousInit() {}
    
    
    @Override
    public void autonomousPeriodic() {}
    
    
    @Override
    public void teleopInit() {}
    
    
    @Override
    public void teleopPeriodic() {}
    
    
    @Override
    public void disabledInit() {}
    
    
    @Override
    public void disabledPeriodic() {}
    
    
    @Override
    public void testInit() {}
    
    
    @Override
    public void testPeriodic() {}
    
    
    @Override
    public void simulationInit() {}
    
    
    @Override
    public void simulationPeriodic() {}
}
