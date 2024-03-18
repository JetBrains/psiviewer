package idea.plugin.psiviewer.controller.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.DebugUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;

public class PsiDump extends AnAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    PsiFile pf = getFile(e);
    try {
      String data = getPsiData(pf);
      placeStringInClipboard(data);
    } catch (Exception ignored) {
    }
  }

  protected void placeStringInClipboard(String data) {StringSelection trans = new StringSelection(data);
    CopyPasteManager cpmgr = CopyPasteManager.getInstance();
    cpmgr.setContents(trans);
  }

  protected static String getPsiData(PsiFile pf) {
    StringBuilder data = new StringBuilder();
    for (PsiFile file : pf.getViewProvider().getAllFiles()) {
      data.append(DebugUtil.psiToString(file, false, true));
    }
    return data.toString();
  }

  protected static PsiFile getFile(AnActionEvent e) {
    return LangDataKeys.PSI_FILE.getData(e.getDataContext());
  }

  @Override
  public void update(AnActionEvent e) {
    e.getPresentation().setEnabled(getFile(e) != null);
  }

  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    return ActionUpdateThread.BGT;
  }
}
