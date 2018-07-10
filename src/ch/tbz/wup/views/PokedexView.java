package ch.tbz.wup.views;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.tbz.wup.viewmodels.PokedexEntryViewModel;
import ch.tbz.wup.viewmodels.PokedexViewModel;

public class PokedexView extends JPanel {
	private static final long serialVersionUID = 1L;

	public PokedexView(PokedexViewModel pokedex) {
		initialize(pokedex);
	}
	
	private void initialize(PokedexViewModel pokedex) {
		this.setBounds(185, 100, 630, 810);
		
		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(600, pokedex.entryCount * 65));
		JScrollPane scrollPane = new JScrollPane(container);
		container.setAutoscrolls(true);
		scrollPane.setPreferredSize(new Dimension(620, 800));
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		
		int i = 0;
		for (PokedexEntryViewModel pokedexEntry : pokedex.entries) {
			JPanel entryView = getEntryView(pokedexEntry);
			entryView.setBounds(0, i * 50, 600, 50);
			container.add(entryView);
			
			i++;
		}
		
		this.add(scrollPane);
	}
	
	private JPanel getEntryView(PokedexEntryViewModel pokedexEntry) {
		JPanel container = new JPanel();
		boolean isDiscovered = pokedexEntry.isDiscovered;
		
		JLabel idCmp = isDiscovered ? new JLabel("#" + String.format("%03d", pokedexEntry.id)) : new JLabel("#...");
		container.add(idCmp);
		idCmp.setBounds(0, 0, 50, 50);
		idCmp.setMinimumSize(new Dimension(50, 50));
		idCmp.setPreferredSize(new Dimension(50, 50));
		idCmp.setMaximumSize(new Dimension(50, 50));
		idCmp.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JLabel spriteCmp = isDiscovered ? 
				new JLabel(new ImageIcon("./files/graphics/sprites/pokemon/" + pokedexEntry.id + ".png"))
		     :  new JLabel(new ImageIcon("./files/graphics/sprites/pokemon/unknown.png"));
		container.add(spriteCmp);
		spriteCmp.setBounds(0, 0, 50, 50);
		spriteCmp.setMinimumSize(new Dimension(50, 50));
		spriteCmp.setPreferredSize(new Dimension(50, 50));
		spriteCmp.setMaximumSize(new Dimension(50, 50));
		
		JLabel nameCmp = isDiscovered ? new JLabel(pokedexEntry.speciesName) : new JLabel("???");
		container.add(nameCmp);
		nameCmp.setBounds(0, 0, 200, 50);
		nameCmp.setMinimumSize(new Dimension(200, 50));
		nameCmp.setPreferredSize(new Dimension(200, 50));
		nameCmp.setMaximumSize(new Dimension(200, 50));
		nameCmp.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JLabel type1Cmp = isDiscovered ? new JLabel(pokedexEntry.type1) : new JLabel("");
		container.add(type1Cmp);
		type1Cmp.setBounds(0, 0, 100, 50);
		type1Cmp.setMinimumSize(new Dimension(100, 50));
		type1Cmp.setPreferredSize(new Dimension(100, 50));
		type1Cmp.setMaximumSize(new Dimension(100, 50));
		type1Cmp.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JLabel type2Cmp = isDiscovered ? new JLabel(pokedexEntry.type2) : new JLabel("");
		container.add(type2Cmp);
		type2Cmp.setBounds(0, 0, 100, 50);
		type2Cmp.setMinimumSize(new Dimension(100, 50));
		type2Cmp.setPreferredSize(new Dimension(100, 50));
		type2Cmp.setMaximumSize(new Dimension(100, 50));
		type2Cmp.setFont(new Font("Arial", Font.PLAIN, 18));
		
		return container;
	}
}
