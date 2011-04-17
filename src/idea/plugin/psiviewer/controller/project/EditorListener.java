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
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import idea.plugin.psiviewer.view.PsiViewerPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @version $Revision: 1.1 $
 * @author  <a href="mailto:andrew_armstrong@bigpond.com">Andrew Armstrong</a>
 */
public class EditorListener implements FileEditorManagerListener, CaretListener
{
    private static final Logger LOG = Logger.getInstance("idea.plugin.psiviewer.controller.project.EditorListener");

    private final PsiViewerPanel _viewer;
    private final Project _project;
    private Editor _selectedEditor;
    private final PsiTreeChangeAdapter _treeChangeListener;
    private Timer _timer;

    public EditorListener(PsiViewerPanel viewer, Project project)
    {
        _viewer = viewer;
        _project = project;
        _treeChangeListener = new PsiTreeChangeAdapter()
        {
            public void childrenChanged(final PsiTreeChangeEvent event)
            {
                updateTreeFromPsiTreeChange(event);
            }

            public void childAdded(PsiTreeChangeEvent event)
            {
                updateTreeFromPsiTreeChange(event);
            }

            public void childMoved(PsiTreeChangeEvent event)
            {
                updateTreeFromPsiTreeChange(event);
            }

            public void childRemoved(PsiTreeChangeEvent event)
            {
                updateTreeFromPsiTreeChange(event);
            }

            public void childReplaced(PsiTreeChangeEvent event)
            {
                updateTreeFromPsiTreeChange(event);
            }

            public void propertyChanged(PsiTreeChangeEvent event)
            {
                updateTreeFromPsiTreeChange(event);
            }
        };
    }

    private void updateTreeFromPsiTreeChange(final PsiTreeChangeEvent event)
    {
        if (isElementChangedUnderViewerRoot(event))
        {
            startPanelUpdateTimer(_selectedEditor, true);
        }
    }

    private boolean isElementChangedUnderViewerRoot(final PsiTreeChangeEvent event)
    {
        PsiElement elementChangedByPsi = event.getParent();
        PsiElement viewerRootElement = _viewer.getRootElement();
        return PsiTreeUtil.isAncestor(viewerRootElement, elementChangedByPsi, false);
    }

   public void fileOpened(FileEditorManager source, VirtualFile file) {
   }

   public void fileClosed(FileEditorManager source, VirtualFile file) {
   }

   public void selectionChanged(FileEditorManagerEvent event) {
      stopListeningToSelectedEditor();

      if (event.getNewFile() == null)
         return;

      startListeningToSelectedEditor();
      _viewer.selectElementAtCaret(_selectedEditor);

   }

   public void selectedFileChanged(FileEditorManagerEvent event)
   {
      selectionChanged(event);
   }

    public void caretPositionChanged(CaretEvent event)
    {
        final Editor editor = event.getEditor();
        debug("caret moved to " + editor.getCaretModel().getOffset() + " in editor " + editor);
        startPanelUpdateTimer(editor, false);
    }

    private synchronized void startPanelUpdateTimer(final Editor editor, final boolean isTreeRefreshed)
    {
        stopPanelUpdateTimer();
        _timer = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                stopPanelUpdateTimer();
               PsiDocumentManager.getInstance(_project).commitAllDocuments();
                if (isTreeRefreshed)
                    _viewer.refreshRootElement();
                _viewer.selectElementAtCaret(editor);
            }
        });
        _timer.setRepeats(false);
        _timer.setCoalesce(true);
        _timer.start();
    }

    private synchronized void stopPanelUpdateTimer()
    {
        if (_timer != null)
        {
            _timer.stop();
            _timer = null;
        }
    }

    public void start()
    {
        FileEditorManager.getInstance(_project).addFileEditorManagerListener(this);
        startListeningToSelectedEditor();
        PsiManager.getInstance(_project).addPsiTreeChangeListener(_treeChangeListener);
    }

    public void stop()
    {
        stopPanelUpdateTimer();
        PsiManager.getInstance(_project).removePsiTreeChangeListener(_treeChangeListener);
        FileEditorManager.getInstance(_project).removeFileEditorManagerListener(this);
        stopListeningToSelectedEditor();
    }

    private void startListeningToSelectedEditor()
    {
//        _selectedEditor = FileEditorManager.getInstance(_project).getSelectedEditor();
        _selectedEditor = FileEditorManager.getInstance(_project).getSelectedTextEditor();
        if (_selectedEditor != null)
        {
            debug("Start listening to editor" + _selectedEditor);
            _selectedEditor.getCaretModel().addCaretListener(this);
        }
    }

    private void stopListeningToSelectedEditor()
    {
        if (_selectedEditor != null)
        {
            debug("Stop listening to editor" + _selectedEditor);
            stopPanelUpdateTimer();
            _selectedEditor.getCaretModel().removeCaretListener(this);
            _selectedEditor = null;
        }
    }

    private static void debug(String message)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug(message);
        }
    }

}
