package teste;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dados.DaoEstabelecimento;
import dados.IDaoEstabelecimento;
import model.Estabelecimento;

public class Teste {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		
		IDaoEstabelecimento daoEstabelecimento = null;
		
		//INSERT INTO (codigo, razaoSocial, nomeFantasia, eMail, senha, cnpj, Endereco, latiutude, logitude )
		Estabelecimento estabelecimento = new Estabelecimento(
				null, "SuperAdmin", "Admin", "admin@admin.com.br", "admin321", "19291136000126", null, "", "");
		
			try {
				emf = Persistence.createEntityManagerFactory("unitPSC");
				em = emf.createEntityManager();
				
				daoEstabelecimento = new DaoEstabelecimento();
				daoEstabelecimento.inserir(estabelecimento);				
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			if (em != null){
				em.close();
			} 
			if (emf != null){
				emf.close();
		}		
		
	}

}
