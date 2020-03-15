/*
 * Copyright 2011 Jon S Akhtar (Sylvanaar)
 * File: TriggerExceptionAction.java, Class: TriggerExceptionAction
 * Last modified: Tue, 5 Apr 2011 10:40:01 -0400
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

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class TriggerExceptionAction extends AnAction {
    public void actionPerformed(@NotNull AnActionEvent e) {
        throw new RuntimeException("Test PsiViewer Exception");
    }
}
