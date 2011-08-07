/*
 *  Copyright (c) 2002 Sabre, Inc. All rights reserved.
 */
package idea.plugin.psiviewer.util;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiJavaReference;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IntrospectionUtil
{
    private static final Logger LOG = Logger.getInstance("idea.plugin.psiviewer.util.IntrospectionUtil");

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

    public static PropertyDescriptor getProperty(Class targetClass, String propertyName)
    {
        PropertyDescriptor[] propertyDescriptors = getProperties(targetClass);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors)
        {
            if (propertyDescriptor.getName().equals(propertyName)) return propertyDescriptor;
        }
        return null;
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

            //LOG.debug("Invoking " + getter.getName() + " on " + target.toString());
            // TODO: Hack...invoking getVariants() is prohibited on PsiJavaReference objects in IDEA build #3144 and later
            if (target instanceof PsiJavaReference && "variants".equals(name))
            {
                value = "<unavailable>";
            }
            else
            {
                value = getter.invoke(target, args);
            }
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

    public static void setValue(Object target, PropertyDescriptor property, Object value)
    {
        Method setter = property.getWriteMethod();
        String name = property.getDisplayName();
        try
        {
            Object args[] = {value};
            setter.setAccessible(true);
            value = setter.invoke(target, args);
        }
        catch (InvocationTargetException ex)
        {
            LOG.debug("Exception setting property " + name + " on " + target.toString());
            LOG.debug(ex.getTargetException());
            value = "<exception=" + ex.getMessage() + ">";
        }
        catch (Exception ex)
        {
            LOG.debug("Exception setting property " + name + " on " + target.toString());
            LOG.debug(ex);
            value = "<exception=" + ex.getMessage() + ">";
        }
    }
}
