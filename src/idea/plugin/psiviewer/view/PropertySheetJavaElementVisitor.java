//package idea.plugin.psiviewer.view;
//
//import com.intellij.psi.*;
//
///**
//* Created by IntelliJ IDEA.
//* User: Jon S Akhtar
//* Date: 11/19/11
//* Time: 2:56 AM
//*/
//public class PropertySheetJavaElementVisitor extends JavaElementVisitor {
//    private PropertySheetHeaderRenderer propertySheetHeaderRenderer;
//
//    public PropertySheetJavaElementVisitor(PropertySheetHeaderRenderer propertySheetHeaderRenderer) {
//        this.propertySheetHeaderRenderer = propertySheetHeaderRenderer;
//    }
//
//    public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
//    }
//
//    public void visitClass(PsiClass psiElement) {
//        propertySheetHeaderRenderer.setIcon(IconCache.getIcon(PsiClass.class));
//    }
//
//    public void visitVariable(PsiVariable psiElement) {
//        propertySheetHeaderRenderer.setIcon(IconCache.getIcon(PsiVariable.class));
//    }
//
//    public void visitField(PsiField psiElement) {
//        propertySheetHeaderRenderer.setIcon(IconCache.getIcon(PsiField.class));
//    }
//
//    public void visitJavaFile(PsiJavaFile psiElement) {
//        propertySheetHeaderRenderer.setIcon(IconCache.getIcon(PsiJavaFile.class));
//    }
//
//    public void visitMethod(PsiMethod psiElement) {
//        propertySheetHeaderRenderer.setIcon(IconCache.getIcon(PsiMethod.class));
//    }
//
//    public void visitJavaToken(PsiJavaToken psiElement) {
//    }
//}
