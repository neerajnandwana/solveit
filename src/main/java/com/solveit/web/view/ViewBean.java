package com.solveit.web.view;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.google.inject.Inject;
import com.solveit.data.Winner;
import com.solveit.data.store.Store;

@ViewScoped
@ManagedBean(name = "store")
public class ViewBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
    private Store store;

	public ViewBean() {}

	public List<Winner> getWinners() {
		return store.getWinners();
    }
}