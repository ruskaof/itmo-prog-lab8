package com.ruskaof.common.commands;

/**
 * A mark that indicates that this command should only be executed
 * if the executor is the owner of a study group
 *
 * (а вот в котлине с sealed-классами такого кринжа было бы меньше)
 */
public interface PrivateAccessedStudyGroupCommand {
    int getStudyGroupId();
}
