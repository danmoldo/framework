package today.learnjava.repository;

import javax.persistence.*;

import today.learnjava.model.Account;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(readOnly = true)
public class AccountRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Account save(Account account) {
		if (account.getId() == null) {
			entityManager.persist(account);
		}
		else {
			entityManager.merge(account);
			entityManager.flush();
		}
		return account;
	}
	
	public Account findByEmail(String email) {
		try {
			return entityManager.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	@Transactional
	public Account findById(Long id) {
		return entityManager.find(Account.class, id);
	}

	public List<Account> findAll() {
		return entityManager.createQuery("select a from Account a", Account.class).getResultList();
	}

	public List<Account> findAllConfirmedUsers() {
		return entityManager.createQuery("select a from Account a where a.status = 1", Account.class).getResultList();
	}

}
