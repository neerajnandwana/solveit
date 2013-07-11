package com.solveit.data.store;

import com.solveit.data.Winner;

import java.util.List;


public interface Store {
	void saveWinner(String winnerName, String ipAddr);
	List<Winner> getWinners();
}
