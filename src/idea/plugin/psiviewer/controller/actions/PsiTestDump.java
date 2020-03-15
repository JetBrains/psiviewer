package idea.plugin.psiviewer.controller.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class PsiTestDump extends PsiDump {
  public static final String TEST_DATA_SEPARATOR = "-----";

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
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
