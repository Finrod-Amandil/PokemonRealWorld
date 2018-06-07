package ch.tbz.wup.ui;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import ch.tbz.wup.Player;
import ch.tbz.wup.Region;

public class MainUi implements IUserInterface {
	private static MainUi _instance;
	
	public static MainUi getInstance() {
		return _instance != null ? _instance : new MainUi();
	}
	
	private JFrame _frame;
	private JLayeredPane _contentPane;
	private JLayeredPane _map;
	
	private Player _player;
	private Region _region;
	
	private MainUi() {}
	
	public void init(Player player, Region region) {
		_player = player;
		_region = region;
		initialize();
	}
	
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
	
	public JFrame getWindow() {
		return _frame;
	}
	
	private void initialize() {
		//Set properties of main frame
	    _frame = new JFrame();
	    _frame.setResizable(false); //Window can't be resized by user
	    _frame.setBounds(500, 20, 1000, 1000); //Set window size
	    _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set close action
	    _frame.getContentPane().setLayout(null);
	    
	    _map = new JLayeredPane();
	    _map.setName("map");
	    
	    try {
			MapBuilder builder = new MapBuilder(_region);
			_map = builder.buildMap();
			updateMapPosition();
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
		JLabel playerSprite = new JLabel(new ImageIcon("./files/graphics/sprites/player/front_border.png"));
		playerSprite.setBounds(_frame.getWidth()/2, _frame.getHeight()/2, 24, 25);
		_contentPane.add(playerSprite);
		_contentPane.moveToFront(playerSprite);
	}
	
	private void updateMapPosition() {
		Rectangle regionBounds = _region.getBounds().getBounds();
		
		//Map is positioned by giving the coordinates of the top left corner relative to the containing JFrame.
		Point rc_upperLeftCorner = new Point(
			regionBounds.x,
			regionBounds.y + regionBounds.height); //regionBounds.y is the lower corner
		
		//Transform position relative to center of JFrame which is equal to player position in real coordinates.
		Point wc_upperLeftCorner = UiUtils.transform(_frame.getBounds(), rc_upperLeftCorner, _player.getLocation());
		
		_map.setBounds(wc_upperLeftCorner.x, wc_upperLeftCorner.y, regionBounds.width, regionBounds.height);
	}
}
