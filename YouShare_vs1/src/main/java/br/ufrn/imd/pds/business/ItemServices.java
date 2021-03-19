package br.ufrn.imd.pds.business;

import br.ufrn.imd.pds.data.ItemDAOMemory;
import br.ufrn.imd.pds.exceptions.BusinessException;
import br.ufrn.imd.pds.exceptions.DataException;
import br.ufrn.imd.pds.exceptions.ReadItemFromDatabaseException;

public class ItemServices implements FacadeItem {

	ItemDAOMemory itemDatabase; // database manager class

	public ItemServices() throws DataException, ReadItemFromDatabaseException {		
		// instantiate database
		itemDatabase = ItemDAOMemory.getInstance();
	}
	
	
	/*
	 * TODO Exception in Business layer: 
	 * - cadastros são responsaveis por fazerem validações de regras de negócio e lançar exceções correspondentes
	 * - Fachadas lançam excessões relacionadas a delimitações de transações com o banco de dados
	 * - Todas as excessões lançadas pelas camadas de Business e Data são repassadas para a camada GUI
	 */
	
	@Override
	public void createItem(String name, String description, String code, String isAvailable, String price) throws DataException {

		// validate item
		
		// create user // TODO do that in the controller latter???
		Tool newTool = new Tool("Electric sander3", "Good electric sander", "0223", 0.0, 0, "none yet", true, 12, "dont spoil", "220");
		
		// require item registration in the database
		itemDatabase.createItem( newTool );
	}

	@Override
	public String readItem(String code) throws BusinessException, DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(String code, String campo, String value) throws BusinessException, DataException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(String code) throws BusinessException, DataException {
		// TODO Auto-generated method stub

	}

	/*
	@Override
	public void addItemReview(String code, int grade, String review) {
		// TODO Auto-generated method stub

	}
	*/

}
