/*
 * Copyright 2011 Jon S Akhtar (Sylvanaar)
 * File: YouTrackBugReporter.java, Class: YouTrackBugReporter
 * Last modified: Tue, 5 Apr 2011 10:27:34 -0400
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

import com.intellij.diagnostic.IdeErrorsDialog;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.ErrorReportSubmitter;
import com.intellij.openapi.diagnostic.IdeaLoggingEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.diagnostic.SubmittedReportInfo;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.NonNls;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.intellij.openapi.diagnostic.SubmittedReportInfo.SubmissionStatus.FAILED;
import static com.intellij.openapi.diagnostic.SubmittedReportInfo.SubmissionStatus.NEW_ISSUE;

public class YouTrackBugReporter extends ErrorReportSubmitter {
    private static final Logger log = Logger.getInstance(YouTrackBugReporter.class);
    @NonNls
    private static final String SERVER_URL = "http://sylvanaar.myjetbrains.com/youtrack/";
    private static final String SERVER_REST_URL = SERVER_URL + "rest/";
    private static final String SERVER_ISSUE_URL = SERVER_REST_URL + "issue";
    private static final String LOGIN_URL = SERVER_REST_URL + "user/login";
    private static final String USER_NAME = "autosubmit";
    private static final String PROJECT_NAME = "PSI";

    private String myDescription = null;
    private String myExtraInformation = "";
    private String myAffectedVersion = null;
    private final boolean myShowDialog = ApplicationInfo.getInstance().getBuild().getBaselineVersion() > 110;

    public String submit() {
        if (this.myDescription == null || this.myDescription.length() == 0) throw new RuntimeException("Description");

        StringBuilder response = new StringBuilder("");

        //Create Post String
        StringBuilder data = new StringBuilder();
        try {

            // Build Login
            data.append(URLEncoder.encode("login", StandardCharsets.UTF_8))
                    .append("=")
                    .append(URLEncoder.encode(USER_NAME, StandardCharsets.UTF_8));
            data.append("&")
                    .append(URLEncoder.encode("password", StandardCharsets.UTF_8))
                    .append("=")
                    .append(URLEncoder.encode("root", StandardCharsets.UTF_8));

            // Send Login
            URL url = new URL(LOGIN_URL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data.toString());
            wr.flush();

            // Get The Response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }

            CookieManager cm = new CookieManager();

            cm.storeCookies(conn);


// project=TST&assignee=beto&summary=new issue&description=description of new issue #&priority=show-stopper&type=feature&subsystem=UI&state=Reopened&affectsVersion=2.0,2.0.1&fixedVersions=2.0&fixedInBuild=2.0.1
            // POST /rest/issue?{project}&{assignee}&{summary}&{description}&{priority}&{type}&{subsystem}&{state}&{affectsVersion}&{fixedVersions}&{attachments}&{fixedInBuild}

            data = new StringBuilder();
            data.append(URLEncoder.encode("project", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(PROJECT_NAME, StandardCharsets.UTF_8));
            data.append("&").append(URLEncoder.encode("assignee", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("Unassigned", StandardCharsets.UTF_8));
            data.append("&").append(URLEncoder.encode("summary", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(myDescription, StandardCharsets.UTF_8));
            data.append("&").append(URLEncoder.encode("description", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(myExtraInformation, StandardCharsets.UTF_8));
            data.append("&").append(URLEncoder.encode("priority", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("4", StandardCharsets.UTF_8));
            data.append("&").append(URLEncoder.encode("type", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("Exception", StandardCharsets.UTF_8));

            if (this.myAffectedVersion != null)
                data.append("&").append(URLEncoder.encode("affectsVersion", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(this.myAffectedVersion, StandardCharsets.UTF_8));

            // Send Data To Page
            url = new URL(SERVER_ISSUE_URL);

            conn = url.openConnection();
            conn.setDoOutput(true);
            cm.setCookies(conn);

            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data.toString());
            wr.flush();

            // Get The Response
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = rd.readLine()) != null) {
                response.append(line);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.toString();
    }


    @Override
    public String getReportActionText() {
        return "Report Error";
    }

    @Override
    public SubmittedReportInfo submit
            (IdeaLoggingEvent[] ideaLoggingEvents, Component
                    component) {

        if (myShowDialog) {
            return submit(ideaLoggingEvents, this.myDescription, "<anonymous>", component);
        } else {
            // show modal error submission dialog
            PluginErrorSubmitDialog dialog = new PluginErrorSubmitDialog(component);
            dialog.prepare();
            dialog.show();

            // submit error to server if user pressed SEND
            int code = dialog.getExitCode();
            if (code == DialogWrapper.OK_EXIT_CODE) {
                dialog.persist();
                String description = dialog.getDescription();
                String user = dialog.getUser();
                return submit(ideaLoggingEvents, description, user, component);
            }
        }
        // otherwise do nothing
        return null;
    }

    @Override
    public void submitAsync(IdeaLoggingEvent[] events, String additionalInfo, Component parentComponent,
                            Consumer<SubmittedReportInfo> consumer) {
        this.myDescription = additionalInfo;
        super.submitAsync(events, additionalInfo, parentComponent, consumer);
    }

    private SubmittedReportInfo submit
            (IdeaLoggingEvent[] ideaLoggingEvents, String
                    description, String
                    user, Component
                    component) {
        this.myDescription = ideaLoggingEvents[0].getThrowableText().substring(0, Math.min(Math.max(80, ideaLoggingEvents[0].getThrowableText().length()), 80));

        @NonNls StringBuilder descBuilder = new StringBuilder();

        String platformBuild = ApplicationInfo.getInstance().getBuild().asString();
        descBuilder.append("Platform Version: ").append(platformBuild).append('\n');
        Throwable t = ideaLoggingEvents[0].getThrowable();
        if (t != null) {
            final PluginId pluginId = IdeErrorsDialog.findPluginId(t);
            if (pluginId != null) {
                final IdeaPluginDescriptor ideaPluginDescriptor = PluginManager.getPlugin(pluginId);
                if (ideaPluginDescriptor != null && !ideaPluginDescriptor.isBundled()) {
                    descBuilder.append("Plugin ").append(ideaPluginDescriptor.getName()).append(" version: ").append(ideaPluginDescriptor.getVersion()).append("\n");
                    this.myAffectedVersion = ideaPluginDescriptor.getVersion();
                }
            }
        }

        if (user == null) user = "<none>";
        if (description == null) description = "<none>";

        descBuilder.append("\n\nDescription: ").append(description).append("\n\nUser: ").append(user);

        for (IdeaLoggingEvent e : ideaLoggingEvents)
            descBuilder.append("\n\n").append(e.toString());

        this.myExtraInformation = descBuilder.toString();

        String result = submit();
        log.info("Error submitted, response: " + result);

        if (result == null)
            return new SubmittedReportInfo(SERVER_ISSUE_URL, "", FAILED);

        String ResultString = null;
        try {
            Pattern regex = Pattern.compile("id=\"([^\"]+)\"", Pattern.DOTALL | Pattern.MULTILINE);
            Matcher regexMatcher = regex.matcher(result);
            if (regexMatcher.find()) {
                ResultString = regexMatcher.group(1);
            }
        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
        }

        SubmittedReportInfo.SubmissionStatus status = NEW_ISSUE;

        if (ResultString == null)
            return new SubmittedReportInfo(SERVER_ISSUE_URL, "", FAILED);
//        else {
//            if (ResultString.trim().length() > 0)
//                status = DUPLICATE;
//        }

        return new SubmittedReportInfo(SERVER_URL + "issue/" + ResultString, ResultString, status);
    }
}
