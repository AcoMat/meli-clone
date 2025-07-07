import LoadingSwitch from "../../components/basic/LoadingSwitch/LoadingSwitch";
import FavoriteProductItemCard from "../../components/cards/FavoriteProductItemCard/FavoriteProductItemCard";
import { useFavoritesContext } from "../../context/FavoritesContext";


function Favorites() {
    const { favorites, loading } = useFavoritesContext();

    return (
        <div className="content-wrapper">
            <LoadingSwitch loading={loading}>
                <div className="my-4">
                    <h4 className="mx-2">Favoritos</h4>
                </div>
                <div className="bg-body p-3 d-flex flex-column">
                    {favorites && favorites.length > 0 ?
                        favorites?.map((product) => {
                            return (
                                <FavoriteProductItemCard key={product.id} product={product} />
                            )
                        })
                        :
                        <div className="d-flex flex-column text-center justify-content-center" style={{ height: "250px" }}>
                            <h5>Aún no tenés productos favoritos</h5>
                            <span className="text-secondary">Agregalos haciendo click en la estrella de la página del producto</span>
                        </div>
                    }
                </div>
            </LoadingSwitch>
        </div>
    );
}

export default Favorites;