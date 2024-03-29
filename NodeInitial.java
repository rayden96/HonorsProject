import java.util.Random;

public class NodeInitial {
    Boolean isTerminal;

    char value;

    NodeInitial leftChild;
    NodeInitial rightChild;

    Random rand;


    //function set: (arity 2)
    // - A: If food in column up
    // - B: If food in row right
    // - C: If food in column down
    // - D: If food in row left

    // - ifDanger1Up - if danger is in cell 2 - 'E'
    // - ifDanger1Right - if danger is in cell 4 - 'F'
    // - ifDanger1Down - if danger is in cell 6 - 'G'
    // - ifDanger1Left - if danger is in cell 8- 'H'
    char[] functionSet = {'A' , 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    //terminal set:
    // - moveUp - move up - 'U'
    // - moveRight - move right - 'R'
    // - moveDown - move down - 'D'
    // - moveLeft - move left - 'L'
    char[] terminalSet = {'U', 'R', 'D', 'L'};

    public NodeInitial(Boolean isTerminal, Random rand) {
        this.isTerminal = isTerminal;
        leftChild = null;
        rightChild = null;
        this.rand = rand;

        if(isTerminal) {
            value = terminalSet[rand.nextInt(4)];
        }
        else {
            value = functionSet[rand.nextInt(8)];
        }
    }

    public NodeInitial copyNode() {
        if(isTerminal) {
            Random rand2 = new Random(this.rand.nextInt());
            NodeInitial newNode = new NodeInitial(isTerminal, rand2);
            newNode.value = value;
            return newNode;
        }
        else {
            Random rand2 = new Random(this.rand.nextInt());
            NodeInitial newNode = new NodeInitial(isTerminal, rand2);
            newNode.value = value;
            newNode.leftChild = leftChild.copyNode();
            newNode.rightChild = rightChild.copyNode();
            return newNode;
        }
    }

    //mutate the node
    public void mutate(int chance, int depth){
        //randomly change the value if the chance is less than the chance percent + depth
        if(rand.nextInt(100) < chance ){
            if(isTerminal){
                value = terminalSet[rand.nextInt(4)];
            }
            else{
                //small chance to change the node to a terminal, else randomly change the value
                if(rand.nextInt(100) < 30 && depth != 0 && depth != 1 && depth != 2){
                    isTerminal = true;
                    value = terminalSet[rand.nextInt(4)];
                    leftChild = null;
                    rightChild = null;
                }
                else{
                    value = functionSet[rand.nextInt(8)];
                }
            }
        }
        else{
            if(!isTerminal){
                if(rand.nextInt(100)<50){
                    leftChild.mutate(chance, depth + 1);
                }
                else{
                    rightChild.mutate(chance, depth + 1);
                }
            }
        }
        
    }

    public Boolean crossover(NodeInitial node, int chance){
        if (rand.nextInt(100) < chance){
            //replace this node with the other node
            value = node.value;
            isTerminal = node.isTerminal;
            leftChild = node.leftChild;
            rightChild = node.rightChild;
            return true;
        }
        else{
            if(!isTerminal && !node.isTerminal){
                if(this.leftChild.crossover(node.leftChild, chance)){
                    return true;
                }
                else if(this.rightChild.crossover(node.rightChild, chance)){
                    return true;
                }
            }
            return false;
        }
    }

    public String printTree(){
        if(isTerminal){
            if(value == 'U'){
                return "U";
            }
            else if(value == 'R'){
                return "R";
            }
            else if(value == 'D'){
                return "D";
            }
            else{
                return "L";
            }
        }
        else{
            if(value == 'A'){
                return "FU(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'B'){
                return "FR(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'C'){
                return "FD(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'D'){
                return "FL(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'E'){
                return "D1U(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'F'){
                return "D1R(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'G'){
                return "D1D(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else if(value == 'H'){
                return "D1L(" + leftChild.printTree() + ", " + rightChild.printTree() + ")";
            }
            else throw new IllegalArgumentException("Invalid node value");
        }
    }


}
