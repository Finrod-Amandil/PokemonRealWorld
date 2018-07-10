package ch.tbz.wup.views;

import java.awt.Component;
import java.awt.Dimension;

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
		this.setBounds(100, 100, 800, 800);
		
		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(100, 500));
		JScrollPane scrollPane = new JScrollPane(container);
		container.setAutoscrolls(true);
		scrollPane.setPreferredSize(new Dimension(100, 200));
		container.add(getEntryView(pokedex.entries.get(0)));
		
		this.add(scrollPane);
	}
	
	private Component getEntryView(PokedexEntryViewModel pokedexEntry) {
		JPanel container = new JPanel();
		container.setBounds(0, 0, 100, 50);
		JLabel nameCmp = new JLabel(pokedexEntry.speciesName);
		container.add(nameCmp);
		nameCmp.setBounds(0, 0, 100, 50);
		
		return container;
	}
}
