package com.curlap.orb.plugin.common;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.curlap.orb.plugin.common.ASTJavadocVisitor.JavadocContent;

public class JavaElementAnalyzer {

	public JavaElementAnalyzer() {
	}
	
//	public void getJavaDoc(ISourceRange range) {
//		ASTParser parser = ASTParser.newParser(AST.JLS3);
//		int offset = range.getOffset();
//		int length = range.getLength();
//		System.out.println("offset:" + offset + " length:" + length);
//		parser.setSource(element);
//		parser.setSourceRange(offset, length);
//		parser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
////		CompilationUnit unit = (CompilationUnit) parser.createAST(null);
//		TypeDeclaration unit = (TypeDeclaration) parser.createAST(null);
//		unit.accept(new ASTVisitor() {
//			public boolean visit(Javadoc node) {
//				System.out.println(node);
//				return super.visit(node);
//			}
//		});
//	}
	
	public JavadocContent getJavaDoc(IMember member) throws JavaModelException {
		// define target
		ICompilationUnit source = member.getCompilationUnit();
		ISourceRange range = member.getSourceRange();
		int offset = range.getOffset();
		int length = range.getLength();
		
		// parser
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(source);
		parser.setSourceRange(offset, length);
		parser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
		TypeDeclaration unit = (TypeDeclaration) parser.createAST(null);
		
//		Javadoc javaDoc = unit.getJavadoc();
//		ChildPropertyDescriptor desc = unit.getJavadocProperty();
//		System.out.println(desc.getChildType());
		
		ASTJavadocVisitor visitor = new ASTJavadocVisitor();
		unit.accept(visitor);
		return visitor.getJavadoc();
	}
}

