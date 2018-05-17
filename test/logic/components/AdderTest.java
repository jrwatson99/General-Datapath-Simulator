package logic.components;

import logic.DataValue;
import logic.Wire;

public class AdderTest {

    private static class connectInputWireTest {

        private static DataValue MAX_VALUE_TO_TEST = new DataValue("10000");
        private static DataValue VALUE_INCREMENT = new DataValue("1000");

        public static void main(String[] args) {
            runAllTests();
            System.out.println("Completed Test: Adder.connectInputWire");
        }

        private static void runAllTests() {
            testInputA();
            testInputB();
        }

        private static void testInputA() {
            for (DataValue i = DataValue.ZERO; i.compareTo(MAX_VALUE_TO_TEST) < 0; i = new DataValue(i.add(VALUE_INCREMENT))) {
                testWithValueInputA(i);
            }
        }

        private static void testInputB() {
            for (DataValue i = DataValue.ZERO; i.compareTo(MAX_VALUE_TO_TEST) < 0; i = new DataValue(i.add(VALUE_INCREMENT))) {
                testWithValueInputB(i);
            }
        }

        private static void testWithValueInputA(DataValue value) {
            testWithValue(value, "inputA");
        }

        private static void testWithValueInputB(DataValue value) {
            testWithValue(value, "inputB");
        }

        private static void testWithValue(DataValue value, String inputName) {
            Wire inputWire = new Wire();
            inputWire.setValue(value);

            Adder adder = new Adder();
            adder.connectInputWire(inputWire, inputName);

            assert(adder.getInputA() != null);
            assert(adder.getInputA().getValue().equals(inputWire.getValue()));
            assert(adder.getInputA().equals(inputWire));
        }
    }

}
