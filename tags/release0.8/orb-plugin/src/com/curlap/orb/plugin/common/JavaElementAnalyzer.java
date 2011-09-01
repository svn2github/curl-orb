package com.curlap.orb.plugin.common;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class JavaElementAnalyzer {
		
	public static JavadocContent getJavaDoc(IMember member) throws JavaModelException {
		if (member.getJavadocRange() == null)
			return null;
			
		// target
		ICompilationUnit source = member.getCompilationUnit();		
		ISourceRange range = member.getSourceRange();
		int offset = range.getOffset();
		int length = range.getLength();
		
		// parser
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(source);
		parser.setSourceRange(offset, length);
		parser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
		
		// parsing
		TypeDeclaration unit = (TypeDeclaration) parser.createAST(null);		
		ASTJavadocVisitor visitor = new ASTJavadocVisitor();
		unit.accept(visitor);
		return visitor.getJavadocContent();
	}
}
