package dados.genericos;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public abstract class DaoGenerico<Entidade> implements IDaoGenerico<Entidade>{

	protected EntityManagerFactory entityManagerFactory;
	protected Class<Entidade> classePersistente;
	
	@SuppressWarnings("unchecked")
	public DaoGenerico(EntityManagerFactory emf){
		this.entityManagerFactory = emf;
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();  
	    classePersistente = (Class<Entidade>) parameterizedType.getActualTypeArguments()[0];  
	}
	
	public final void alterar(Entidade entidade) {
		EntityManager em = this.getEntityManagerFactory().createEntityManager();

		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			 
			entidade = em.merge(entidade);;
			
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()){
				tx.rollback();
			}
		}
	}

	public final void inserir(Entidade entidade) {
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();		
		try {
			tx.begin();
			em.persist(entidade);
			tx.commit();
			em.close();
		} catch (PersistenceException e) {
			tx.rollback();
		}
	}


	public final void inserirColecao(Collection<Entidade> colecao) {
		
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();

			for (Entidade entidade : colecao) {
				em.persist(entidade);	
			}
			
			tx.commit();
			
			em.close();
			System.out.println(classePersistente.getSimpleName() + " salvos com sucesso: " + colecao.size());
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()){
				tx.rollback();
			}
		}
	}

	public final void remover(Entidade entidade) {
		EntityManager em = this.entityManagerFactory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
	
			entidade = em.merge(entidade);
			em.remove(entidade);
			
			tx.commit();
			
			System.out.println(classePersistente.getSimpleName() + " removido com sucesso");
		} catch (Exception e){
			e.printStackTrace();
			if (tx != null && tx.isActive()){
				tx.rollback();
			}
		}
	}

	
	public final Entidade consultarPorId(Integer chave) {
		Entidade instance = null;
		EntityManager em = this.entityManagerFactory.createEntityManager();
		
		try {
			instance = (Entidade) em.find(classePersistente, chave);
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		return instance;
	}

	public List<Entidade> consultarTodos() {
		List<Entidade> instance = null;
		EntityManager em = this.entityManagerFactory.createEntityManager();
		try {
			instance = ((List<Entidade>) em.createQuery(
					"from " + getPersistentClass().getName()).getResultList());
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		
		em.close();
		return instance;
	}

	public List<Entidade> consultarTodos(Integer indiceInicial,
			Integer quantidade) {
		
		List<Entidade> instance = null;
		EntityManager em = this.entityManagerFactory.createEntityManager();
		try {
			instance = ((List<Entidade>) em.createQuery(
					"from " + getPersistentClass().getName()).setMaxResults(quantidade).getResultList());
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		
		em.close();
		return instance;
	}

	
	/**
	 * Utilizado para se injetar o Entity manager no DAO.
	 * 
	 * @param entityManager
	 *            entity manager
	 */
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public EntityManagerFactory getEntityManagerFactory() {		
		return entityManagerFactory;
	}
	
	/**
	 * Busca a classe persistente do objeto utilizado na classe.
	 * 
	 * @return classe persistente
	 */
	protected final Class<Entidade> getPersistentClass() {
		return classePersistente;
	}

		
}
