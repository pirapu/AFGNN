package pdg;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import graphStructures.GraphNode;
import graphStructures.RelationshipEdge;
import graphStructures.ReturnObject;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.StringEdgeNameProvider;
import org.jgrapht.ext.StringNameProvider;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The Class PDGCore.
 */
public class PDGCore {

	/**
	 * Instantiates a new PDG core.
	 */
	public PDGCore() {
	}

	/**
	 * Adds the file.
	 *
	 * @param inArg        the in FileInputStram
	 * @param hrefGraph    the href graph
	 * @param previousNode the previous node
	 * @param consoleText  the console text
	 * @return true, if successful
	 * @throws ParseException the parse exception
	 * @throws IOException    Signals that an I/O exception has occurred.
	 */
	public boolean addFile(FileInputStream inArg,
			@SuppressWarnings("rawtypes") DirectedGraph<GraphNode, RelationshipEdge> hrefGraph, GraphNode previousNode, 
			String PrevClass, String selectedFileName, String outputDir, String apiAndSampleName) throws ParseException, IOException {
		CompilationUnit cu;
		try {
			// parse the file
			cu = JavaParser.parse(inArg);
			inArg.close();
			// System.out.println("Input File Parsing Done");
		} catch (Exception e) {
			System.out.println("\nSyntatic Error");
			System.out.println("File: " + selectedFileName + " and apiAndSampleName: " + apiAndSampleName);
			System.err.println();
			return false;
		}

		CodeVisitor cv = new CodeVisitor();
		// System.out.println(">>Calling astPrint");
		// cv.astPrint(cu);
		// System.out.println(">>astPrint Done");

		// todot("stage2.dot", hrefGraph);

		FileOutputStream out;

		String[] ParsedName = selectedFileName.split("\\.");
		// System.out.println("***************");
		// System.out.println("Processing: " + selectedFileName + " of " + apiAndSampleName);
		// System.out.println(ParsedName[0]);
		// System.out.println("***************");
		File ofile = new File(outputDir + ParsedName[0] + "_" + apiAndSampleName + "_graph_dump.txt");
		ofile.getParentFile().mkdirs(); // Will create parent directories if not exists
		ofile.createNewFile(); // if file already exists will do nothing
		out = new FileOutputStream(ofile, false);
		
		try {
			cv.semanticAnalysis(cu, hrefGraph, previousNode, new ArrayList<>(), out);
		} catch (Exception e) {
			System.out.println("Error for: " + ParsedName[0] + "_" + apiAndSampleName);
			System.out.println("Error: " + e.getMessage());
			String errorFileName = "./../error.txt";
			BufferedWriter error_out = new BufferedWriter(
                new FileWriter(errorFileName, true));
			error_out.write("File: " + ParsedName[0] + "_" + apiAndSampleName + "\n");
            error_out.write(e.getMessage() + "\n");
			error_out.write("==============================================\n");
            error_out.close();
		}

		// todot("stage3.dot", hrefGraph);

		SymbolTable st = cv.st;
		st.addDependencies(hrefGraph, out);

		// write what node are of what class

		out.close();

		// todot("stage4.dot", hrefGraph);
		// System.out.println(">>Calling printSymbolTable");
		// st.printSymbolTable();
		// System.out.println(">>printSymbolTable Done");

		return true;
	}

	public void todot(String filename,
			@SuppressWarnings("rawtypes") DirectedGraph<GraphNode, RelationshipEdge> hrefGraph) {
		FileOutputStream out;
		try {
			checkIfFolderExists();
			GraphNode.exporting = true;

			if (filename == null) {
				GraphNode.exporting = false;
				return;
			}
			out = new FileOutputStream("dotOutputs/" + filename);
			@SuppressWarnings("rawtypes")
			DOTExporter<GraphNode, RelationshipEdge> exporter = new DOTExporter<>(
					new StringNameProvider<>(), null,
					new StringEdgeNameProvider<>());
			exporter.export(new OutputStreamWriter(out), hrefGraph);
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		GraphNode.exporting = false;

	}

	private boolean checkIfFolderExists() {
		File theDir = new File("dotOutputs");
		return !theDir.exists() && theDir.mkdir();
	}

}

class CodeVisitor extends VoidVisitorAdapter<Object> {

	ArrayList<String> errorList = new ArrayList<>();
	SymbolTable st = new SymbolTable();

	CodeVisitor() {
	}

	// SEMANTIC ANALYSIS
	ArrayList<String> semanticAnalysis(Node node,
			@SuppressWarnings("rawtypes") DirectedGraph<GraphNode, RelationshipEdge> hrefGraph, GraphNode previousNode,
			ArrayList<Scope> ls, FileOutputStream out) {
		ReturnObject ret;
		GraphNode nextNode = previousNode;
		ArrayList<Scope> lastScopes = new ArrayList<>(ls);

		if (relevant(node)) {
			ret = st.SemanticNodeCheck(node, hrefGraph, previousNode, lastScopes, out);
			if (ret.hasError()) {
				errorList.add(ret.getError());
			} else {
				nextNode = ret.getGraphNode();
			}
		}

		for (Node child : node.getChildrenNodes()) {
			semanticAnalysis(child, hrefGraph, nextNode, lastScopes, out);
		}

		return errorList;
	}

	ArrayList<String> printSemanticErrors(JTextArea textArea) {

		ReturnObject ret;
		// add undefined methods error
		if (st.pendingMethodDeclarations.size() > 0)
			errorList.addAll(st.pendingMethodDeclarations.stream().map(undeclaredMethod -> "error:Undeclared Method "
					+ undeclaredMethod.methodName + " in class " + undeclaredMethod.methodScope + "")
					.collect(Collectors.toList()));
		// check method calls that were pending
		for (int i = 0; i < st.pendingMethodNodes.size(); i++) {
			ret = st.postProcessMethodCallNode(st.pendingMethodNodes.get(i).method,
					st.pendingMethodNodes.get(i).classScope, st.pendingMethodNodes.get(i).methodName,
					st.pendingMethodNodes.get(i).callerMethod);
			if (ret.hasError()) {
				errorList.add(ret.getError());
			}
		}
		textArea.setText(textArea.getText() + "Semantic errors:\n");

		for (String error : errorList) {
			textArea.setText(textArea.getText() + error + "\n");
		}
		textArea.setText(textArea.getText() + "Ended semantic errors\n");
		return errorList;
	}

	// AST PRINTING
	void astPrint(Node child2) {
		if (relevant(child2)) {
			if (child2.getClass().equals(com.github.javaparser.ast.body.MethodDeclaration.class)) {
				printMethodModifiers(child2);
				MethodType(child2);
				MethodName(child2);
			} else if (child2.getClass().equals(com.github.javaparser.ast.body.ClassOrInterfaceDeclaration.class)) {
				printClassIntModifiers(child2);
				ClassName(child2);
				ClassExtension(child2);
			}

			else {
				System.out.println("------------------------------------------------------------");
				System.out.println(child2.getClass());
				System.out.println(child2.toString());
			}
		}
		child2.getChildrenNodes().forEach(this::astPrint);
	}

	private boolean relevant(Node child2) {
		return !child2.getClass().equals(com.github.javaparser.ast.body.VariableDeclarator.class) &&
				!child2.getClass().equals(CompilationUnit.class)
				&& !child2.getClass().equals(com.github.javaparser.ast.stmt.ExpressionStmt.class)
				&& !child2.getClass().equals(com.github.javaparser.ast.stmt.BlockStmt.class)
				&& !child2.getClass().equals(com.github.javaparser.ast.type.VoidType.class)
				&& !child2.getClass().equals(com.github.javaparser.ast.type.ClassOrInterfaceType.class);
	}

	private void MethodType(Node child2) {
		if (child2.getClass().equals(com.github.javaparser.ast.body.MethodDeclaration.class)) {
			System.out.println("------------------------------------------------------------");
			System.out.print("Method.Type\n" + ((MethodDeclaration) child2).getType().toString() + "\n");
		}
	}

	private void MethodName(Node child2) {
		if (child2.getClass().equals(com.github.javaparser.ast.body.MethodDeclaration.class)) {
			System.out.println("------------------------------------------------------------");
			System.out.print("Method.Name\n" + ((MethodDeclaration) child2).getNameExpr() + "\n");
		}
	}

	private void ClassName(Node child2) {
		if (child2.getClass().equals(com.github.javaparser.ast.body.ClassOrInterfaceDeclaration.class)) {
			System.out.println("------------------------------------------------------------");
			System.out.print("Class.Name\n" + ((ClassOrInterfaceDeclaration) child2).getNameExpr() + "\n");
		}
	}

	private void ClassExtension(Node child2) {
		if (child2.getClass().equals(com.github.javaparser.ast.body.ClassOrInterfaceDeclaration.class)) {
			System.out.println("------------------------------------------------------------");
			System.out.print(
					"Class.ExtensionOf\n" + ((ClassOrInterfaceDeclaration) child2).getExtends().toString() + "\n");
		}
	}

	private void printMethodModifiers(Node child2) {
		if (ModifierSet.isPrivate(((MethodDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("Method.Modifier\nprivate\n");
		}
		if (ModifierSet.isPublic(((MethodDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("Method.Modifier\npublic\n");
		}
		if (ModifierSet.isStatic(((MethodDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("Method.Modifier\nstatic\n");
		}
		if (ModifierSet.isStrictfp(((MethodDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("Method.Modifier\nstrictfp\n");
		}
		if (ModifierSet.isSynchronized(((MethodDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("Method.Modifier\nsyncronized\n");
		}
		if (ModifierSet.isTransient(((MethodDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("Method.Modifier\ntransient\n");
		}
		if (ModifierSet.isVolatile(((MethodDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("Method.Modifier\nvolatile\n");
		}
	}

	private void printClassIntModifiers(Node child2) {
		if (ModifierSet.isPrivate(((ClassOrInterfaceDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("ClassOrInterface.Modifier\nprivate\n");
		}
		if (ModifierSet.isPublic(((ClassOrInterfaceDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("ClassOrInterface.Modifier\npublic\n");
		}
		if (ModifierSet.isStatic(((ClassOrInterfaceDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("ClassOrInterface.Modifier\nstatic\n");
		}
		if (ModifierSet.isStrictfp(((ClassOrInterfaceDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("ClassOrInterface.Modifier\nstrictfp\n");
		}
		if (ModifierSet.isSynchronized(((ClassOrInterfaceDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("ClassOrInterface.Modifier\nsyncronized\n");
		}
		if (ModifierSet.isTransient(((ClassOrInterfaceDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("ClassOrInterface.Modifier\ntransient\n");
		}
		if (ModifierSet.isVolatile(((ClassOrInterfaceDeclaration) child2).getModifiers())) {
			System.out.println("------------------------------------------------------------");
			System.out.print("ClassOrInterface.Modifier\nvolatile\n");
		}
	}
}