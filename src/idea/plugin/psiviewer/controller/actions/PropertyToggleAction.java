/*
 *  Copyright (c) 2002 Sabre, Inc. All rights reserved.
 */
package idea.plugin.psiviewer.controller.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.diagnostic.Logger;
import idea.plugin.psiviewer.util.IntrospectionUtil;

import javax.swing.*;
import java.beans.PropertyDescriptor;

public class PropertyToggleAction extends ToggleAction
{
    private static final Logger LOG = Logger.getInstance(PropertyToggleAction.class);
    private final Object _target;
    private PropertyDescriptor _property;

    public PropertyToggleAction(String actionName, String toolTip, Icon icon, Object target, String property)
    {
        super(actionName, toolTip, icon);
        _target = target;
        _property = IntrospectionUtil.getProperty(target.getClass(), property);
        if (!isPropertyValid(property)) _property = null;
    }

    private boolean isPropertyValid(String property)
    {
        if (_property == null)
        {
            LOG.error("Could not find " + getPropertyName(property));
            return false;
        }
        if (_property.getReadMethod() == null)
        {
            LOG.error("Could not find getter for " + getPropertyName(property));
            return false;
        }
        if (_property.getWriteMethod() == null)
        {
            LOG.error("Could not find setter for " + getPropertyName(property));
            _property = null;
            return false;
        }
        return true;
    }

    private String getPropertyName(String property)
    {
        return "property " + property + " in class " + _target.getClass();
    }

    public boolean isSelected(AnActionEvent anactionevent)
    {
        if (_property == null) return false;
        return (Boolean) IntrospectionUtil.getValue(_target, _property);
    }

    public void setSelected(AnActionEvent anactionevent, boolean flag)
    {
        IntrospectionUtil.setValue(_target, _property, flag);
    }

}
