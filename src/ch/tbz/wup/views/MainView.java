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

import ch.tbz.wup.viewmodels.MainViewModel;
import ch.tbz.wup.viewmodels.PokedexViewModel;

/**
 * Main graphic user interface containing most components and implementing other components.
 */
public class MainView implements IUserInterface {
	private static MainView _instance; //Instance variable for Singleton pattern.
	
	/**
	 * @return  The singleton instance of MainView
	 */
	public static MainView getInstance() {
		if (_instance == null) {
			_instance = new MainView();
		}
		return _instance;
	}
	
	private MainView() {} //private constructor for Singleton pattern
	
	private JFrame _frame; //Base frame
	private JLayeredPane _contentPane;  //Topmost content container
	private JLayeredPane _map; //Container containing all map parts
	private PokedexView _pokedexView; //Custom container for Pokédexview.
	
	//Components which have stationary real coordinates (and thus move when player 'moves')
	private List<Component> _stationaryComponents = new ArrayList<Component>();
	
	/**
	 * Initializes the view using the provided data.
	 * 
	 * @param viewModel  Data required for view initialization.
	 */
	@Override
	public void init(MainViewModel viewModel) {
		initialize(viewModel);
	}
	
	/**
	 * Starts up the UI and displays it.
	 */
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
	
	/**
	 * Returns the base component of the window.
	 */
	@Override
	public JFrame getWindow() {
		return _frame;
	}
	
	/**
	 * Moves the view for the specified delta.
	 * 
	 * @param dX  The location difference along x-axis
	 * @param dY  The location difference along y-axis
	 */
	@Override
	public void moveView(int dX, int dY) {
		//Move map
		_map.setBounds(
			_map.getX() - dX, _map.getY() + dY,
			_map.getWidth(), _map.getHeight());
		
		//Move all stationary (in real coordinates) components
		for (Component component : _stationaryComponents) {
			component.setBounds(
				component.getX() - dX, component.getY() + dY,
				component.getWidth(), component.getHeight());
		}
		
	}
	
	/**
	 * Displays the specified image
	 * 
	 * @param filePath  Path to the image file.
	 * @param dimensions  Dimensions of the image file.
	 * @param rc_point  Where to show image (centered).
	 * @param rc_center  Relative center point.
	 * @return  The Component containing the image.
	 */
	@Override
	public JLabel showImage(String filePath, Rectangle dimensions, Point rc_point, Point rc_center) {
		JLabel image = new JLabel(new ImageIcon(filePath));
		
		Point imageLocation = UiUtils.transform(_frame.getBounds(), rc_point, rc_center);
		image.setBounds(imageLocation.x - (dimensions.width / 2), imageLocation.y - (dimensions.height / 2), dimensions.width, dimensions.height);
		_contentPane.add(image);
		_contentPane.moveToFront(image);
		_stationaryComponents.add(image);
		
		return image;
	}

	/**
	 * Hides the image given
	 * 
	 * @param label  The component to hide.
	 */
	@Override
	public void hideImage(JLabel label) {
		_stationaryComponents.remove(label);
		_contentPane.remove(label);
		_contentPane.moveToBack(label);
		label.setVisible(false);
	}
	
	
	/**
	 * Builds a new PokedexView and displays it.
	 * 
	 * @param pokedex  The Pokedex data to display.
	 */
	@Override
	public void showPokedex(PokedexViewModel pokedex) {
		_pokedexView = new PokedexView(pokedex);
		_contentPane.add(_pokedexView);
		_contentPane.moveToFront(_pokedexView);
	}

	/**
	 * Hides the pokedex.
	 */
	@Override
	public void hidePokedex() {
		_contentPane.moveToBack(_pokedexView);
		_contentPane.remove(_pokedexView);
		_pokedexView.setVisible(false);
	}
	
	//Builds the main view: Sets its properties, builds map and sets the player sprite.
	private void initialize(MainViewModel viewModel) {
		//Set properties of main frame
	    _frame = new JFrame();
	    _frame.setResizable(false); //Window can't be resized by user
	    _frame.setBounds(500, 20, 1000, 1000); //Set window size
	    _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set close action
	    _frame.getContentPane().setLayout(null);
	    
	    //Create map component
	    _map = new JLayeredPane();
	    _map.setName("map");
	    
	    try {
	    	//build map
			MapBuilder builder = new MapBuilder(viewModel);
			_map = builder.buildMap();
			updateMapPosition(viewModel);
			_contentPane = new JLayeredPane();
			_contentPane.setBounds(0, 0, _frame.getWidth(), _frame.getHeight());
			_frame.getContentPane().add(_contentPane);
			
			_contentPane.add(_map);
			setPlayerSprite();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Loads and places the player sprite.
	private void setPlayerSprite() {
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
}
