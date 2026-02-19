package graphStructures;

import com.github.javaparser.ast.Node;

/**
 * The Class VarChanges.
 */
public class VarChanges2 {
    
    /** The where. */
    private Node where;
    
    /** The var. */
    private String var;

    /**
     * Instantiates a new var changes.
     *
     * @param gn the graph node
     * @param v the variable
     */
    public VarChanges2(Node gn, String v) {
        this.where = gn;
        this.var = v;
    }

    /**
     * Gets the graph node.
     *
     * @return the graph node
     */
    public Node getGraphNode() {
        return this.where;
    }
    
    /**
     * Gets the var.
     *
     * @return the variable
     */
    public String getVar() {
        return this.var;
    }
}
