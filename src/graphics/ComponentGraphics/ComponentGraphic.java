package graphics.ComponentGraphics;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import logic.ExecutionEnvironment;
import logic.components.Component;

public abstract class ComponentGraphic {
	private Text name;
	protected Component component;
	
	public abstract void updateLoc(double x, double y);
	
	public void updateTextLoc(double x, double y) {
		name.setX(x);
		name.setY(y);
	}
	public abstract void updateWireText();
	public ComponentGraphic() {
		this.name=new Text();
	}

	public abstract Shape[] getGraphics();
	
	public abstract Text[] getValueText();

	public abstract Component getComponent();

	public String getName() {
		return name.getText();
	}
	
	public Text getText() {return name;}
	
	public void setName(String name) {
		this.name.setText(name);
	}

	public abstract void config();

	public abstract void addMouseHandler();

    protected class ComponentGraphicMouseHandler implements EventHandler<MouseEvent> {

        private ContextMenu menu;
        private Shape[] graphics;

        public ComponentGraphicMouseHandler(ContextMenu menu, Shape... graphics) {
            this.menu = menu;
            this.graphics = graphics;
        }

        @Override
        public void handle(MouseEvent e) {
            if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                updateLoc(e.getX(), e.getY());
            }
            else if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                if(e.getButton()== MouseButton.PRIMARY) {
                    //do we want anything here? maybe highlight it?
//                        	config();
                }
                else {
                    menu.show(graphics[0], e.getX(),e.getY());
                }
            }
        }
    }

    protected ContextMenu createContextMenu() { //this should probably be moved to componentgraphic class
        MenuItem[] items;
        MenuItem del = createDeleteMenuItem();

        if (!(this instanceof WireJunctionGraphic)) {
            MenuItem cfg = createConfigMenuItem();
            items = new MenuItem[] {cfg, del};
        }
        else {
            items = new MenuItem[] {del};
        }

        return new ContextMenu(items);
    }

    private MenuItem createConfigMenuItem() {
        MenuItem cfg = new MenuItem("Config");
        cfg.setOnAction(e -> config());

        return cfg;
    }

    private MenuItem createDeleteMenuItem() {
        MenuItem del = new MenuItem("Delete");
        del.setOnAction(e -> delete());

        return del;
    }

    private void delete() {
        removeOutputLines();
        removeFromParent();
    }

    private void removeFromParent() {
        Pane parentPane = getParentPane();
        parentPane.getChildren().removeAll(getGraphics());
        parentPane.getChildren().removeAll(getValueText());
        parentPane.getChildren().removeAll(getText());
    }

    protected abstract void removeOutputLines();

    protected Pane getParentPane() {
        Pane parentPane;
        parentPane = ExecutionEnvironment.get().getDataPathWindow().getPane();

        return parentPane;
    }
}
