package utilities;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

import java.sql.Timestamp;

 public class MyReport {
     private MyReport(){}

    public static String startStep(String title, String msg) {
        var uuid = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());

        MyLogger.info(title, msg);
        Allure.getLifecycle().startStep(uuid, new StepResult().setName(msg).setStatus(Status.FAILED));

        return uuid;
    }

    public static void updateStepStatus(String uuid, Status status) {
        Allure.getLifecycle().updateStep(uuid, step -> step.setStatus(status));
    }

    public static void stopStep(String uuid) {
        Allure.getLifecycle().stopStep(uuid);
    }

    public static void updateStepToBePassed(String uuid) {
        updateStepStatus(uuid, Status.PASSED);
        stopStep(uuid);
    }

    public static void attach(String title, String att) {
        MyLogger.info(title, att);
        Allure.addAttachment("Response", att);
    }
}
