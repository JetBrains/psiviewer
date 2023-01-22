/*
 * Copyright 2011 Jon S Akhtar (Sylvanaar)
 * File: PluginErrorSubmitDialog.java, Class: PluginErrorSubmitDialog
 * Last modified: Tue, 5 Apr 2011 10:10:06 -0400
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sylvanaar.idea.errorreporting;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMUtil;
import org.jdom.Document;
import org.jdom.Element;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * This class defines the error submission dialog which allows the user to enter error details. It looks very similar to the built-in IDEA error
 * submission dialog.
 *
 * @author <a href="mailto:intellij@studer.nu">Etienne Studer</a>, Jul 14, 2006
 */
public class PluginErrorSubmitDialog extends DialogWrapper {
    private static final Logger LOGGER = Logger.getInstance(PluginErrorSubmitDialog.class);

    @SuppressWarnings({"AnalyzingVariableNaming"})
    public String USERNAME;// persisted setting

    private final JTextArea myDescriptionTextArea;
    private final JTextField myUserTextField;
    private final JPanel myContentPane;

    public PluginErrorSubmitDialog(Component inParent) {
        super(inParent, true);

        setTitle(PluginErrorReportSubmitterBundle.message("submission.dialog.title"));

        myDescriptionTextArea = new JTextArea(10, 50);
        myDescriptionTextArea.setWrapStyleWord(true);
        myDescriptionTextArea.setLineWrap(true);
        myDescriptionTextArea.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        myUserTextField = new JTextField();

        JPanel descriptionPane = new JPanel(new BorderLayout());
        descriptionPane.add(new JLabel(PluginErrorReportSubmitterBundle.message("submission.dialog.label.description")), BorderLayout.NORTH);
        descriptionPane.add(myDescriptionTextArea, BorderLayout.CENTER);

        JPanel userPane = new JPanel(new BorderLayout());
        userPane.add(new JLabel(PluginErrorReportSubmitterBundle.message("submission.dialog.label.user")), BorderLayout.NORTH);
        userPane.add(myUserTextField, BorderLayout.CENTER);

        myContentPane = new JPanel(new BorderLayout(0, 10));
        myContentPane.add(descriptionPane, BorderLayout.CENTER);
        myContentPane.add(userPane, BorderLayout.SOUTH);

        setOKButtonText(PluginErrorReportSubmitterBundle.message("submission.dialog.button.send"));

        init();
    }

    public void prepare() {
        File file = new File(getOptionsFilePath());
        if (file.exists()) {
            try {
                Document document = JDOMUtil.loadDocument(file);
                Element applicationElement = document.getRootElement();
                if (applicationElement == null) {
                    throw new InvalidDataException("Expected root element >application< not found");
                }
                Element componentElement = applicationElement.getChild("component");
                if (componentElement == null) {
                    throw new InvalidDataException("Expected element >component< not found");
                }

                DefaultJDOMExternalizer.readExternal(this, componentElement);
                myUserTextField.setText(USERNAME);
            } catch (Exception e) {
                LOGGER.info("Unable to read configuration file", e);
            }
        }
    }

    public void persist() {
        try {
            Element applicationElement = new Element("application");
            Element componentElement = new Element("component");
            applicationElement.addContent(componentElement);

            USERNAME = myUserTextField.getText();
            DefaultJDOMExternalizer.writeExternal(this, componentElement);

            Document document = new Document(applicationElement);
            JDOMUtil.writeDocument(document, getOptionsFilePath(), "\r\n");
        } catch (Exception e) {
            LOGGER.info("Unable to persist configuration file", e);
        }
    }

    private String getOptionsFilePath() {
        String optionsPath = PathManager.getOptionsPath();
        String filePath = optionsPath + File.separator + "pluginErrorReportSubmitter.xml";
        return filePath;
    }

    protected JComponent createCenterPanel() {
        return myContentPane;
    }

    public JComponent getPreferredFocusedComponent() {
        return myDescriptionTextArea;
    }

    public String getDescription() {
        return myDescriptionTextArea.getText();
    }

    public String getUser() {
        return myUserTextField.getText();
    }
}
