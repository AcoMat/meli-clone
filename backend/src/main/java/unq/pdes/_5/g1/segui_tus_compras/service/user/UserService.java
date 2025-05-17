package unq.pdes._5.g1.segui_tus_compras.service.user;

import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.exception.UserNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.product.Review;
import unq.pdes._5.g1.segui_tus_compras.model.purchase.Purchase;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;

import java.util.List;

@Service
public class UserService {

    private final UsersRepository _usersRepository;

    public UserService(UsersRepository usersRepository) {
        this._usersRepository = usersRepository;
    }

    public User getUserById(Long id) {
        return _usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User updateUser(User user) {
        if (!_usersRepository.existsById(user.getId())) {
            throw new UserNotFoundException("User not found");
        }
        return _usersRepository.save(user);
    }

    public List<Product> getUserFavorites(Long userId) {
        User user = getUserById(userId);
        return user.getFavorites();
    }

    public List<Purchase> getUserPurchases(Long userId) {
        User user = getUserById(userId);
        return user.getPurchases();
    }

    public List<Review> getUserReviews(Long userId) {
        User user = getUserById(userId);
        return user.getReviews();
    }
}
