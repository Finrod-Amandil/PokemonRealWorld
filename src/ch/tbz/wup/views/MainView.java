package ch.tbz.wup.views;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import ch.tbz.wup.viewmodels.MainViewModel;

public class MainView implements IUserInterface {
	private static MainView _instance;
	
	public static MainView getInstance() {
		if (_instance == null) {
			_instance = new MainView();
		}
		return _instance;
	}
	
	private MainView() {}
	
	private JFrame _frame;
	private JLayeredPane _contentPane;
	private JLayeredPane _map;
	
	private List<Component> _stationaryComponents = new ArrayList<Component>();
	
	@Override
	public void init(MainViewModel viewModel) {
		initialize(viewModel);
	}
	
	@Override
	public void show() {
		EventQueue.invokeLater(new Runnable() {
	      public void run() {
	        try {
	          _frame.setVisible(true);
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      }
	    });
	}
	
	@Override
	public JFrame getWindow() {
		return _frame;
	}
	
	@Override
	public void moveView(int dX, int dY) {
		_map.setBounds(
			_map.getX() - dX, _map.getY() + dY,
			_map.getWidth(), _map.getHeight());
		
		for (Component component : _stationaryComponents) {
			component.setBounds(
				component.getX() - dX, component.getY() + dY,
				component.getWidth(), component.getHeight());
		}
		
	}
	
	private void initialize(MainViewModel viewModel) {
		//Set properties of main frame
	    _frame = new JFrame();
	    _frame.setResizable(false); //Window can't be resized by user
	    _frame.setBounds(500, 20, 1000, 1000); //Set window size
	    _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set close action
	    _frame.getContentPane().setLayout(null);
	    
	    _map = new JLayeredPane();
	    _map.setName("map");
	    
	    try {
			MapBuilder builder = new MapBuilder(viewModel);
			_map = builder.buildMap();
			updateMapPosition(viewModel);
			_contentPane = new JLayeredPane();
			_contentPane.setBounds(0, 0, _frame.getWidth(), _frame.getHeight());
			_frame.getContentPane().add(_contentPane);
			
			_contentPane.add(_map);
			setPlayerSprite();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setPlayerSprite() {
		JLabel crossHair = new JLabel(new ImageIcon("./files/graphics/sprites/player/crosshair.png"));
		crossHair.setBounds(_frame.getWidth()/2 - 50, _frame.getHeight()/2 - 50, 100, 100);
		_contentPane.add(crossHair);
		_contentPane.moveToFront(crossHair);
		
		JLabel circle = new JLabel(new ImageIcon("./files/graphics/sprites/pokemon/0.png"));
		circle.setBounds(_frame.getWidth()/2 - 24, _frame.getHeight()/2 - 24, 48, 48);
		_contentPane.add(circle);
		_contentPane.moveToFront(circle);
		
		JLabel playerSprite = new JLabel(new ImageIcon("./files/graphics/sprites/player/front_border.png"));
		playerSprite.setBounds(_frame.getWidth()/2 - 15, _frame.getHeight()/2 -15, 30, 30);
		_contentPane.add(playerSprite);
		_contentPane.moveToFront(playerSprite);
	}
	
	private void updateMapPosition(MainViewModel viewModel) {
		Rectangle regionBounds = viewModel.regionBounds.getBounds();
		
		//Map is positioned by giving the coordinates of the top left corner relative to the containing JFrame.
		Point rc_upperLeftCorner = new Point(
			regionBounds.x,
			regionBounds.y + regionBounds.height); //regionBounds.y is the lower corner
		
		//Transform position relative to center of JFrame which is equal to player position in real coordinates.
		Point wc_upperLeftCorner = UiUtils.transform(_frame.getBounds(), rc_upperLeftCorner, viewModel.playerLocation);
		
		_map.setBounds(wc_upperLeftCorner.x, wc_upperLeftCorner.y, regionBounds.width, regionBounds.height);
	}

	@Override
	public JLabel showImage(String filePath, Rectangle dimensions, Point rc_point, Point rc_center) {
		JLabel image = new JLabel(new ImageIcon(filePath));
		
		Point imageLocation = UiUtils.transform(_frame.getBounds(), rc_point, rc_center);
		image.setBounds(imageLocation.x - (dimensions.width / 2), imageLocation.y - (dimensions.height / 2), dimensions.width, dimensions.height);
		_contentPane.add(image);
		_contentPane.moveToFront(image);
		_stationaryComponents.add(image);
		
		/*JLabel circleImage = new JLabel(new ImageIcon("./files/graphics/sprites/pokemon/0.png"));
		circleImage.setBounds(imageLocation.x - (dimensions.width / 2), imageLocation.y - (dimensions.height / 2), dimensions.width, dimensions.height);
		_contentPane.add(circleImage);
		_contentPane.moveToFront(circleImage);
		_stationaryComponents.add(circleImage);*/
		
		return image;
	}

	@Override
	public void hideImage(JLabel label) {
		_stationaryComponents.remove(label);
		_contentPane.remove(label);
		_contentPane.moveToBack(label);
		label.setVisible(false);
	}
}
