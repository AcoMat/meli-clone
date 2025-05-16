import HorizontalProductCard from "../../components/cards/HorizontalProductCard/HorizontalProductCard";

function Favorites() {
    return (
        <div className="content-wrapper">
            {
                !user ?
                    <p className="mx-auto">Cargando...</p>
                    :
                    <>
                        <div className="my-4">
                            <h4 className="mx-2">Favoritos</h4>
                        </div>
                        <div className="bg-body p-3 d-flex flex-column">
                            {user.likedproducts.length > 0 ?
                                user.likedproducts.map((product) => {
                                    return (
                                        <HorizontalProductCard key={product.id} product={product} onClick={() => handleProductClick(product.id)} />
                                    )
                                })
                                :
                                <div className="d-flex flex-column text-center justify-content-center" style={{ height: "250px" }}>
                                    <h5>Aún no tenés productos favoritos</h5>
                                    <span className="text-secondary">Agregalos haciendo click en la estrella de la página del producto</span>
                                </div>
                            }
                        </div>
                    </>
            }
        </div>
    );
}

export default Favorites;