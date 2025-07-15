import { formatARS } from "../../../util/priceUtil"
import ProductCardHistory from "../../cards/ProductHistory/ProductCardHistory"

export default function PurchaseHistory({purchase, showReviewLink = false}) {

    return (
        <div className="bg-body rounded mb-4">
            <div className="border-bottom px-4 py-3 d-flex justify-content-between align-items-center">
                <h5>{`${purchase.date.split('T')[0]}`}</h5>
                <h5>Total: {formatARS(purchase.total)}</h5>
            </div>
            <div className="border-bottom px-4 py-2">
                {
                    purchase.items.map((item) => {
                        return (
                            <ProductCardHistory
                                key={item.product.id + purchase.id}
                                product={item.product}
                                amount={item.amount}
                                showReviewLink={showReviewLink}
                            />
                        )
                    })
                }
            </div>
        </div>
    )
}