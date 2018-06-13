package ch.tbz.wup.views;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import ch.tbz.wup.viewmodels.MainViewModel;

public class MapBuilder {
	private HashMap<Point, JLabel> _mapParts;
	private JLayeredPane _map;
	private MainViewModel _viewModel;
	
	public MapBuilder(MainViewModel viewModel) {
		_viewModel = viewModel;
		_mapParts = new HashMap<Point, JLabel>();
	}
	
	public JLayeredPane buildMap() throws IOException {
		_map = new JLayeredPane();
	    _map.setName("map");
	    Rectangle regionBounds = _viewModel.regionBounds.getBounds();
	    _map.setBounds(0, 0, regionBounds.width, regionBounds.height);
		
		//Get origins (lower left corners) of map parts;
		List<Point> requiredMapPartOrigins = getMapPartOrigins();
		
		//Try loading images
		String filePath = "./files/graphics/region_maps/" + _viewModel.regionName + "/";
		
		if (!Files.isDirectory(Paths.get(filePath))) {
			throw new IOException("No map images found for region " + _viewModel.regionName);
		}
		
		for (Point originPoint : requiredMapPartOrigins) {
			
			String fileName = originPoint.x + "_" + originPoint.y + ".png";
			
			File imageFile = new File(filePath + fileName);
			if (!imageFile.exists()) {
				//Outside map
			}
			else {
				addToMap(imageFile, originPoint);
			}
		}
		
		return _map;
	}
	
	private ArrayList<Point> getMapPartOrigins() {
		ArrayList<Point> mapPartOrigins = new ArrayList<Point>();
		Rectangle regionBounds = _viewModel.regionBounds.getBounds();
		
		Point rc_lowerLeftCornerKm = new Point(
			(int)Math.floor((regionBounds.x)/1000),
			(int)Math.floor((regionBounds.y)/1000));
		
		Point rc_upperRightCornerKm = new Point(
			(int)Math.floor((regionBounds.x + regionBounds.getWidth())/1000),
			(int)Math.floor((regionBounds.y + regionBounds.getHeight())/1000));
		
		for (int x = rc_lowerLeftCornerKm.x; x <= rc_upperRightCornerKm.x; x++) {
			for (int y = rc_lowerLeftCornerKm.y; y <= rc_upperRightCornerKm.y; y++) {
				mapPartOrigins.add(new Point(x, y));
			}
		}
		
		return mapPartOrigins;
	}
	
	private void addToMap(File imageFile, Point originPoint) {
		JLabel tmpLabel;
		try {
			tmpLabel = new JLabel(new ImageIcon(ImageIO.read(imageFile)));;
			_mapParts.put(originPoint, tmpLabel);
			
			Point rc_upperLeftCorner = new Point(
				originPoint.x * 1000,
				(originPoint.y * 1000) + 1000);
			
			Rectangle regionBounds = _viewModel.regionBounds.getBounds();
			
			Point mapCenter = new Point(
				regionBounds.x + (int)(regionBounds.getWidth() / 2),
				regionBounds.y + (int)(regionBounds.getHeight() / 2));
			
			Point wc_upperLeftCorner = UiUtils.transform(_map.getBounds(), rc_upperLeftCorner, mapCenter);
			
			tmpLabel.setBounds(wc_upperLeftCorner.x, wc_upperLeftCorner.y, 1000, 1000);
			tmpLabel.setName(originPoint.x + "_" + originPoint.y);
			
			_map.add(tmpLabel);
			_map.moveToFront(tmpLabel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
