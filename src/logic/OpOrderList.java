package logic;

import logic.components.ALU;

import java.util.ArrayList;
import java.util.List;

public class OpOrderList {

    private ArrayList<OpOrderObject> opOrderObjects;

    private class OpOrderObject {
        private ALU.Operation operation;
        private int position;

        public OpOrderObject(ALU.Operation operation, String positionInBinary) {
            this.operation = operation;
            this.position = Integer.parseInt(positionInBinary, 2);
        }

        public int getPosition() { return position;}
        public ALU.Operation getOperation() { return operation;}
    }

    public void addOpOrderObject(ALU.Operation operation, String positionInBinary) { opOrderObjects.add(new OpOrderObject(operation, positionInBinary));}

    public OpOrderList() {
        opOrderObjects = new ArrayList<OpOrderObject>();
    }

    private void sortOpOrderObjects() {
        int i = 1;
        while (i < opOrderObjects.size()) {
            while (i > 0 && opOrderObjects.get(i).getPosition() < opOrderObjects.get(i - 1).getPosition()) {

                opOrderObjects.add(i - 1, opOrderObjects.remove(i));
                i--;
            }

            i++;
        }
    }

    public ArrayList<ALU.Operation> createALUOpOrder(int bitLength) {
        sortOpOrderObjects();
        ArrayList<ALU.Operation> opOrder = new ArrayList<ALU.Operation>();

        int i = 0;
        while (i < opOrderObjects.size()) {
            while (opOrder.size() < opOrderObjects.get(i).getPosition()) {
                opOrder.add(ALU.Operation.INVALID);
            }
            opOrder.add(opOrderObjects.get(i).getOperation());
            i++;
        }

        long sizeForBitLength = Math.round(Math.pow(2, bitLength));

        while (opOrder.size() < sizeForBitLength) {
            opOrder.add(ALU.Operation.INVALID);
        }

        return opOrder;
    }
}
