/*
 *  Copyright (c) 2002 Sabre, Inc. All rights reserved.
 */
package idea.plugin.psiviewer.view;

import com.intellij.openapi.util.Ref;
import com.intellij.testFramework.PlatformTestUtil;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Ignore;

import javax.swing.*;
import javax.swing.table.TableModel;

@Ignore("Tests need to be reworked, they are run without IDE")
public class PropertySheetPanelTest extends TestCase {
    public PropertySheetPanelTest(String s) {
        super(s);
    }

    private final PropertySheetPanel propertySheetPanel = new PropertySheetPanel();


    public void testSetTarget() {
        setTargetAndWait(propertySheetPanel);
        TableModel model = propertySheetPanel.getTable().getModel();
        Assert.assertEquals("# cols", 2, model.getColumnCount());
        Assert.assertEquals("# rows", 3, model.getRowCount());
        assertCellEquals("class", model, 0, 0);
        assertCellEquals(MyBean.class.toString(), model, 0, 1);
        assertCellEquals("object", model, 1, 0);
        assertCellEquals("null", model, 1, 1);
        assertCellEquals("text", model, 2, 0);
        assertCellEquals("Text", model, 2, 1);
    }

    private static void setTargetAndWait(PropertySheetPanel propertySheetPanel) {
        Ref<Boolean> doneRef = new Ref<>(false);
        propertySheetPanel.setTarget(new MyBean(), () -> doneRef.set(true));
        PlatformTestUtil.waitWithEventsDispatching("Could not finish setting target", doneRef::get, 10);
    }

    private static void assertCellEquals(String expectedValue, TableModel model, int row, int col) {
        Assert.assertEquals("table[" + row + "," + col + "]", expectedValue, model.getValueAt(row, col));
    }

    static class MyBean {
        public String getText() {
            return "Text";
        }

        public Object getObject() {
            return null;
        }

        public void setObject(Object o) {
        }

        public void setWriteOnly(Object o) {
        }
    }

    public static void main(String[] args) {
        final PropertySheetPanel propertySheetPanel = new PropertySheetPanel();
        final JFrame jFrame = new JFrame("Property Sheet");
        jFrame.getContentPane().add(propertySheetPanel);
        setTargetAndWait(propertySheetPanel);
        jFrame.pack();
//        jFrame.show();
        jFrame.setVisible(true); // 20050826
    }

}
