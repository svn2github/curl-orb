package com.curlap.orb.plugin.popup.actions;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.html.parser.TagElement;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;

public class SourceAnalyzer {
	
	private final ICompilationUnit element;
	
	public SourceAnalyzer(ICompilationUnit element) {
		this.element = element;
	}
	
	public void read() {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(element);
		CompilationUnit unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());
		unit.accept(new ASTVisitorImpl());
		
		String sourceName = element.getElementName().substring(0, element.getElementName().lastIndexOf("."));
		System.out.println(sourceName);
	}
}

class ASTVisitorImpl extends ASTVisitor {
	private final Logger logger = Logger.getLogger(ASTVisitorImpl.class.getName());
	
	public boolean visit(Javadoc node) {
		logger.log(Level.INFO, "visit(Javadoc) - start");
		logger.log(Level.INFO, "node", node);
		Iterator iterator = node.tags().iterator();
		while(iterator.hasNext()) {
			TagElement element = (TagElement) iterator.next();
			logger.log(Level.INFO, "Œ^:" + element.getClass().getName());
			logger.log(Level.INFO, "element", element);
		}
		logger.log(Level.INFO, "end");
		return super.visit(node);
	}

}
