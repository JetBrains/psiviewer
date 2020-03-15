/*
 *  Copyright (c) 2002 Sabre, Inc. All rights reserved.
 */
package idea.plugin.psiviewer.controller.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.diagnostic.Logger;
import idea.plugin.psiviewer.util.IntrospectionUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.beans.PropertyDescriptor;

public class PropertyToggleAction extends ToggleAction {
    private static final Logger LOG = Logger.getInstance(PropertyToggleAction.class);
    private final Object myTarget;
    private PropertyDescriptor myPropertyDescriptor;

    public PropertyToggleAction(String actionName, String toolTip, Icon icon, Object target, String property) {
        super(actionName, toolTip, icon);
        myTarget = target;
        myPropertyDescriptor = IntrospectionUtil.getProperty(target.getClass(), property);
        if (!isPropertyValid(property)) myPropertyDescriptor = null;
    }

    private boolean isPropertyValid(String property)
    {
        if (myPropertyDescriptor == null) {
            LOG.error("Could not find " + getPropertyName(property));
            return false;
        }
        if (myPropertyDescriptor.getReadMethod() == null) {
            LOG.error("Could not find getter for " + getPropertyName(property));
            return false;
        }
        if (myPropertyDescriptor.getWriteMethod() == null) {
            LOG.error("Could not find setter for " + getPropertyName(property));
            myPropertyDescriptor = null;
            return false;
        }
        return true;
    }

    private String getPropertyName(String property)
    {
        return "property " + property + " in class " + myTarget.getClass();
    }

    public boolean isSelected(@NotNull AnActionEvent anactionevent) {
        if (myPropertyDescriptor == null) return false;
        return (Boolean) IntrospectionUtil.getValue(myTarget, myPropertyDescriptor);
    }

    public void setSelected(@NotNull AnActionEvent anactionevent, boolean flag) {
        IntrospectionUtil.setValue(myTarget, myPropertyDescriptor, flag);
    }

}
