package graphStructures;

import java.util.Objects;

/**
 * The Class GraphNode.
 */
public class GraphNode {
    
    /** The info. */
    private String info;
    
    /** The id. */
    private int id;

    /** The exporting. */
    public static boolean exporting = false;

    private String nodeClass;

    /**
     * Instantiates a new graph node.
     *
     * @param id the id
     * @param string the string
     */
    public GraphNode(int id, String string, String nodeClass) {
        this.info = string;
        this.id = id;
        this.nodeClass = nodeClass;
    }

    /* Gives a ditonary like output of th line number and string in it*/
    public String getData(){
        String ID = Integer.toString(this.id);
        String outS = "Line_" + ID;
        outS += " $$ ";
        outS += this.info;
        return outS; 
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if(!exporting)
            return ("[" + this.id + "] " + this.info);
        return "Line_" + this.id;
        //return "Line_"+ this.info;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!GraphNode.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final GraphNode other = (GraphNode) obj;
        return !(!Objects.equals(this.info, other.info) || other.info == null);
    }

    public String getNodeClass() {
        return this.nodeClass;
    }

    public String getNodeInfo(){
        return this.info;
    }

    public void ReAssign(String infor){
        this.info = infor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}