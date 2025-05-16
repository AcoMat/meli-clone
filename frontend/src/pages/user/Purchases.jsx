import LoadingSwitch from "../../components/basic/LoadingSwitch/LoadingSwitch";
import PurchaseHistory from "../../components/user/Purchases/PurchaseHistory";
import usePurchases from "../../hooks/usePurchases";

function Purchases() {
    const { purchases, loading } = usePurchases();

    return (
        <div className="content-wrapper">
            <LoadingSwitch loading={loading}>
                <div className="my-4">
                    <h4 className="mx-2">Tus compras</h4>
                </div>
                <div className="d-flex flex-column">
                    {
                        purchases && purchases?.length > 0 ?
                            purchases.map((purchase) => {
                                return (
                                    <PurchaseHistory key={purchase.date.toString()} purchase={purchase} />
                                )
                            })
                            :
                            <div className="d-flex flex-column text-center justify-content-center bg-body" style={{ height: "250px" }}>
                                <h5>Todavia no compraste nada</h5>
                                <span className="text-secondary">Explora nuevos productos</span>
                            </div>
                    }
                </div>
            </LoadingSwitch>
        </div>
    );
}

export default Purchases;