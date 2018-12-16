package cz.muni.fi.service;

import cz.muni.fi.persistence.dao.UserDao;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.service.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service layer implementation for User
 *
 * @author Jakub Polacek
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void addUser(User user) throws ServiceException {
        try {
            user.setPasswordHash(createHash(user.getPassword()));
            userDao.addUser(user);
        } catch (Exception e) {
            throw new ServiceException("Could not add user.", e);
        }
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        try {
            userDao.updateUser(user);
        } catch (Exception e) {
            throw new ServiceException("Could not update user.", e);
        }
    }

    @Override
    public void deleteUser(User user) throws ServiceException {
        try {
            userDao.deleteUser(user);
        } catch (Exception e) {
            throw new ServiceException("Could not delete user.", e);
        }
    }

    @Override
    public List<User> getUsersByName(String name) throws ServiceException {
        try {
            if (name == null) {
                throw new IllegalArgumentException("Null name argument.");
            }
            return userDao.getAllUsers().stream().filter(user -> user.getName().equals(name)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Could not get users by name.", e);
        }
    }

    @Override
    public User getUserById(Long id) throws ServiceException {
        try {
            return userDao.getUserById(id);
        } catch (Exception e) {
            throw new ServiceException("Could not get user by id.", e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            return userDao.getAllUsers();
        } catch (Exception e) {
            throw new ServiceException("Could not get all categories.", e);
        }
    }

    @Override
    public boolean authenticate(User u, String password) {
        return validatePassword(password, u.getPasswordHash());
    }

    //see  https://crackstation.net/hashing-security.htm#javasourcecode
    private static String createHash(String password) {
        final int SALT_BYTE_SIZE = 24;
        final int HASH_BYTE_SIZE = 24;
        final int PBKDF2_ITERATIONS = 1000;
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        // Hash the password
        byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validatePassword(String password, String correctHash) {
        if(password==null) return false;
        if(correctHash==null) throw new IllegalArgumentException("password hash is null");
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     *
     * @param a the first byte array
     * @param b the second byte array
     * @return true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
    }
}