package ch.tbz.wup;

import java.awt.Point;

import ch.tbz.wup.ui.MainUi;

public class PokemonStarter {

	public static void main(String[] args) {
		//CoordinateConverter.convertPointFromWGS84toLV03(8, 20);
		
		//KmlParser.readAreas("C:\\Users\\severin.zahler\\Desktop\\test.kml");
		
		Player player = Player.getInstance();
		player.setLocation(new Point(683570, 246830));
		Region region = new Region("zurich", null);
		
		MainUi ui = new MainUi(player, region);
		ui.show();
	}
}
