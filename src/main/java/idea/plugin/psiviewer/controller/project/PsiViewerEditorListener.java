/*
    IDEA PsiViewer Plugin
    Copyright (C) 2002 Andrew J. Armstrong

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

	Author:
	Andrew J. Armstrong <andrew_armstrong@bigpond.com>
*/
package idea.plugin.psiviewer.controller.project;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.CaretAdapter;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import idea.plugin.psiviewer.view.PsiViewerPanel;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:andrew_armstrong@bigpond.com">Andrew Armstrong</a>
 * @version $Revision: 1.1 $
 */
public class PsiViewerEditorListener extends CaretAdapter implements FileEditorManagerListener, CaretListener {


    private static final Logger LOG = Logger.getInstance(PsiViewerEditorListener.class);

    private final Project myProject;

    public PsiViewerEditorListener(Project project) {
        myProject = project;
    }

    private @NotNull PsiViewerPanel getViewerPanel() {
        return PsiViewerProjectService.getViewerPanel(myProject);
    }

    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        debug("source = [" + source + "], file = [" + file + "]");
    }

    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        debug("source = [" + source + "], file = [" + file + "]");
    }

    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        var viewerPanel = getViewerPanel();
        if (viewerPanel.isToolWindowVisible()) {
            debug("selection changed " + event);
            viewerPanel.selectElementAtCaret();
        }
    }

    public void caretPositionChanged(@NotNull CaretEvent event) {
        var viewerPanel = getViewerPanel();
        if (viewerPanel.isToolWindowVisible()) {
            final Editor editor = event.getEditor();
            debug("caret moved to " + editor.getCaretModel().getOffset() + " in editor " + editor);
            viewerPanel.selectElementAtCaret();
        }
    }

    private static void debug(String message) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(message);
        }
    }

}
