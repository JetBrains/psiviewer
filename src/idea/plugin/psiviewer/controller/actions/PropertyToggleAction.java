/*
 *  Copyright (c) 2002 Sabre, Inc. All rights reserved.
 */
package idea.plugin.psiviewer.controller.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import idea.plugin.psiviewer.controller.project.PsiViewerProjectService.BooleanConsumer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.function.BooleanSupplier;

public class PropertyToggleAction extends ToggleAction {
    @NotNull
    private final BooleanConsumer myMutator;
    @NotNull
    private final BooleanSupplier myAccessor;

    public PropertyToggleAction(String actionName, String toolTip, Icon icon,
                                @NotNull BooleanSupplier accessor,
                                @NotNull BooleanConsumer mutator) {
        super(actionName, toolTip, icon);
        myAccessor = accessor;
        myMutator = mutator;

    }

    public boolean isSelected(@NotNull AnActionEvent anactionevent) {
        return myAccessor.getAsBoolean();
    }

    public void setSelected(@NotNull AnActionEvent anactionevent, boolean flag) {
        myMutator.accept(flag);
    }
}
