package org.afeka.fi.backend.pojo.commonstructure;

public enum Status {
    pending,success,failed,taskReturnNextYesIncorrectFormat,taskReturnNextNoIncorrectFormat,
    nextYesIncorrectFormat,nextNoIncorrectFormat,FiPathLoopError,stepNotExist;

    public static String getDescription(String status) {
        String description;
        if (status.equals(Status.taskReturnNextYesIncorrectFormat.name()))
            description= "The next step if the task  success not in Number format";
        else if (status.equals(Status.taskReturnNextYesIncorrectFormat.name()))
            description= "The next step if the task fail not in Number format";
        else if (status.equals(Status.nextYesIncorrectFormat.name()))
            description="The next step if the test success not in Number format";
        else if (status.equals(Status.nextNoIncorrectFormat.name()))
            description="The next step if the test fail not in Number format";
        else if (status.equals(Status.FiPathLoopError.name()))
            description="Loop is exist in the path";
        else if (status.equals(Status.stepNotExist.name()))
            description="The next step is not in the range";
        else
            description=status+" please implement this error in Status.getDescription";
        return description;
    }
}
