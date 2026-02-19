package pdg;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import org.jgrapht.DirectedGraph;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;

import graphStructures.GraphNode;
import graphStructures.RelationshipEdge;
import graphStructures.ReturnObject;
import graphStructures.VarChanges;
import graphStructures.VarChanges2;


/**
 * The Class SymbolTable.
 */
class SymbolTable {
    //this array can accept any type of object, will contain classScope,GlobalScope,MethodScope and LoopScope var types
    //overall symbol tables can be accessed through here
    /** The scopes. */
    private ArrayList<Scope> scopes = new ArrayList<>();
    
    /** The pending method declarations. */
    ArrayList<Method> pendingMethodDeclarations = new ArrayList<>();
    
    /** The pending method nodes. */
    ArrayList<MethodNode> pendingMethodNodes = new ArrayList<>();
    
    /** The last class. */
    private ClassScope lastClass = null;
    
    /** The last method. */
    private MethodScope lastMethod = null;
    
    /** The last loop. */
    private LoopScope lastLoop = null;
    
    /** The last scope. */
    private Scope lastScope = null;

    
    boolean FilerON = true;
    

    /**
     * The Class MethodNode.
     */
    class MethodNode{
        
        /** The method. */
        Node method;
        
        /** The class scope. */
        String classScope;
        
        /** The method name. */
        String methodName;
        
        /** The caller method. */
        String 	callerMethod;

        /**
         * Instantiates a new method node.
         *
         * @param node the node
         * @param classScope2 the class scope2
         * @param name the name
         * @param caller the caller
         */
        MethodNode(Node node, String classScope2, String name, String caller) {
            method=node;
            classScope=classScope2;
            methodName=name;
            callerMethod=caller;
        }
    }
    
    /**
     * The Class Method.
     */
    class Method{
        
        /** The method name. */
        String methodName = null;
        
        /** The method scope. */
        String methodScope = null;
        
        /**
         * Instantiates a new method.
         *
         * @param n the n
         * @param s the s
         */
        Method(String n, String s){
            methodName=n;
            methodScope=s;
        }
    }
    
    /**
     * The Class Parameter.
     */
    private class Parameter {
        
        /** The param name. */
        String paramName;
        
        /** The param type. */
        String paramType;
        
        /**
         * Instantiates a new parameter.
         */
        Parameter(){
            paramName = null;
            paramType = null;
        }
    }
    
    /**
     * The Class Variable.
     */
    private class Variable {
        
        /** The var name. */
        String varName;
        
        /** The var type. */
        String varType;
        
        /**
         * Instantiates a new variable.
         */
        Variable(){
            varName = null;
            varType = null;
        }
        
        /**
         * Instantiates a new variable.
         *
         * @param name the name
         * @param type the type
         */
        Variable(String name, String type) {
            varName=name;
            varType=type;
        }
    }
    
    /**
     * The Class Field.
     */
    private class Field {
        
        /** The field name. */
        String fieldName;
        
        /** The field type. */
        String fieldType;
        
        /**
         * Instantiates a new field.
         */
        Field(){
            fieldName=null;
            fieldType=null;
        }
    }

    /**
     * Instantiates a new symbol table.
     */
    SymbolTable(){}

    /**
     * Relevant.
     *
     * @param child2 the child2
     * @return true, if successful
     */
    private boolean relevant(Node child2) {
        return !child2.getClass().equals(com.github.javaparser.ast.body.VariableDeclarator.class) &&
                !child2.getClass().equals(com.github.javaparser.ast.CompilationUnit.class) &&
                !child2.getClass().equals(com.github.javaparser.ast.stmt.ExpressionStmt.class) &&
                !child2.getClass().equals(com.github.javaparser.ast.stmt.BlockStmt.class) &&
                !child2.getClass().equals(com.github.javaparser.ast.type.VoidType.class) &&
                !child2.getClass().equals(com.github.javaparser.ast.type.ClassOrInterfaceType.class);
    }

    /**
     * Prints the symbol table.
     */
    void printSymbolTable(){
        for (Scope scope : scopes) {
            System.out.println("SCOPE " + scope);
            if (scope.getClass() == ClassScope.class) {
                System.out.println(((ClassScope) scope).fieldTable.toString());
                System.out.println(((ClassScope) scope).funcTable.toString());
            }
            if (scope.getClass() == MethodScope.class) {
                System.out.println(((MethodScope) scope).paramTable.toString());
                System.out.println(((MethodScope) scope).localVarTable.toString());
            }
            if (scope.getClass() == LoopScope.class) System.out.println(((LoopScope) scope).localVarTable.toString());
        }
    }

    /**
     * Fill class scope.
     *
     * @param node the node
     * @param classScp the class scope
     */
    private void fillClassScope(Node node,ClassScope classScp){
        classScp.Name = ((ClassOrInterfaceDeclaration)node).getNameExpr().toString();
        classScp.Type=((ClassOrInterfaceDeclaration)node).getExtends().toString();
    }

    /**
     * Adds the class scope.
     *
     * @param cs the class scope
     * @param ls the loop scope
     * @return true, if successful
     */
    private boolean addClassScope(ClassScope cs, ArrayList<Scope> ls){
        if(!scopes.contains(cs)){
            scopes.add(cs);
            ls.add(cs);
            lastClass = cs;
            lastScope = cs;
            return true;
        }
        else return false;
    }

    /**
     * Fill loop scope.
     *
     * @param node the node
     * @param loopScp the loop scope
     */
    private void fillLoopScope(Node node,LoopScope loopScp){
        loopScp.ClassName = lastClass.Name;
        loopScp.MethodName = lastMethod.Name;
        loopScp.loopNode=node;
    }

    /**
     * Adds the loop scope.
     *
     * @param ls the loop scope
     * @param ls1 the scope array
     */
    private void addLoopScope(LoopScope ls, ArrayList<Scope> ls1){
        scopes.add(ls);
        ls1.add(ls);
        lastLoop = ls;
        lastScope = ls;
    }

    /**
     * Fill method scope.
     *
     * @param node the node
     * @param methodScp the method scope
     */
    private void fillMethodScope(Node node,MethodScope methodScp){
        methodScp.Type = ((MethodDeclaration)node).getType().toString();
        methodScp.Name = ((MethodDeclaration)node).getNameExpr().toString();
        methodScp.className = lastClass.Name;
        methodScp.methodNode = node;
    }

    /**
     * Adds the method scope.
     *
     * @param methodScp the method scope
     * @param ls the scope array
     * @return true, if successful
     */
    private boolean addMethodScope(MethodScope methodScp, ArrayList<Scope> ls){
        if(!lastClass.funcTable.contains(methodScp.Name)){
            lastClass.funcTable.put(methodScp.Name, methodScp.Type);
            scopes.add(methodScp);
            ls.add(methodScp);
            lastMethod = methodScp;
            lastScope = methodScp;
            return true;
        }
        return false;
    }

    /**
     * Check pending methods.
     *
     * @param methodScp the method scope
     */
    private void checkPendingMethods(MethodScope methodScp){
        for(int i=0;i<pendingMethodDeclarations.size();i++){
            if(methodScp.className.equals(pendingMethodDeclarations.get(i).methodScope))
                if(methodScp.Name.equals(pendingMethodDeclarations.get(i).methodName)){
                    pendingMethodDeclarations.remove(pendingMethodDeclarations.get(i));
                }
        }
    }

    /**
     * Verify method arguments.
     *
     * @param node the node
     * @param method the method
     * @param callerMethod the caller method
     * @return the string
     */
    private String verifyMethodArguments(Node node,Method method,String callerMethod){
        MethodScope methodScp = null;
        //find MethodScope
        for (Scope scope : scopes) {
            if (scope.getClass().equals(MethodScope.class))
                if (((MethodScope) scope).className.equals(method.methodScope))
                    if (((MethodScope) scope).Name.equals(method.methodName))
                        methodScp = ((MethodScope) scope);
        }
        if(!((MethodCallExpr)node).getArgs().isEmpty())
            if(!methodScp.paramTable.isEmpty())
                if(((MethodCallExpr)node).getArgs().size()!=methodScp.paramTable.size())
                    return "error:Method call of "+methodScp.Name+" in class "+methodScp.className+" has an invalid number of arguments("+((MethodCallExpr)node).getArgs().size()+" instead of "+methodScp.paramTable.size()+")";

        if(((MethodCallExpr)node).getArgs().isEmpty())
            if(!methodScp.paramTable.isEmpty())
                return "error:Method call of "+methodScp.Name+" in class "+methodScp.className+" has an invalid number of arguments(0 instead of "+methodScp.paramTable.size()+")";

        if(methodScp.paramTable.isEmpty())
            if(!((MethodCallExpr)node).getArgs().isEmpty())
                return "error:Method call of "+methodScp.Name+" in class "+methodScp.className+" has an invalid number of arguments("+((MethodCallExpr)node).getArgs().size()+" instead of 0)";

        ArrayList<String> undeclared=new ArrayList<>();

        for(Node child: node.getChildrenNodes()){
            boolean varFound=false;
            if(child.getClass().equals(com.github.javaparser.ast.expr.NameExpr.class)){
                for (Scope scope : scopes) {
                    if (scope.getClass().equals(LoopScope.class)) {
                        if (((LoopScope) scope).MethodName.equals(callerMethod)) {
                            if (((LoopScope) scope).ClassName.equals(lastClass.Name)) {
                                if (((LoopScope) scope).localVarTable.containsKey(child.toString()))
                                    System.out.println("PARENT " + node.getParentNode().toString());
                                varFound = true;
                            }
                        }
                    }

                    if (scope.getClass().equals(MethodScope.class)) {
                        if (((MethodScope) scope).Name.equals(callerMethod)) {
                            if (((MethodScope) scope).paramTable.containsKey(child.toString()))
                                //noinspection UnusedAssignment
                                varFound = true;
                            else if (((MethodScope) scope).localVarTable.containsKey(child.toString()))
                                System.out.println("PARENT " + node.getParentNode().toString());
                            varFound = true;
                        }
                    }
                }
                if(!varFound)
                    undeclared.add(child.toString());
            }
        }

        String returnString = "error:Variables with identifiers:";
        undeclared=assignBinaryExpressionCheck(node);
        if(undeclared.size()>0){
            for(int i=0;i<undeclared.size();i++){
                if(i==0)
                    returnString  = returnString.concat(undeclared.get(i)+" ");
                else returnString  = returnString.concat("and " + undeclared.get(i))+ " ";
                returnString = returnString.concat("in Method:"+callerMethod+" are not declared");
            }

            return returnString;
        }
        return "clear";
    }

    /**
     * Post process method call node.
     *
     * @param node the node
     * @param scope the scope
     * @param methodName the method name
     * @param callerMethod the caller method
     * @return the return object
     */
    ReturnObject postProcessMethodCallNode(Node node, String scope, String methodName, String callerMethod){
        boolean methodFound=false;
        Method method =new Method(methodName,scope);
        //check if this method is defined
        for (Scope scope1 : scopes) {
            if (scope1.getClass() == ClassScope.class) {
                if (((ClassScope) scope1).Name.equals(scope))
                    if (((ClassScope) scope1).funcTable.containsKey(methodName))
                        methodFound = true;
            }
        }

        if(!methodFound)
            return new ReturnObject("clear");
        else{
            String returnVal;
            returnVal=verifyMethodArguments(node, method,callerMethod);
            if(!returnVal.equals("clear"))
                return new ReturnObject(returnVal);
        }
        return new ReturnObject("clear");
    }

    /**
     * Process method call node.
     *
     * @param node the node
     * @return the return object
     */
    private ReturnObject processMethodCallNode(Node node){
        if(((MethodCallExpr)node).getScope()!=null){
            StringTokenizer stock = new StringTokenizer(node.toString(),".");
            boolean methodFound=false;
            String method;
            stock.nextToken();
            method = stock.nextToken();
            stock = new StringTokenizer(method,"(");
            method = stock.nextToken();
            //ignore system method Calls
            if(!((MethodCallExpr)node).getScope().toString().startsWith("System")){
                String classScope;
                if(((MethodCallExpr)node).getScope().toString().startsWith("this"))
                    classScope=lastClass.Name;
                else classScope=((MethodCallExpr)node).getScope().toString();
                Method methodWithScope = new Method(method,classScope);
                //check if this method is defined and determine nr of args
                for (Scope scope : scopes) {
                    if (scope.getClass() == ClassScope.class) {
                        if (((ClassScope) scope).Name.equals(classScope))
                            if (((ClassScope) scope).funcTable.containsKey(method))
                                methodFound = true;
                    }
                }
                if(!methodFound){
                    pendingMethodDeclarations.add(methodWithScope);
                    pendingMethodNodes.add(new MethodNode(node,classScope,method,lastMethod.Name));
                }
                else{
                    String returnVal;
                    returnVal=verifyMethodArguments(node, methodWithScope,lastMethod.Name);
                    if(!returnVal.equals("clear"))
                        return new ReturnObject(returnVal);
                }

            }
        }

        //if not null, it's not an user defined method of current Class scope
        if(((MethodCallExpr)node).getScope()==null){
            boolean methodFound=false;
            StringTokenizer stock = new StringTokenizer(node.toString(),"(");
            String methodName=stock.nextToken();
            Method method =new Method(methodName,lastClass.Name);
            if(lastClass.funcTable.containsKey(methodName))
                methodFound=true;
            if(!methodFound){
                pendingMethodDeclarations.add(method);
                pendingMethodNodes.add(new MethodNode(node,lastClass.Name,methodName,lastMethod.Name));
            }
            else{
                String returnVal;
                returnVal=verifyMethodArguments(node, method,lastMethod.Name);
                if(!returnVal.equals("clear"))
                    return new ReturnObject(returnVal);
            }
        }
        return new ReturnObject("clear");
    }

    /**
     * Adds the parameter.
     *
     * @param node the node
     * @param param the param
     * @return true, if successful
     */
    private boolean addParameter(Node node,Parameter param){
        int i = 0;
        for(Node child: node.getChildrenNodes()){
            if(relevant(child)){
                if(i == 0){
                    param.paramName = child.toString();
                    i++;
                }
                else
                    param.paramType = child.toString();
            }
        }
        if(!lastMethod.paramTable.containsKey(param.paramName)){
            lastMethod.paramTable.put(param.paramName, param.paramType);
            return true;
        }
        return false;
    }

    /**
     * Adds the variable.
     *
     * @param node the node
     * @param var the variable
     * @return the array list
     */
    private ArrayList<Variable> addVariable(Node node,Variable var){
        int i = 0;
        int c = 0;
        ArrayList<Variable> repeatedVars = new ArrayList<>();

        for(Node child: node.getChildrenNodes()){
            if(i == 0){
                var.varType = child.toString();
            }
            if(child.getClass().equals(com.github.javaparser.ast.body.VariableDeclarator.class))
                c=0;
            for(Node child2: child.getChildrenNodes()){
                if(i==1){
                    if(c == 0){
                        var.varName = child2.toString();
                        c++;
                    }
                }
                if(i>1 && child2.getClass().equals(com.github.javaparser.ast.body.VariableDeclaratorId.class)){
                    if(c==0){
                        Variable othervar= new Variable(child2.toString(),var.varType);
                        if(!putVariable(othervar))
                            repeatedVars.add(othervar);
                        c++;
                    }
                }
            }
            i++;
        }
        if(!putVariable(var))
            repeatedVars.add(var);
        return repeatedVars;
    }

    /**
     * Put variable.
     *
     * @param var the variable
     * @return true, if successful
     */
    private boolean putVariable(Variable var){
        if(lastScope.equals(lastMethod)){
            if(!lastMethod.localVarTable.containsKey(var.varName)){
                lastMethod.localVarTable.put(var.varName, var.varType);
                return true;
            }
        }
        else if(lastScope.equals(lastLoop)){
            if(!lastLoop.localVarTable.containsKey(var.varName)){
                lastLoop.localVarTable.put(var.varName, var.varType);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the field.
     *
     * @param node the node
     * @param fld the field
     * @return true, if successful
     */
    private boolean addField(Node node, Field fld){
        int i=0,c = 0;
        for(Node child: node.getChildrenNodes()){
            if(i == 0){
                fld.fieldType = child.toString();
                i++;
            }
            if(i==1){
                for(Node child2: child.getChildrenNodes()){
                    if(c == 0){
                        fld.fieldName = child2.toString();
                        c++;
                    }
                }
            }
        }
        if(!lastClass.fieldTable.containsKey(fld.fieldName)){
            lastClass.fieldTable.put(fld.fieldName, fld.fieldType);
            return true;
        }
        return false;
    }

    /**
     * Assign binary expression check.
     *
     * @param node the node
     * @return the array list
     */
    private ArrayList<String> assignBinaryExpressionCheck(Node node){
        boolean varFound=false;
        ArrayList<String> undeclared = new ArrayList<>();
        for(Node child: node.getChildrenNodes()){
            if(child.getClass().equals(com.github.javaparser.ast.expr.NameExpr.class)){
                if(lastMethod.paramTable.containsKey(child.toString()))
                    varFound=true;
                else if(lastMethod.localVarTable.containsKey(child.toString()))
                    varFound=true;
                else if(lastClass.fieldTable.containsKey(child.toString()))
                    varFound=true;
                for (Scope scope : scopes) {
                    if (scope.getClass().equals(LoopScope.class)) {
                        if (((LoopScope) scope).MethodName.equals(lastMethod.Name)) {
                            if (((LoopScope) scope).ClassName.equals(lastClass.Name)) {
                                if (((LoopScope) scope).localVarTable.containsKey(child.toString()))
                                    varFound = true;
                            }
                        }
                    }
                }
                if(!varFound){
                    undeclared.add(child.toString());
                }
            }
        }
        return undeclared;
    }

    /**
     * Check return.
     *
     * @param node the node
     * @return the return object
     */
    private ReturnObject checkReturn(Node node){

        int i=0;
        boolean varFound = false;
        for(Node child: node.getChildrenNodes()){

            if(child.getClass().equals(com.github.javaparser.ast.expr.NameExpr.class)){
                if(lastMethod.paramTable.containsKey(child.toString())){
                    if(!lastMethod.paramTable.get(child.toString()).equals(lastMethod.Type)){
                        return  new ReturnObject("error:Type of return value -"+lastMethod.paramTable.get(child.toString())+"- doesnt match method's: "+lastMethod.Name+" should return: "+lastMethod.Type+"");
                    }
                    varFound=true;
                }
                if(lastMethod.localVarTable.containsKey(child.toString())){
                    if(!lastMethod.localVarTable.get(child.toString()).equals(lastMethod.Type)){
                        return  new ReturnObject("error:Type of return value -"+lastMethod.localVarTable.get(child.toString())+"- doesnt match method's: "+lastMethod.Name+" should return: "+lastMethod.Type+"");
                    }
                    varFound=true;
                }
                if(!varFound){
                    return  new ReturnObject("error:Variable with identifier "+child.toString()+" is undefined");
                }
            }
            else if(child.getClass().equals(com.github.javaparser.ast.expr.AssignExpr.class)){
                for(Node child2: child.getChildrenNodes()){
                    if(i==0){
                        if(child2.getClass().equals(com.github.javaparser.ast.expr.NameExpr.class)){
                            if(lastMethod.paramTable.containsKey(child2.toString())){
                                if(!lastMethod.paramTable.get(child2.toString()).equals(lastMethod.Type)){
                                    return  new ReturnObject("error:Type of return value -"+lastMethod.paramTable.get(child2.toString())+"- doesnt match method's: "+lastMethod.Name+" should return: "+lastMethod.Type+"");
                                }
                            }
                            if(lastMethod.localVarTable.containsKey(child2.toString())){
                                if(!lastMethod.localVarTable.get(child2.toString()).equals(lastMethod.Type)){
                                    return  new ReturnObject("error:Type of return value -"+lastMethod.localVarTable.get(child2.toString())+"- doesnt match method's: "+lastMethod.Name+" should return: "+lastMethod.Type+"");
                                }
                            }
                        }
                    }
                    i++;
                }
            }
        }
        return null;
    }

    /**
     * Update scopes.
     *
     * @param ls the scope array
     */
    private void updateScopes(ArrayList<Scope> ls){
        if(ls.size()!=0)
            lastScope=ls.get(ls.size()-1);
    }

    /**
     * Semantic node check.
     *
     * @param node the node
     * @param hrefGraph the href graph
     * @param previousNode the previous node
     * @param ls the scope array
     * @param FileOutputStream output file handel
     * @return the return object
     */
    // byte[] strToBytes = str.getBytes(); outputStream.write(strToBytes);
    

    ReturnObject SemanticNodeCheck(Node node, @SuppressWarnings("rawtypes") DirectedGraph<GraphNode, RelationshipEdge> hrefGraph, GraphNode previousNode, ArrayList<Scope> ls, FileOutputStream out) {
        GraphNode nodeToSend = null;
        
        updateScopes(ls);

        if(node.getClass().equals(com.github.javaparser.ast.body.ClassOrInterfaceDeclaration.class)){
            ClassScope classScp = new ClassScope();
            fillClassScope(node,classScp);
            if(!addClassScope(classScp, ls))
                return new ReturnObject("error:repeated class/interface declaration of "+classScp.Name+" ");

            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, false, out);
        }

        else if(node.getClass().equals(com.github.javaparser.ast.stmt.TryStmt.class) || 
                node.getClass().equals(com.github.javaparser.ast.stmt.CatchClause.class) ||
                node.getClass().equals(com.github.javaparser.ast.stmt.AssertStmt.class)){
            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, false, out);
        }

        else if(node.getClass().equals(com.github.javaparser.ast.body.MethodDeclaration.class)){
            MethodScope methodScp = new MethodScope();
            fillMethodScope(node,methodScp);
            checkPendingMethods(methodScp);
            if(!addMethodScope(methodScp, ls))
                return  new ReturnObject("error:repeated method declaration of "+methodScp.Name+", please use different identifiers for methods in same class");

            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, false, out);
            
            // To process method parameters
            for(Node child: node.getChildrenNodes()){
                if(child.getClass().equals(com.github.javaparser.ast.body.Parameter.class)) {
                    for(Node childNode : child.getChildrenNodes()){
                        if(childNode.getClass().equals(com.github.javaparser.ast.body.VariableDeclaratorId.class)) {
                            // addSEEdge(childNode.toString(), nodeToSend, hrefGraph, out);
                            // lastScope.varAccesses.add(new VarChanges(nodeToSend, childNode.toString()));
                            lastScope.varChanges.add(new VarChanges(nodeToSend, childNode.toString()));
                        }
                    }
                }
            }
        }

        else if(node.getClass().equals(com.github.javaparser.ast.body.Parameter.class)){
            Parameter param = new Parameter();
            if(!addParameter(node,param))
                return  new ReturnObject("error:duplicated param identifier  : "+param.paramName+" in Method:"+lastMethod.Name+"");
        }
        // If delt
        else if(node.getClass().equals(com.github.javaparser.ast.stmt.IfStmt.class)){
            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, false, out);
            for(Node child2 : node.getChildrenNodes())
                for(Node child: child2.getChildrenNodes()){
                    if(child.getClass().equals(com.github.javaparser.ast.expr.NameExpr.class)) {
                        // addSEEdge(child.toString(), nodeToSend, hrefGraph, out);
                        lastScope.varAccesses.add(new VarChanges2(node, child.toString()));

                        String variable = child.toString();
                        for (int i1 = scopes.size() - 1; i1 >= 0; i1--) {
                            ArrayList<VarChanges> vc = scopes.get(i1).varChanges;
                            for (VarChanges aVc : vc)
                                if (aVc.getVar().equals(variable))
                                    addEdgeBetweenNodes(aVc.getGraphNode(), nodeToSend, "FD", hrefGraph, out );
                        }
                    }
                }
        }

        else if(node.getClass().equals(com.github.javaparser.ast.expr.UnaryExpr.class)){
            ArrayList<String> undeclared;
            String returnString = "error:Variables with identifiers:";
            undeclared=assignBinaryExpressionCheck(node);
            if(undeclared.size()>0){
                for(int i=0;i<undeclared.size();i++){
                    if(i==0)
                        returnString  = returnString.concat(undeclared.get(i)+" ");
                    else returnString  = returnString.concat("and " + undeclared.get(i))+ " ";
                    returnString = returnString.concat("in Method:"+lastMethod.Name+" are not declared");
                }
                return  new ReturnObject(returnString);
            }
            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, false, out);
            for(Node child: node.getChildrenNodes()){
                if(child.getClass().equals(com.github.javaparser.ast.expr.NameExpr.class)) {
                    // addSEEdge(child.toString(), nodeToSend, hrefGraph, out);
                    lastScope.varAccesses.add(new VarChanges2(node, child.toString()));

                    String variable = child.toString();
                    for (int i1 = scopes.size() - 1; i1 >= 0; i1--) {
                        ArrayList<VarChanges> vc = scopes.get(i1).varChanges;
                        for (VarChanges aVc : vc)
                            if (aVc.getVar().equals(variable))
                                addEdgeBetweenNodes(aVc.getGraphNode(), nodeToSend, "FD", hrefGraph, out );
                    }
                }
            }
        }

        else if(node.getClass().equals(com.github.javaparser.ast.expr.BinaryExpr.class)){
            ArrayList<String> undeclared;
            String returnString = "error:Variables with identifiers:";
            undeclared=assignBinaryExpressionCheck(node);
            if(undeclared.size()>0){
                for(int i=0;i<undeclared.size();i++){
                    if(i==0)
                        returnString  = returnString.concat(undeclared.get(i)+" ");
                    else returnString  = returnString.concat("and " + undeclared.get(i))+ " ";
                    returnString = returnString.concat("in Method:"+lastMethod.Name+" are not declared");
                }

                return  new ReturnObject(returnString);
            }
            nodeToSend = getExpressionNode(node);
            for(Node child: node.getChildrenNodes()){
                if(child.getClass().equals(com.github.javaparser.ast.expr.NameExpr.class)){
                    String variable = child.toString();
                    for(int i1 = scopes.size() - 1; i1 >= 0; i1--) {
                        ArrayList<VarChanges> vc = scopes.get(i1).varChanges;
                        for (VarChanges aVc : vc)
                            if (aVc.getVar().equals(variable))
                                addEdgeBetweenNodes(aVc.getGraphNode(), nodeToSend, "FD", hrefGraph, out);
                        }

                    }
                }
        }
            
        else if (node.getClass().equals(com.github.javaparser.ast.expr.VariableDeclarationExpr.class)) {
            Variable var = new Variable();
            ArrayList<Variable> repeatedOcc;
            repeatedOcc=addVariable(node,var);
            if(repeatedOcc.size()>0){
                String returnstring = "error:duplicated variable declarations:";
                for(int i=0;i<repeatedOcc.size();i++){
                    if(i==0)
                        returnstring  = returnstring.concat(repeatedOcc.get(i).varName+" ");
                    else returnstring  = returnstring.concat("and " + repeatedOcc.get(i).varName)+ " ";
                }
                returnstring = returnstring.concat("in Method:"+lastMethod.Name+"");
                return  new ReturnObject(returnstring);
            }
            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, false, out);

            for(Node child: node.getChildrenNodes()){
                if(child.getClass().equals(com.github.javaparser.ast.body.VariableDeclarator.class)) {
                    for(Node childNode : child.getChildrenNodes()){
                        if(childNode.getClass().equals(com.github.javaparser.ast.body.VariableDeclaratorId.class)) {
                            // addSEEdge(childNode.toString(), nodeToSend, hrefGraph, out);
                            // lastScope.varAccesses.add(new VarChanges(nodeToSend, childNode.toString()));
                            lastScope.varChanges.add(new VarChanges(nodeToSend, childNode.toString()));
                        }
                    }
                }
            }

        }

        else if (node.getClass().equals(com.github.javaparser.ast.body.FieldDeclaration.class)) {
            Field fld= new Field();
            if(!addField(node,fld))
                return  new ReturnObject("error:duplicated fields: "+fld.fieldName+" in class : "+lastClass.Name+"");

            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, false, out);
        }

        else if (node.getClass().equals(com.github.javaparser.ast.stmt.ForStmt.class)) {
            LoopScope loopScp = new LoopScope();
            fillLoopScope(node,loopScp);
            addLoopScope(loopScp, ls);

            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, true, out);
            analyseVariablesInLoop(node, hrefGraph, nodeToSend, loopScp, out);
        }

        else if (node.getClass().equals(com.github.javaparser.ast.stmt.DoStmt.class)) {
            LoopScope loopScp = new LoopScope();
            fillLoopScope(node,loopScp);
            addLoopScope(loopScp, ls);

            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, true, out);
            analyseVariablesInLoop(node, hrefGraph, nodeToSend, loopScp, out);
        }

        else if (node.getClass().equals(com.github.javaparser.ast.stmt.WhileStmt.class)) {
            LoopScope loopScp = new LoopScope();
            fillLoopScope(node,loopScp);
            addLoopScope(loopScp, ls);

            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, true, out);
            analyseVariablesInLoop(node, hrefGraph, nodeToSend, loopScp, out);
        }

        else if(node.getClass().equals(com.github.javaparser.ast.expr.MethodCallExpr.class)){
            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, false, out);
            for(Node childNode : node.getChildrenNodes()) {
                if(childNode.getClass().equals(com.github.javaparser.ast.expr.NameExpr.class)){
                    String variable = childNode.toString();
                    Expression objectName = ((com.github.javaparser.ast.expr.MethodCallExpr)node).getScope();
                    if(objectName != null && objectName.toString().equals(variable)){
                        addSEEdge(childNode.toString(), node, hrefGraph, out);
                    }
                    lastScope.varAccesses.add(new VarChanges2(node, childNode.toString()));

                    for(int i = scopes.size() - 1; i >= 0; i--) {
                        ArrayList<VarChanges> vc = scopes.get(i).varChanges;
                        for (VarChanges aVc : vc) {
                            if (aVc.getVar().equals(variable)) {
                                addEdgeBetweenNodes(aVc.getGraphNode(), nodeToSend, "FD", hrefGraph, out );
                            }
                        }
                    }
                }
            }
            return processMethodCallNode(node);
        }

        else if(node.getClass().equals(com.github.javaparser.ast.expr.FieldAccessExpr.class)){
            String scope;
            if(!((FieldAccessExpr) node).getScope().toString().startsWith("System")){
                if(((FieldAccessExpr) node).getScope().toString().startsWith("this"))
                    scope=lastClass.Name;
                else scope=((FieldAccessExpr) node).getScope().toString();
                for (Scope scope1 : scopes) {
                    if (scope1.getClass() == ClassScope.class) {
                        if (((ClassScope) scope1).Name.equals(scope))
                            if (!((ClassScope) scope1).fieldTable.containsKey(((FieldAccessExpr) node).getField()))
                                return new ReturnObject("error:Field with identifier " + ((FieldAccessExpr) node).getField() + " in Method " + lastMethod.Name + " is not declared");

                    }
                }
            }
        }

        else if(node.getClass().equals(com.github.javaparser.ast.expr.AssignExpr.class)){
            ArrayList<String> undeclared;
            String returnString = "error:Variables with identifiers:";
            undeclared=assignBinaryExpressionCheck(node);
            if(undeclared.size()>0){
                for(int i=0;i<undeclared.size();i++){
                    returnString = returnString.concat("in Method:"+lastMethod.Name+" are not declared");
                }
            }
            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, false, out);
            int counter = 0;
            for(Node child: node.getChildrenNodes()){
                if(child.getClass().equals(com.github.javaparser.ast.expr.NameExpr.class)){

                    if(counter == 0){
                        // addSEEdge(child.toString(), nodeToSend, hrefGraph, out);
                        // lastScope.varAccesses.add(new VarChanges(nodeToSend, child.toString()));
                        lastScope.varChanges.add(new VarChanges(nodeToSend, child.toString()));
                    } else {
                        // addSEEdge(child.toString(), nodeToSend, hrefGraph, out);
                        lastScope.varAccesses.add(new VarChanges2(node, child.toString()));
                    }

                    String variable = child.toString();
                    for(int i1 = scopes.size() - 1; i1 >= 0; i1--) {
                        ArrayList<VarChanges> vc = scopes.get(i1).varChanges;
                        for (VarChanges aVc : vc)
                            if (aVc.getVar().equals(variable))
                                addEdgeBetweenNodes(aVc.getGraphNode(), nodeToSend, "FD", hrefGraph, out);
                    }
                }
                else if(child.getClass().equals(com.github.javaparser.ast.expr.BinaryExpr.class)){
                    for(Node childNode : child.getChildrenNodes()) {
                        if(childNode.getClass().equals(com.github.javaparser.ast.expr.NameExpr.class)){

                            if(counter == 0) {
                                // addSEEdge(childNode.toString(), nodeToSend, hrefGraph, out);
                                // lastScope.varAccesses.add(new VarChanges(nodeToSend, childNode.toString()));
                                lastScope.varChanges.add(new VarChanges(nodeToSend, childNode.toString()));
                            } else {
                                // addSEEdge(childNode.toString(), nodeToSend, hrefGraph, out);
                                lastScope.varAccesses.add(new VarChanges2(node, childNode.toString()));
                            }

                            String variable = childNode.toString();
                            for(int i1 = scopes.size() - 1; i1 >= 0; i1--) {
                                ArrayList<VarChanges> vc = scopes.get(i1).varChanges;
                                for (VarChanges aVc : vc)
                                    if (aVc.getVar().equals(variable))
                                        addEdgeBetweenNodes(aVc.getGraphNode(), nodeToSend, "FD", hrefGraph, out);
                            }
                        }
                    }
                }
                counter++;
            }
        }

        else if(node.getClass().equals(com.github.javaparser.ast.stmt.ReturnStmt.class)){
            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, false, out);
            ReturnObject returnObject;
            if((returnObject=checkReturn(node))!=null)
                return returnObject;
            return new ReturnObject(nodeToSend);
        }

        else if(node.getClass().equals(com.github.javaparser.ast.expr.NameExpr.class)){
            nodeToSend = addNodeAndEdgeToGraph(node, hrefGraph, previousNode, false, out);
            String variable = node.toString();
            for(int i1 = scopes.size() - 1; i1 >= 0; i1--) {
                ArrayList<VarChanges> vc = scopes.get(i1).varChanges;
                for (VarChanges aVc : vc)
                    if (aVc.getVar().equals(variable))
                        addEdgeBetweenNodes(aVc.getGraphNode(), nodeToSend, "FD", hrefGraph, out);
            }
            
        }
        return new ReturnObject(nodeToSend);
    }

    private void addSEEdge(String variable, Node to_node, DirectedGraph<GraphNode, RelationshipEdge> hrefGraph, FileOutputStream out) {
        all_scope: for (int i = scopes.size() - 1; i >= 0; i--) {
            ArrayList<VarChanges2> vc = scopes.get(i).varAccesses;
            for (int j = vc.size() - 1; j >= 0; j--) {
                VarChanges2 aVc = vc.get(j);
                String lhs = aVc.getVar().equals(variable)
                        && aVc.getGraphNode().getClass().equals(com.github.javaparser.ast.expr.MethodCallExpr.class)
                        && ((com.github.javaparser.ast.expr.MethodCallExpr) aVc.getGraphNode()).getScope() != null
                                ? ((com.github.javaparser.ast.expr.MethodCallExpr) aVc.getGraphNode()).getScope()
                                        .toString()
                                : "";
                if (lhs.equals(variable)) {
                    GraphNode node1 = getExpressionNode(aVc.getGraphNode());
                    GraphNode node2 = getExpressionNode(to_node);
                    addEdgeBetweenNodes(node1, node2, "SE", hrefGraph, out);
                    break all_scope;
                }
            }
        }
    }

    /**
     * Analyze variables in loop.
     *
     * @param node the node
     * @param hrefGraph the href graph
     * @param nodeToSend the node to send
     * @param loopScp the loop scope
     */
     //need to look at this to figure out handling method express or function calls primarily and nested loops as well secondary 
    private void analyseVariablesInLoop(Node node, @SuppressWarnings("rawtypes") DirectedGraph<GraphNode, RelationshipEdge> hrefGraph,
                                        GraphNode nodeToSend, LoopScope loopScp, FileOutputStream out) {
        loopScp.gn = nodeToSend;
        loopScp.node = node;

        for(Node child : node.getChildrenNodes())
            if(child.getClass().equals(com.github.javaparser.ast.expr.BinaryExpr.class))
                for(Node childNode : child.getChildrenNodes())
                    if(childNode.getClass().equals(com.github.javaparser.ast.expr.NameExpr.class)){

                        // addSEEdge(childNode.toString(), nodeToSend, hrefGraph, out);
                        lastScope.varAccesses.add(new VarChanges2(node, childNode.toString()));

                        String variable = childNode.toString();
                        for(int i = scopes.size() - 1; i >= 0; i--) {
                            ArrayList<VarChanges> vc = scopes.get(i).varChanges;
                            vc.stream().filter(aVc -> aVc.getVar().equals(variable)).forEach(aVc -> addEdgeBetweenNodes(aVc.getGraphNode(), nodeToSend, "FD", hrefGraph, out));
                        }
                    }
    }

    private void writeTOFile(String S, FileOutputStream out){
        byte[] strToBytes = S.getBytes(); 
        try {
            out.write(strToBytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Adds the edge between nodes.
     *
     * @param graphNode the graph node
     * @param node the node
     * @param string the string
     * @param hrefGraph the href graph
     */
    @SuppressWarnings("rawtypes")
	private void addEdgeBetweenNodes(GraphNode graphNode, GraphNode node, String string, DirectedGraph<GraphNode, RelationshipEdge> hrefGraph, FileOutputStream out) {
        try{
            // For Debugging
            // System.out.println("$$$$ addEdgeBetweenNodes Called");
            // System.out.println(">> try " + graphNode.getData() + " --> " + node.getData() + "[ " + string + " ]");
            // System.out.println("##<< graphNodeClass " + graphNode.getNodeClass());
            // System.out.println("##<<  node.getclass " + node.getNodeClass());

            hrefGraph.addVertex(graphNode);
            hrefGraph.addVertex(node);
            hrefGraph.addEdge(graphNode, node, new RelationshipEdge(string));

            String s1 = graphNode.getData();
            String s2 = node.getData();
            String s1tokens[]=s1.split("\\R",2);
            String s2tokens[]=s2.split("\\R",2);
            String s = s1tokens[0]+"-->"+s2tokens[0]+ "[ " + string + " ]\n";
            // System.out.println(s);
            writeTOFile(s, out);
            // System.out.println("$$$$ addEdgeBetweenNodes Done Successfully");
        } catch (Exception e) {
            System.out.println("ERROR in graph - " + e.getMessage());
            e.printStackTrace();
            
        }
    }

    // This method is to consider complete line by looking at STMT node in parents
    private GraphNode getExpressionNode(Node node) {
        GraphNode newNode = new GraphNode(node.getBeginLine(), node.toString(), node.getClass().toString());
        Node parentNode = node;
        while (parentNode != null) {
            if ((com.github.javaparser.ast.stmt.Statement.class).isAssignableFrom(parentNode.getClass()) ||
                (com.github.javaparser.ast.stmt.CatchClause.class).isAssignableFrom(parentNode.getClass())) {
                newNode.setId(parentNode.getBeginLine());
                String parentNodeCode = parentNode.toString().strip();
                newNode.ReAssign(parentNodeCode);
                break;
            }
            parentNode = parentNode.getParentNode();
        }
        return newNode;
    }

    /**
     * Adds the node and edge to graph.
     *
     * @param node the node
     * @param hrefGraph the href graph
     * @param previousNode the previous node
     * @param loop the loop
     * @param out filehandle
     * @return the graph node
     */
    private GraphNode addNodeAndEdgeToGraph(Node node, @SuppressWarnings("rawtypes") DirectedGraph<GraphNode, RelationshipEdge> hrefGraph,
                                            GraphNode previousNode, boolean loop,FileOutputStream out) {
        GraphNode nodeToSend = null;
        try{
            //GraphNode newNode = new GraphNode(node.getBeginLine(), node.toString(), node.getClass().toString());
            // hrefGraph.addVertex(newNode);
            GraphNode newNode = getExpressionNode(node);
            // System.out.println("$$ addNodeAndEdgeToGraph Called");
            
            if(previousNode == null){
                // System.out.println("!! previousNode == null ");
                nodeToSend = newNode;
            }
            nodeToSend = newNode;

            if(newNode.getNodeClass().equals("class com.github.javaparser.ast.stmt.IfStmt")
            ||newNode.getNodeClass().equals("class com.github.javaparser.ast.body.MethodDeclaration")
            ||newNode.getNodeClass().equals("class com.github.javaparser.ast.body.ClassOrInterfaceDeclaration")
            ||newNode.getNodeClass().equals("class com.github.javaparser.ast.stmt.WhileStmt")
            ||newNode.getNodeClass().equals("class com.github.javaparser.ast.stmt.ForStmt")
            ){
                //prune the node data into simplified form
                String block = newNode.getNodeInfo();
                // System.out.println("block: "+ block);
                String[] arrSplit = block.split("\\R",2);
                newNode.ReAssign(arrSplit[0]);
                // System.out.println("After ReAassign:" + newNode.getNodeInfo());
            }

            //System.out.println("##>>  newNodeClass " + newNode.getNodeClass());
            //System.out.println("##>> PrevNodeClass " + previousNode.getNodeClass());
            hrefGraph.addVertex(previousNode);
            hrefGraph.addVertex(newNode);
            hrefGraph.addEdge(previousNode, newNode);

            String s1 = previousNode.getData();
            String s2 = newNode.getData();
            String s1tokens[]=s1.split("\\R",2);
            String s2tokens[]=s2.split("\\R",2);
            String S = s1tokens[0]+"-->"+s2tokens[0]+"[ CD ]\n";
            //System.out.println(S);
            writeTOFile(S, out);

            if(loop){
                hrefGraph.addEdge(newNode, newNode);
                s1 = previousNode.getData();
                s1tokens = s1.split("\\R",2);
                String S1 = s1tokens[0]+"-->"+s1tokens[0]+"[ CD ]\n";
                //System.out.println(S1);
                writeTOFile( S1, out);
            }
            //System.out.println("$$ addNodeAndEdgeToGraph Done Successfully");   
        } catch (Exception e) {
            //System.out.println("ERROR in graph - " + e.getMessage());
            //e.printStackTrace();
        }
        return nodeToSend;
    }

    /**
     * Adds the dependencies.
     *
     * @param hrefGraph the href graph
     */
    void addDependencies(@SuppressWarnings("rawtypes") DirectedGraph<GraphNode, RelationshipEdge> hrefGraph, FileOutputStream out) {
        scopes.stream().filter(scope -> scope instanceof LoopScope).forEach(scope -> {
            LoopScope ls = (LoopScope) scope;
            (ls.node.getChildrenNodes()).stream().filter(child -> child.getClass().equals(BinaryExpr.class)).forEach(child -> child.getChildrenNodes().stream().filter(childNode -> childNode.getClass().equals(NameExpr.class)).forEach(childNode -> {
                String variable = childNode.toString();
                ls.varChanges.stream().filter(vc -> vc.getVar().equals(variable)).forEach(vc -> addEdgeBetweenNodes(vc.getGraphNode(), ls.gn, "FD", hrefGraph, out));
            }));
            for (VarChanges2 va : ls.varAccesses){
                GraphNode to_node = getExpressionNode(va.getGraphNode());
                ls.varChanges.stream().filter(vc -> va.getVar().equals(vc.getVar())).forEach(vc -> addEdgeBetweenNodes(vc.getGraphNode(), to_node, "FD", hrefGraph, out));
            }
        });
    }

}
