package idea.plugin.psiviewer.controller.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiFile;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: 5/5/11
 * Time: 7:29 PM
 */
public class PsiTestDump extends PsiDump {
  public static final String TEST_DATA_SEPARATOR = "-----";

  @Override
  public void actionPerformed(AnActionEvent e) {
    PsiFile pf = getFile(e);
    try {
      StringBuilder data = new StringBuilder();

      data.append(pf.getText());

      data.append('\n').append(TEST_DATA_SEPARATOR).append('\n');

      data.append(getPsiData(pf));

      placeStringInClipboard(data.toString());
    } catch (Exception ignored) {
    }
  }
}
