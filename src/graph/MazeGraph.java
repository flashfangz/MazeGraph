package graph;
import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/** 
 * <P>The MazeGraph is an extension of WeightedGraph.  
 * The constructor converts a Maze into a graph.</P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	/** 
	 * <P>Construct the MazeGraph using the "maze" contained
	 * in the parameter to specify the vertices (Junctures)
	 * and weighted edges.</P>
	 * 
	 * <P>The Maze is a rectangular grid of "junctures", each
	 * defined by its X and Y coordinates, using the usual
	 * convention of (0, 0) being the upper left corner.</P>
	 * 
	 * <P>Each juncture in the maze should be added as a
	 * vertex to this graph.</P>
	 * 
	 * <P>For every pair of adjacent junctures (A and B) which
	 * are not blocked by a wall, two edges should be added:  
	 * One from A to B, and another from B to A.  The weight
	 * to be used for these edges is provided by the Maze. 
	 * (The Maze methods getMazeWidth and getMazeHeight can
	 * be used to determine the number of Junctures in the
	 * maze. The Maze methods called "isWallAbove", "isWallToRight",
	 * etc. can be used to detect whether or not there
	 * is a wall between any two adjacent junctures.  The 
	 * Maze methods called "getWeightAbove", "getWeightToRight",
	 * etc. should be used to obtain the weights.)</P>
	 * 
	 * @param maze to be used as the source of information for
	 * adding vertices and edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {
		for(int x=0;x<maze.getMazeWidth();x++) {
			for(int y=0;y<maze.getMazeHeight();y++) {
				Juncture junc = new Juncture(x,y);
				this.addVertex(junc);
			}
		}
		for(int x=0;x<maze.getMazeWidth();x++) {
			for(int y=0;y<maze.getMazeHeight();y++) {
				Juncture junc = new Juncture(x,y);
				if(!maze.isWallAbove(junc)) {
					Juncture junc2 = new Juncture(x,y-1);
					int weight = maze.getWeightAbove(junc);
					this.addEdge(junc, junc2, weight);
					
				}
				if(!maze.isWallBelow(junc)) {
					Juncture junc2 = new Juncture(x,y+1);
					int weight = maze.getWeightBelow(junc);
					this.addEdge(junc, junc2, weight);
					
				}
				if(!maze.isWallToRight(junc)) {
					Juncture junc2 = new Juncture(x+1,y);
					int weight = maze.getWeightToRight(junc);
					this.addEdge(junc, junc2, weight);
					
				}
				if(!maze.isWallToLeft(junc)) {
					Juncture junc2 = new Juncture(x-1,y);
					int weight = maze.getWeightToLeft(junc);
					this.addEdge(junc, junc2, weight);
					
				}
			}
		}
	}
}
