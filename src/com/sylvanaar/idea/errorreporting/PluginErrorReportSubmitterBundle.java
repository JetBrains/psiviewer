/*
 * Copyright 2011 Jon S Akhtar (Sylvanaar)
 * File: PluginErrorReportSubmitterBundle.java, Class: PluginErrorReportSubmitterBundle
 * Last modified: Mon, 8 Nov 2010 08:19:00 -0500
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

import com.intellij.CommonBundle;
import org.jetbrains.annotations.PropertyKey;

import java.util.ResourceBundle;

public class PluginErrorReportSubmitterBundle {
    private static final ResourceBundle OUR_BUNDLE = ResourceBundle.getBundle("com.sylvanaar.idea.errorreporting.PluginErrorReportSubmitterBundle");

    private PluginErrorReportSubmitterBundle() {
    }

    public static String message(@PropertyKey(resourceBundle = "com.sylvanaar.idea.errorreporting.PluginErrorReportSubmitterBundle") String key,
                                 Object... params) {
        return CommonBundle.message(OUR_BUNDLE, key, params);
    }
}
