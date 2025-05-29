import ProductCardHistory from "../../cards/ProductHistory/ProductCardHistory"

export default function PurchaseHistory({purchase}) {

    return (
        <div className="bg-body rounded mb-4">
            <div className="border-bottom px-4 py-3">
                <span className="fw-medium">{`${purchase.date}`}</span>
            </div>
            <div className="border-bottom px-4 py-2">
                {
                    purchase.items.map((item) => {
                        return (
                            <ProductCardHistory key={item.product.id + purchase.id} product={item.product}/>
                        )
                    })
                }
            </div>
        </div>
    )
}