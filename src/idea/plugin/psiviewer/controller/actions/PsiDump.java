package idea.plugin.psiviewer.controller.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.DebugUtil;

import java.awt.datatransfer.StringSelection;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: 5/4/11
 * Time: 6:41 PM
 */
public class PsiDump extends AnAction {
  @Override
  public void actionPerformed(AnActionEvent e) {
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
    String data = "";
    for (PsiFile file : pf.getViewProvider().getAllFiles()) {
      data = (new StringBuilder()).append(data).append(DebugUtil.psiToString(file, false, true)).toString();
    }
    return data;
  }

  protected static PsiFile getFile(AnActionEvent e) {
    return LangDataKeys.PSI_FILE.getData(e.getDataContext());
  }

  @Override
  public void update(AnActionEvent e) {
    e.getPresentation().setEnabled(getFile(e) != null);
  }
}
