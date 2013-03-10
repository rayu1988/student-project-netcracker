package ua.netcrackerteam.DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.netcrackerteam.configuration.HibernateUtil;
import ua.netcrackerteam.configuration.ShowHibernateSQLInterceptor;

import javax.interceptor.Interceptors;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class DAOCommonImpl implements DAOCommon{
    @Override
    @Interceptors(ShowHibernateSQLInterceptor.class)
    public void setUser(String userName,
                        String userPassword,
                        String userEmail,
                        String active,
                        int idUserCategory) throws SQLException{
        Session session = null;
        Transaction transaction = null;
        try {
            Locale.setDefault(Locale.ENGLISH);
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            UserList userList = new UserList();
            userList.setUserName(userName);
            userList.setPassword(userPassword);
            userList.setEmail(userEmail);
            userList.setActive(active);
            //Filipenko
            //---------
            //userList.setIdUserCategory(idUserCategory);
            //+++++++++
            userList.setIdUserCategory(getUserCategoryByID(idUserCategory, session));
            session.save(userList);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    @Interceptors(ShowHibernateSQLInterceptor.class)
    public void deleteUserByName(String userName) {
        Session session = null;
        Query re = null;
        Transaction transaction = null;
        try {
            Locale.setDefault(Locale.ENGLISH);
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            String hql = "delete from UserList where userName = '" + userName + "'";
            Query query = session.createQuery(hql);
            int row = query.executeUpdate();
            if (row == 0){
                System.out.println("Doesn't deleted any row!");
            }
            else{
                System.out.println("Deleted Row: " + row);
            }
            session.close();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Interceptors(ShowHibernateSQLInterceptor.class)
    public static List getUser() throws SQLException {
        Session session = null;
        Query re = null;
        List listOfForms = null;
        try {
            Locale.setDefault(Locale.ENGLISH);
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            re = session.createQuery("from UserList");
            listOfForms = re.list();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return listOfForms;
    }

    @Interceptors(ShowHibernateSQLInterceptor.class)
    public static UserCategory getUserCategoryByID(int currUserCategoryID, Session session) throws SQLException {
        //Session session = null;
        Query re = null;
        List listOfCategories = null;
        UserCategory currUserCategory = new UserCategory();
        try {
            Locale.setDefault(Locale.ENGLISH);
            //session = HibernateUtil.getSessionFactory().getCurrentSession();
            //session.beginTransaction();
            re = session.createQuery("from UserCategory where idUSerCategory = '" + currUserCategoryID + "'");
            listOfCategories = re.list();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            /*if (session != null && session.isOpen()) {
                session.close();
            }*/
        }
        currUserCategory = (UserCategory) listOfCategories.get(0);
        return currUserCategory;
    }

    @Override
    @Interceptors(ShowHibernateSQLInterceptor.class)
    public void addSomethingNew(Object newData) {
        Session session = null;
        Transaction transaction = null;
        try {
            Locale.setDefault(Locale.ENGLISH);
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(newData);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    @Interceptors(ShowHibernateSQLInterceptor.class)
    public List getUserByName(String userName) throws SQLException {
        Session session = null;
        Query re = null;
        List listOfUsers = null;
        try {
            Locale.setDefault(Locale.ENGLISH);
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            re = session.createQuery("from UserList where upper(userName) ='" + userName.toUpperCase() + "'");
            listOfUsers = re.list();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return listOfUsers;
    }

    @Override
    public void resetOnNewEmail(String userName, String userEmail){
        Session session = null;
        Query re = null;
        UserList userList = null;
        Transaction transaction = null;
        try {
            Locale.setDefault(Locale.ENGLISH);
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            re = session.createQuery("from UserList where upper(userName) ='" + userName.toUpperCase() + "'");
            userList = (UserList) re.uniqueResult();
            userList.setEmail(userEmail);
            session.save(userList);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
