import { useUserContext } from "../context/AuthContext";
import PurchaseHistory from "../components/user/Purchases/PurchaseHistory";

function Purchases() {
    const { user } = useUserContext();

    return (
        <div className="content-wrapper">
            {
                !user ?
                    <p className="mx-auto">Cargando...</p>
                    :
                    <>
                        <div className="my-4">
                            <h4 className="mx-2">Tus compras</h4>
                        </div>
                        <div className="d-flex flex-column">
                            {
                                user.purchaseHistory.length > 0 ?
                                user.purchaseHistory.map((purchase) => {
                                    return (
                                        <PurchaseHistory key={purchase.date.toString()} purchase={purchase}/>
                                    )
                                })
                                    :
                                    <div className="d-flex flex-column text-center justify-content-center bg-body" style={{ height: "250px" }}>
                                        <h5>Todavia no compraste nada</h5>
                                        <span className="text-secondary">Explora nuevos productos</span>
                                    </div>
                            }
                        </div>
                    </>
            }
        </div>
    );
}

export default Purchases;