//  © 2K26 ❱──💀──❰ pat_mic ? code is life : life is code
package BusinessLogic;

import DataAccess.Interfaces.IDAO;
import Infrastructure.AppException;
import java.util.List;

public class FactoryBL<T> {
    private final IDAO<T> dao;

    public FactoryBL(Class<? extends IDAO<T>> daoClass) {
        try {
            this.dao = daoClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(
                new AppException("Error al instanciar DAO", e, getClass(), "FactoryBL")
            );
        }
    }

    public List<T> getAll() throws AppException {
        return dao.readAll();
    }

    public T getById(Integer id) throws AppException {
        return dao.readBy(id);
    }

    public boolean add(T entity) throws AppException {
        return dao.create(entity);
    }

    public boolean update(T entity) throws AppException {
        return dao.update(entity);
    }

    public boolean delete(Integer id) throws AppException {
        return dao.delete(id);
    }

    public Integer count() throws AppException {
        return dao.getMaxReg(); // en tu helper esto es COUNT(*)
    }
}

