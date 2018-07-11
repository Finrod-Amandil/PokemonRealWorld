package ch.tbz.wup.viewmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for building the PokédexView.
 */
public class PokedexViewModel {
	public int entryCount = 0;
	public List<PokedexEntryViewModel> entries = new ArrayList<PokedexEntryViewModel>();
}
