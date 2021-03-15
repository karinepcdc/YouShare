package br.ufrn.imd.pds.business;

import br.ufrn.imd.pds.data.ItemDAOMemory;

public class ItemServices implements FacadeItem {

	ItemDAOMemory itemDatabase; // database manager class

	public ItemServices() {		
		// instantiate database
		itemDatabase = ItemDAOMemory.getInstance();
	}
	
	
	@Override
	public void createItem(String name, String description, String code, String isAvailable, String price) {
		// TODO Auto-generated method stub

	}

	@Override
	public String readItem(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(String code, String campo, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(String code) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addItemReview(String code, int grade, String review) {
		// TODO Auto-generated method stub

	}

}
