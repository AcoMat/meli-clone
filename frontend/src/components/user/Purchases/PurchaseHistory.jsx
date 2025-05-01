import ProductCardHistory from "../../cards/ProductHistory/ProductCardHistory"

export default function PurchaseHistory({purchase}) {
    const meses = [
        "enero", "febrero", "marzo", "abril", "mayo", "junio",
        "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
    ];

    return (
        <div className="bg-body rounded mb-4">
            <div className="border-bottom px-4 py-3">
                <span className="fw-medium">{`${purchase.date[2]} de ${meses[purchase.date[1]]} de ${purchase.date[0]}`}</span>
            </div>
            <div className="border-bottom px-4 py-2">
                {
                    purchase.items.map((item) => {
                        return (
                            <ProductCardHistory key={item.product.id + purchase.date} product={item.product}/>
                        )
                    })
                }
            </div>
        </div>
    )
}