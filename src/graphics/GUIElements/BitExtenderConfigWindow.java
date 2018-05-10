package graphics.GUIElements;

import graphics.ComponentGraphics.BitExtenderGraphic;
import graphics.ComponentGraphics.ComponentGraphic;
import javafx.scene.control.TextField;
import logic.components.BitExtender;

public class BitExtenderConfigWindow extends ConfigWindow{
    private BitExtenderGraphic componentGraphic;


    public BitExtenderConfigWindow(String title, BitExtenderGraphic componentGraphic) {
        super(title);
        this.componentGraphic = componentGraphic;
        this.addInputLine(new InputLine("Name"));
        this.addInputLine(new InputLine("Input Bit Width"));
        this.addInputLine(new InputLine("Output Bit Width"));
    }

    @Override
    public void updateComponent() throws Exception {
        if (componentGraphic.getComponent() instanceof BitExtender) {
            if (getOutWidth() > getInWidth()) {
                ((BitExtender) componentGraphic.getComponent()).setInputBitLength(getInWidth());
                ((BitExtender) componentGraphic.getComponent()).setOutputBitLength(getOutWidth());
                componentGraphic.setName(getName());
            }
            else {
                throw new Exception("Output width must be greater than input width!");
            }
        }
    }

    @Override
    protected void closeWindow() {
        try {
            updateComponent();
            close();
        } catch (NumberFormatException e1) {
            new AlertWindow("Non Integer Number Detected");
        }
        catch( Exception e2) {
            new AlertWindow("Invalid Bit Data:  " + e2.getMessage());
            e2.printStackTrace();
        }
    }

    public String getName() {
        return ((TextField)getLayout().getChildren().get(1)).getText();
    }
    public int getInWidth() {
        return Integer.parseInt(((TextField)getLayout().getChildren().get(3)).getText());
    }
    public int getOutWidth() { return Integer.parseInt(((TextField)getLayout().getChildren().get(5)).getText()); }
}