import useAdminGetTopBuyers from "../../../hooks/admin/useAdminGetTopBuyers";
import LoadingSwitch from "../../basic/LoadingSwitch/LoadingSwitch";
import avatarPlaceholder from '../../../assets/ui/profile-placeholder.png';
import { Link } from "react-router-dom";

export default function TopBuyers() {
    const title = "Top Compradores";
    const { users, loading, error } = useAdminGetTopBuyers();

    return (
        <LoadingSwitch loading={loading} error={error}>
            <div className="bg-body rounded shadow-sm d-flex flex-column gap-3 p-4 my-4">
                <h2 className="text-center border-bottom pb-3">{title}</h2>
                <div className="d-flex flex-column justify-content-center gap-4">
                    {users && users.length > 0 ? (
                        <table className="table">
                            <thead>
                                <tr>
                                    <th className="text-center" scope="col">Top n°</th>
                                    <th scope="col">Usuario</th>
                                    <th className="text-center" scope="col">N° de Compras</th>
                                </tr>
                            </thead>
                            <tbody>
                                {users.map((user, index) => (
                                    <tr key={top.productId || index} className="align-middle">
                                        <th className="text-center" scope="row">{index + 1}</th>
                                        <td>
                                            <Link to={`/admin/users/${user.userId}`}>
                                                <img className='img-fluid rounded-circle mx-2' src={avatarPlaceholder} alt={`${user.fullName}`} style={{ width: '40px', height: '40px' }} />
                                                {user.fullName}
                                            </Link>
                                        </td>
                                        <td className="text-center">{user.purchaseCount}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ) : (
                        <p className="text-muted text-center">No hay productos disponibles</p>
                    )}
                </div>
            </div>
        </LoadingSwitch>

    )
}