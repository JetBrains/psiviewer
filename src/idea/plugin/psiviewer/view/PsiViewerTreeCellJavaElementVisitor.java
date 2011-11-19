//package idea.plugin.psiviewer.view;
//
//import com.intellij.psi.*;
//
///**
//* Created by IntelliJ IDEA.
//* User: Jon S Akhtar
//* Date: 11/19/11
//* Time: 2:59 AM
//*/
//class PsiViewerTreeCellJavaElementVisitor extends JavaElementVisitor {
//
//    private PsiViewerTreeCellRenderer psiViewerTreeCellRenderer;
//
//    public PsiViewerTreeCellJavaElementVisitor(PsiViewerTreeCellRenderer psiViewerTreeCellRenderer) {
//        this.psiViewerTreeCellRenderer = psiViewerTreeCellRenderer;
//    }
//
//    public void visitVariable(PsiVariable psiElement) {
//        psiViewerTreeCellRenderer.setIcon(IconCache.getIcon(PsiVariable.class));
//        psiViewerTreeCellRenderer.setText("PsiVariable: " + psiElement.getName());
//    }
//
//    public void visitJavaFile(PsiJavaFile psiElement) {
//        psiViewerTreeCellRenderer.setIcon(IconCache.getIcon(PsiJavaFile.class));
//        psiViewerTreeCellRenderer.setText("PsiJavaFile: " + psiElement.getName());
//    }
//
//    public void visitJavaToken(PsiJavaToken psiElement) {
//        psiViewerTreeCellRenderer.setText("PsiJavaToken: " + psiElement.getText());
//    }
//
//    public void visitMethod(PsiMethod psiElement) {
//        psiViewerTreeCellRenderer.setIcon(IconCache.getIcon(PsiMethod.class));
//        psiViewerTreeCellRenderer.setText("PsiMethod: " + psiElement.getName());
//    }
//
//    public void visitField(PsiField psiElement) {
//        psiViewerTreeCellRenderer.setIcon(IconCache.getIcon(PsiField.class));
//        psiViewerTreeCellRenderer.setText("PsiField: " + psiElement.getName());
//    }
//
//    public void visitClass(PsiClass psiElement) {
//        psiViewerTreeCellRenderer.setIcon(IconCache.getIcon(PsiClass.class));
//        psiViewerTreeCellRenderer.setText("PsiClass: " + psiElement.getName());
//    }
//
//    public void visitReferenceExpression(PsiReferenceExpression psireferenceexpression) {
//    }
//}
