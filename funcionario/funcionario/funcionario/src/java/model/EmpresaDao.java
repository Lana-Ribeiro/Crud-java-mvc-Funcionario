package model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

public class EmpresaDao {

    private EntityManager manager;

    private void conectar() {
        manager = Persistence.createEntityManagerFactory("funcionarioPU").createEntityManager();
    }

    public <T> int alterar(T obj) {
        conectar();
        try {
            manager.getTransaction().begin();
            T objetoAtualizado = manager.merge(obj);
            manager.getTransaction().commit();
            return 1;
        } catch (RollbackException erro) {
            return 0;
        } catch (Exception erro) {
            manager.getTransaction().rollback();
            return 2;
        } finally {
            manager.close();
        }
    }

    public enum SaveResult {
        SUCESSO, CHAVEDUPLICADA, ERRO
    }

    public <T> SaveResult salvar(T obj) {
        conectar();
        try {
            manager.getTransaction().begin();
            manager.persist(obj);
            manager.getTransaction().commit();
            return SaveResult.SUCESSO;

        } catch (RollbackException erro) { //duplicação de PK
            return SaveResult.CHAVEDUPLICADA;

        } catch (Exception erro) {
            return SaveResult.ERRO;
        }
    }

    public <T> List listar(String queryNomeada, Class<T> classe) {
        conectar();

        try {
            return manager.createNamedQuery(queryNomeada, classe).getResultList();

        } catch (NoResultException erro) {
            return null;
        }
    }

    public <T> T buscar(Class<T> classe, Object id) {
        conectar();

        try {
            return manager.find(classe, id);
        } catch (Exception x) {
            return null;
        }
    }

    public <T> int excluir(Object pk, Class<T> classe) {
        conectar();

        try {
            T obj = manager.find(classe, pk);
            if (obj == null) { //Não encontrou
                return 2;
            } else { //Encontrou
                manager.getTransaction().begin();
                manager.remove(obj); //Excluiu
                manager.getTransaction().commit();
                return 1;
            }
        } catch (Exception erro) {
            erro.printStackTrace();
            return 0;
        }
    }
}
