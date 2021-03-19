

/*
Written By Arif Meighan
08/03/2021
 */
//Generate a grid with random weights, Then create Minimum Spanning Tree.



//TODO CHECK USAGE OF MATH IS FINE

import java.util.Arrays;
import java.util.Random;

public class Maze {


    int[] componentRep;
    MatrixGraph MG;
    private int width,height;

    private int exit, entrance;



    Maze(int width, int height){
        Random r = new Random();
        this.width = width;
        this.height = height;
        exit = r.nextInt(height);
        entrance = r.nextInt(height);

        componentRep = new int[width*height];
        MG = new MatrixGraph(width*height,false);

        for(int i = 0; i < height*width;i++){
            for(int j = 0; j < height*width;j++){

                if(i+width == j){ //Handles down connections
                    double num = r.nextDouble();
                    MG.addEdge(i,j,num);
                }
                if(j == (i+1)){ //Adds the "right connections" for nodes,
                    if((i+1) % width !=0){ //Unless you are at the edge
                        double num = r.nextDouble();
                        MG.addEdge(i,j,num);

                    }
                }
            }
        }
    }


    MatrixGraph GetMatrixGraph(){
        return MG;
    }



    void initalizeComponents(){ //Initalizes the representatives in the array to be themselves.
        for (int i = 0; i < height*width; i++) {
            componentRep[i] = i;
        }
    }


    void mergeComponents(int x, int y){ //merges in terms of vertexes, Once all one number it is MST
        int mergeRep = componentRep[y]; //check representative at location y,
        int newRep = componentRep[x]; //Changes it to rep at x

        for(int i=0; i<componentRep.length; i++){
            if(componentRep[i] == mergeRep)
            {
                componentRep[i] = newRep;
            }
        }
    }

    boolean CheckComponentMerge(int x, int y){
        if(componentRep[x] == componentRep[y]){
            return true;
        }
        return false;
    }

    boolean MSTsolved(int[] array) //checks if the array contains all the same Components.
    {
        int initial = array[0];
     for(int i = 0; i<array.length;i++)
     {
        if(array[i] != initial){
            return false;
        }
     }
     return true;
    }


    void spanningTree(){
        initalizeComponents();
        MatrixGraph MinimumSpanningMaze = new MatrixGraph(height*width, false); //Will replace maze at end,

        /*
        We want to continually find shortest weight,
        that has a connection to a component
        but not the same component on the other end though
        we check if they have the same component, by looking at their index on the ComponentRep Array
        If equal, they are in the same component, otherwise add the edge and merge the endpoint to old component,
         */

        while(!MSTsolved(componentRep)){ //repeat until no edges are found,
        for(int i = 0; i < componentRep.length; i++){
            double lowestWeight = Double.MAX_VALUE; //check all possible connections
            int lowestNeighbour = Integer.MAX_VALUE;
            int[] neigbours = MG.neighbours(i); //Get all neighbours of the point i,

            for(int j = 0; j<neigbours.length; j++){
                if(MG.weight(i,neigbours[j]) < lowestWeight && !Double.isNaN(MG.weight(i,neigbours[j]))){//finds lowest weight,
                    if(!CheckComponentMerge(i,neigbours[j])){ //Checks if it is part of the same component
                        lowestWeight = MG.weight(i,neigbours[j]); //If not, it will obtain the weight, and location
                        lowestNeighbour = neigbours[j];
                    }
                }
            }
            if(lowestWeight != Double.MAX_VALUE){ //If there was a point found,
                mergeComponents(i,lowestNeighbour); //It will merge it to the component,
                MinimumSpanningMaze.addEdge(i,lowestNeighbour,lowestWeight); //and add it to the Maze.

                this.MG.deleteEdge(i,lowestNeighbour); //Removing old vertex from old maze

            }
            if(MSTsolved(componentRep)){
                break;
            }
        }
        }

        this.MG = MinimumSpanningMaze;
    }





    void print(){ // Utilizes a stringbuilder for performance (it runs faster my smarter friend told me)
        StringBuilder SB = new StringBuilder();
        SB.append("Maze Output:\n");
        SB.append("Entrance: "+(entrance+1)+"\n");
        SB.append("Exit:"+(exit+1)+"\n");
        int[] BottomConnections = new int[width];
        int columncount = 0;
        int row = 0;
        Arrays.fill(BottomConnections,0);


        for(int i = 0; i < height*width;i++){
            if(row == entrance && (i+width) % width == 0){
                SB.append("*");
            }else if(row == exit && (i+1) % width == 0){
                SB.append("*");
            } else{
                SB.append("+");
            }
            columncount++;

            if(i != height*width-1) {
                if (!Double.isNaN(MG.weight(i,i + 1))) {
                    SB.append(" - ");
                } else {
                    SB.append("   ");
                }
            }

            if(i < (width*height - width)){ // if not bottom

                if(MG.weight(i,i + width) > 0){
                    BottomConnections[columncount-1] = 1;
                }
            }



            if(columncount == width && i != height*width-1){
                SB.append("\n");
                columncount = 0;
                for (int b = 0; b < BottomConnections.length; b++)
                {
                 if(BottomConnections[b] == 0){
                     SB.append("    ");

                 }else{
                     SB.append("|   ");

                 }
                 BottomConnections[b] = 0;
                }
                row++;
                SB.append("\n");
            }

        }


        System.out.println(SB.toString());
    }

    public static void main(String[] args) {
        Maze Maze1 = new Maze(10,10); //Cycles appearing in these
        MatrixGraph MG = Maze1.GetMatrixGraph();
        Maze1.spanningTree();
        Maze1.print();


    }

}










