package decision.game.factory;

import decision.game.SNFIGame;
import decision.game.profile.ActionProfile;
import decision.game.profile.Profile;

public class PQRExample extends SNFIGame<Integer> {
	
	public PQRExample() {
		
	}

	@Override
	public int nPlayers() {
		return 2;
	}

	@Override
	public Profile<Integer> nActions() {
		return new Profile<>(3,2);
	}

	@Override
	public int nWorlds() {
		return 3;
	}
	
	public int signal(int player, int world) {
		switch (world) {
		case 0: // P is the murderer
			switch (player) {
			case 0: return 0;
			case 1: return 1;
			}
		case 1: // Q is the murderer
			switch (player) {
			case 0: return 1;
			case 1: return 1;
			}
		case 2: // R is the murderer
			switch (player) {
			case 0: return 1;
			case 1: return 0;
			}
		}
		throw new IllegalArgumentException("The player " + player + " and/or the world " + world + "is invalid.");
	}

	@Override
	public Profile<Integer> utility(ActionProfile p, int world) {
		Profile<Integer> u = new Profile<>();
		for (int i = 0 ; i < 2 ; i ++) {
			if (p.get(i) == world) {
				u.add(0);
			}
			else {
				if (p.get(0) == p.get(1)) {
					u.add(2);
				}
				else {
					u.add(3);
				}
			}
		}
		return u;
	}
	
	

}
