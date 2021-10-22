import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class CTCIStackOfBoxes {
    public static void main(String[] args) {
        try {
            CTCIStackOfBoxes obj = new CTCIStackOfBoxes();
            obj.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(String[] args) {
        ArrayList<Cube> holdData = new ArrayList<>();
        //5 10 8
        //7 14 9
        //9 2 2
        holdData.add(new Cube(5, 10, 8));
        holdData.add(new Cube(7, 14, 9));
        holdData.add(new Cube(9, 2, 2));
        HashSet<Integer> indexesThere = new HashSet<>();
        for (int x = 0; x < holdData.size(); x++) {
            indexesThere.add(x);
        }
        System.out.println(returnTallestHeight(holdData, new HashMap<String, Integer>(), indexesThere, false, null, holdData.size()));
    }

    public int returnTallestHeight(ArrayList<Cube> holdCubes, HashMap<String, Integer> precomputedHeights, HashSet<Integer> indexesThere, boolean child, Cube parentCube,int totalCubes) {
        boolean badChild = false;
        int maxHeight = 0;
        indexesThere = (HashSet<Integer>) indexesThere.clone();
        for (Iterator<Integer> iterator = indexesThere.iterator();iterator.hasNext();) {
            Integer integer = iterator.next();
            Cube currentCube = holdCubes.get(integer);
            if (currentCube != parentCube) {
                if (precomputedHeights.containsKey(currentCube.height + " " + currentCube.width + " " + currentCube.depth)) {
                    if (precomputedHeights.get(currentCube.height + " " + currentCube.width + " " + currentCube.depth) > maxHeight) {
                        maxHeight = precomputedHeights.get(currentCube.height + " " + currentCube.width + " " + currentCube.depth);
                    }
                }else if(parentCube != null && (currentCube.height > parentCube.height || currentCube.width > parentCube.width || currentCube.depth > parentCube.depth)){
                    iterator.remove();
                    totalCubes--;
                    int currentChildHeight = returnTallestHeight(holdCubes, precomputedHeights, indexesThere, true, currentCube,totalCubes)+currentCube.height;
                    precomputedHeights.put(currentCube.height + " " + currentCube.width + " " + currentCube.depth, currentChildHeight);
                    return 0;
                } else {
                    if(totalCubes == 1){
                        return currentCube.height;
                    }
                    int currentChildHeight = returnTallestHeight(holdCubes, precomputedHeights, indexesThere, true, currentCube,totalCubes);
                    if (!(parentCube == null || (currentCube.height < parentCube.height && currentCube.depth < parentCube.depth && currentCube.width < parentCube.width))) {
                        badChild = true;
                    } else {
                        if (currentChildHeight > maxHeight) {
                            maxHeight = currentChildHeight + currentCube.height;
                        }
                    }
                    if (currentCube.height > maxHeight && !badChild) {
                        maxHeight = currentCube.height;
                    }
                    precomputedHeights.put(currentCube.height + " " + currentCube.width + " " + currentCube.depth,maxHeight);
                }
                if (currentCube.height > maxHeight && !badChild) {
                    maxHeight = currentCube.height;
                }
            }
        }
        if(!badChild){
            return maxHeight;
        }
        return 0;
    }

}

class Cube {
    public int height;
    public int width;
    public int depth;

    public Cube(int height, int width, int depth) {
        this.height = height;
        this.width = width;
        this.depth = depth;
    }
}
