package today.learnjava.repository;

import today.learnjava.model.ContactMessage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ContactMessageRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public ContactMessage save (ContactMessage contactMessage) {
        entityManager.persist(contactMessage);
        return contactMessage;
    }

    public List<ContactMessage> findAll() {
        return entityManager.createQuery("select c from ContactMessage c", ContactMessage.class).getResultList();
    }

}
