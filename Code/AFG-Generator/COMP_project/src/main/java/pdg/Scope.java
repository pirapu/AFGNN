package pdg;

import java.util.ArrayList;

import graphStructures.VarChanges;
import graphStructures.VarChanges2;

/**
 * The Class Scope.
 */
class Scope {
	
	/** The var changes. */
	ArrayList<VarChanges> varChanges = new ArrayList<>();
	
	/** The var accesses. */
	ArrayList<VarChanges2> varAccesses = new ArrayList<>();
}
