/*
 *  Copyright (c) 2002 Sabre, Inc. All rights reserved.
 */
package idea.plugin.psiviewer.util;

import com.intellij.openapi.diagnostic.Logger;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IntrospectionUtil
{
    private static final Logger LOG = Logger.getInstance(IntrospectionUtil.class);

    public static PropertyDescriptor[] getProperties(Class targetClass)
    {
        PropertyDescriptor[] propertyDescriptors;
        try
        {
            BeanInfo bi = Introspector.getBeanInfo(targetClass);
            propertyDescriptors = bi.getPropertyDescriptors();
        }
        catch (IntrospectionException ex)
        {
            LOG.debug("Introspector.getBeanInfo(" + targetClass + ") exception...");
            LOG.debug(ex);
            propertyDescriptors = new PropertyDescriptor[0];
        }
        return propertyDescriptors;
    }

    public static Object getValue(Object target, PropertyDescriptor property)
    {
        Method getter = property.getReadMethod();
        String name = property.getDisplayName();
        Object value;
        try
        {
            Object args[] = {};
            getter.setAccessible(true);

            value = getter.invoke(target, args);
        }
        catch (InvocationTargetException ex)
        {
            LOG.debug("Exception getting property " + name + " on " + target.toString());
            LOG.debug(ex.getTargetException());
            value = "<exception=" + ex.getMessage() + ">";
        }
        catch (Exception ex)
        {
            LOG.debug("Exception getting property " + name + " on " + target.toString());
            LOG.debug(ex);
            value = "<exception=" + ex.getMessage() + ">";
        }
        return value;
    }
}
