import { Link } from "react-router-dom";

export default function TopProducts({ title, topProducts, concept }) {
    return (
        <div className="bg-body rounded shadow-sm d-flex flex-column gap-3 p-4 my-4 w-100">
            <h2 className="text-center border-bottom pb-3">{title}</h2>
            <div className="d-flex flex-column justify-content-center gap-4">
                {topProducts && topProducts.length > 0 ? (
                    <table className="table">
                        <thead className="text-center">
                            <tr>
                                <th scope="col">Top n°</th>
                                <th scope="col">Producto</th>
                                <th scope="col">{concept}</th>
                            </tr>
                        </thead>
                        <tbody>
                            {topProducts.map((top, index) => (
                                <tr key={top.productId || index} className="align-middle">
                                    <th className="text-center" scope="row">{index + 1}</th>
                                    <td>
                                        <Link to={`/product/${top.productId}`} className="d-flex align-items-center text-decoration-none">
                                            <img
                                                style={{ maxHeight: "80px", maxWidth: "80px" }}
                                                src={top.firstPicture}
                                                className="img-fluid me-3" />
                                            <p className="mb-0">{top.name}</p>
                                        </Link>
                                    </td>
                                    <td className="text-center">{top.total}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p className="text-muted text-center">No hay productos disponibles</p>
                )}
            </div>
        </div>
    )
}
